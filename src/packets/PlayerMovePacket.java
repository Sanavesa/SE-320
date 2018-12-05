package packets;

import alalim4.sos.model.GameSymbol;
import alalim4.sos.model.TurnDecision;

public class PlayerMovePacket extends Packet
{
	public PlayerMovePacket(int moveRow, int moveColumn, char moveSymbol)
	{
		row = moveRow;
		column = moveColumn;
		symbol = moveSymbol;
	}
	
	@Override
	public PacketHeader getPacketHeader()
	{
		return PacketHeader.PlayerMove;
	}
	
	public int getRow()
	{
		return row;
	}

	public void setRow(int moveRow)
	{
		row = moveRow;
	}

	public int getColumn()
	{
		return column;
	}

	public void setColumn(int moveColumn)
	{
		column = moveColumn;
	}

	public GameSymbol getSymbol()
	{
		return GameSymbol.fromCharacter(symbol);
	}

	public void setSymbol(char moveSymbol)
	{
		symbol = moveSymbol;
	}
	
	public void setSymbol(GameSymbol moveSymbol)
	{
		symbol = moveSymbol.toString().charAt(0);
	}
	
	public TurnDecision toTurnDecision()
	{
		return new TurnDecision(row, column, getSymbol());
	}
	
	@Override
	public String toString()
	{
		return "PlayerMovePacket [row=" + row + ", column=" + column + ", symbol=" + symbol + "]";
	}

	private static final long serialVersionUID = 7L;
	private int row;
	private int column;
	private char symbol;
}