package alalim4.sos.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import java.util.function.Supplier;

import packets.Packet;

public class Server
{
	public Server(int port) throws IOException
	{
		serverSocket = new ServerSocket(port);
		clients = new ArrayList<>();
		connected = serverSocket.isBound();
		onConnectedClientCallback = null;
		
		System.out.println("[SERVER] Started on port: " + port);
		
		listenForConnections();
	}

	public void stop()
	{
		if(!isConnected())
			return;
		
		clients.stream().forEach(client ->
		{
			client.disconnect();
		});
		
		if(serverSocket != null)
		{
			try
			{
				serverSocket.close();
			}
			catch (IOException e) {}
		}
		
		System.out.println("[SERVER] Stopped");
		clients.clear();
		connected = false;
	}
	
	public void disconnectClient(Client client)
	{
		if(!isConnected())
			return;
		
		client.disconnect();
		clients.remove(client);
		System.out.println("[SERVER] Client (" + clients.size() + ") removed: " + client);
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
	public Consumer<Client> getOnConnectedClient()
	{
		return onConnectedClientCallback;
	}

	public void setOnConnectedClient(Consumer<Client> callback)
	{
		onConnectedClientCallback = callback;
	}
	
	public Supplier<PacketHandler> getClientPacketHandler()
	{
		return clientPacketHandler;
	}

	public void setClientPacketHandler(Supplier<PacketHandler> callback)
	{
		clientPacketHandler = callback;
	}
	
	public final <T extends Packet> void sendDataToAll(T data)
	{
		for(Client client : clients)
		{
			client.sendData(data);
		}
	}

	public List<Client> getClients()
	{
		return clients;
	}

	private void listenForConnections()
	{
		Executor executor = Executors.newSingleThreadExecutor(new ThreadFactory()
		{
			@Override
			public Thread newThread(Runnable r)
			{
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});
		
		executor.execute(() ->
		{
			try
			{
				while(isConnected())
				{
					Socket client = serverSocket.accept();
					PacketHandler packetHandler = clientPacketHandler.get();
					Client serverClient = new Client(packetHandler, client);
					clients.add(serverClient);
					
					System.out.println("[SERVER] New connection (" + clients.size() + "): " + client);
					
					if(onConnectedClientCallback != null)
					{
						onConnectedClientCallback.accept(serverClient);
					}
				}
			}
			catch (IOException e)
			{
				
			}
			finally
			{
				stop();
			}
		});
	}
	
	private final ServerSocket serverSocket;
	private List<Client> clients;
	private boolean connected;
	private Consumer<Client> onConnectedClientCallback;
	private Supplier<PacketHandler> clientPacketHandler;
}
