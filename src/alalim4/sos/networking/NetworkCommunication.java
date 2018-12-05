package alalim4.sos.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import packets.Packet;

final class NetworkCommunication
{
	public NetworkCommunication(Socket netSocket, long pingDelay) throws IOException
	{
		outputWriter = new ObjectOutputStream(netSocket.getOutputStream());
		outputWriter.flush();
		inputReader = new ObjectInputStream(netSocket.getInputStream());
		socket = netSocket;
		
		connected = netSocket.isConnected();
		
//		Timer pingTimer = new Timer();
//		
//		TimerTask pingTimerTask = new TimerTask()
//		{
//			@Override
//			public void run()
//			{
//				sendData(new PingPacket());
//				
//				if(!isConnected())
//					pingTimer.cancel();
//			}
//		};
//		
//		pingTimer.scheduleAtFixedRate(pingTimerTask, pingDelay, pingDelay);
	}

	public void close()
	{
		if (socket != null)
		{
			try
			{
				socket.close();
			}
			catch (IOException e) {}
		}
		
		connected = false;
	}

	public Packet readData()
	{
		if(!connected)
			return null;
		
		try
		{
			synchronized (inputReader)
			{
				Object obj = inputReader.readObject();
				
	
				if (obj instanceof Packet)
				{
					System.out.println("Received " + ((Packet) obj));
					return (Packet) obj;
				}
	
				return null;
			}
		}
		catch(ClassNotFoundException e)
		{
			return null;
		}
		catch (IOException e)
		{
			connected = false;
			return null;
		}
	}

	public <T extends Packet> void sendData(T data)
	{
		if(!connected)
			return;
		
		try
		{
			synchronized (outputWriter)
			{
				outputWriter.writeObject(data);
				System.out.println("Sent " + data);
			}
		}
		catch(IOException e)
		{
			connected = false;
		}
	}
	
	public boolean isConnected()
	{
		return connected;
	}

	private final ObjectInputStream inputReader;
	private final ObjectOutputStream outputWriter;
	private final Socket socket;
	private boolean connected;
}