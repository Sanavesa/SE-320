package alalim4.sos.networking;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import packets.Packet;
import packets.PacketHeader;

public class PacketHandler
{
	public PacketHandler()
	{
		messageCallbacks = new HashMap<>();
	}
	
	public void setHandler(PacketHeader packetHeader, BiConsumer<Client, Packet> consumer)
	{
		messageCallbacks.put(packetHeader, consumer);
	}
	
	public BiConsumer<Client, Packet> getHandler(PacketHeader packetHeader)
	{
		return messageCallbacks.get(packetHeader);
	}
	
	public void removeHandler(PacketHeader packetHeader)
	{
		messageCallbacks.remove(packetHeader);
	}
	
	public void clearHandlers()
	{
		messageCallbacks.clear();
	}
	
	private final Map<PacketHeader, BiConsumer<Client, Packet>> messageCallbacks;
}