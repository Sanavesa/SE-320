package packets;

public class GameRestartPacket extends Packet
{
	public GameRestartPacket(boolean isHostTurn)
	{
		hostTurn = isHostTurn;
	}

	@Override
	public PacketHeader getPacketHeader()
	{
		return PacketHeader.GameRestart;
	}
	
	public boolean isHostTurn()
	{
		return hostTurn;
	}
	
	public void setHostTurn(boolean isHostTurn)
	{
		hostTurn = isHostTurn;
	}
	
	@Override
	public String toString()
	{
		return "GameRestartPacket [hostTurn=" + hostTurn + "]";
	}

	private static final long serialVersionUID = 11L;
	private boolean hostTurn;
}