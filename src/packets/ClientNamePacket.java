package packets;

public class ClientNamePacket extends Packet
{
	public ClientNamePacket(String clientName)
	{
		name = clientName;
	}
	
	@Override
	public PacketHeader getPacketHeader()
	{
		return PacketHeader.ClientName;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String clientName)
	{
		name = clientName;
	}
	
	@Override
	public String toString()
	{
		return "ClientNamePacket [name=" + name + "]";
	}

	private static final long serialVersionUID = 10L;
	private String name;
}
