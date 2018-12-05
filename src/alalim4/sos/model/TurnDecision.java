package alalim4.sos.model;

import packets.PlayerMovePacket;

public class TurnDecision
{
	public TurnDecision(int turnRow, int turnColumn, GameSymbol turnSymbol)
	{
		row = turnRow;
		column = turnColumn;
		symbol = turnSymbol;
	}

	public int getRow()
	{
		return row;
	}

	public int getColumn()
	{
		return column;
	}

	public GameSymbol getSymbol()
	{
		return symbol;
	}
	
	public PlayerMovePacket toPlayerMovePacket()
	{
		char gameSymbol = 'S';
		if(symbol == GameSymbol.O)
			gameSymbol = 'O';
		return new PlayerMovePacket(row, column, gameSymbol);
	}

	@Override
	public String toString()
	{
		return "TurnDecision [row=" + row + ", column=" + column + ", symbol=" + symbol + "]";
	}

	private final int row;
	private final int column;
	private final GameSymbol symbol;
}
