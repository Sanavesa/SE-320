package alalim4.sos.networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.BiConsumer;

import packets.Packet;

public class Client
{
	public Client(PacketHandler handler, String ip, int port) throws UnknownHostException, IOException
	{
		this(handler, new Socket(ip, port));
	}

	public Client(PacketHandler handler, Socket sock) throws IOException
	{
		System.out.println("Client connected");
		socket = sock;
		networkCommunication = new NetworkCommunication(socket, 5000);
		packetHandler = handler;
		
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
			while(isConnected())
			{
				Packet packet = networkCommunication.readData();
				
				if(packet != null)
				{
					onReceivedData(packet);
				}
			}
			
			disconnect();
		});
	}
	
	public final <T extends Packet> void sendData(T data)
	{
		if(!isConnected())
			return;
		
		networkCommunication.sendData(data);
	}
	
	public boolean isConnected()
	{
		return networkCommunication.isConnected();
	}
	
	public void disconnect()
	{
		if(!isConnected())
			return;
		
		networkCommunication.close();
		System.out.println("Client disconnected");
	}
	
	public PacketHandler getPacketHandler()
	{
		return packetHandler;
	}

	private void onReceivedData(Packet data)
	{
		if(!isConnected())
			return;
		
		BiConsumer<Client, Packet> consumer = packetHandler.getHandler(data.getPacketHeader());
		if(consumer != null)
		{
			consumer.accept(this, data);
		}
	}
	
	private final Socket socket;
	private final NetworkCommunication networkCommunication;
	private final PacketHandler packetHandler;
}