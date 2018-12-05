package packets;

import alalim4.sos.model.GameMode;

public class GameDetailsPacket extends Packet
{
	public GameDetailsPacket(int size, GameMode gameMode, String name)
	{
		setBoardSize(size);
		setGameMode(gameMode);
		setHostName(name);
	}
	
	public GameDetailsPacket(int size, String mode, String name)
	{
		boardSize = size;
		gameMode = mode;
		hostName = name;
	}
	
	@Override
	public PacketHeader getPacketHeader()
	{
		return PacketHeader.GameDetails;
	}
	
	public int getBoardSize()
	{
		return boardSize;
	}

	public void setBoardSize(int size)
	{
		boardSize = size;
	}

	public GameMode getGameMode()
	{
		return GameMode.fromString(gameMode);
	}

	public void setGameMode(String mode)
	{
		gameMode = mode;
	}
	
	public void setGameMode(GameMode mode)
	{
		gameMode = mode.toString();
	}

	public String getHostName()
	{
		return hostName;
	}

	public void setHostName(String name)
	{
		hostName = name;
	}
	
	@Override
	public String toString()
	{
		return "GameDetailsPacket [boardSize=" + boardSize + ", gameMode=" + gameMode + ", hostName=" + hostName + "]";
	}

	private static final long serialVersionUID = 8L;
	private int boardSize;
	private String gameMode;
	private String hostName;
}