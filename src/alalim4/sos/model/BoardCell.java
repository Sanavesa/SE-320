package alalim4.sos.model;

public class BoardCell
{
	public BoardCell(int cellRow, int cellColumn, GameSymbol cellSymbol)
	{
		row = cellRow;
		column = cellColumn;
		symbol = cellSymbol;
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

	public void setSymbol(GameSymbol cellSymbol)
	{
		symbol = cellSymbol;
	}

	@Override
	public int hashCode()
	{
		final int prime = 17;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + row;
		result = prime * result + column;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null)
		{
			return false;
		}

		if (getClass() != obj.getClass())
		{
			return false;
		}

		BoardCell other = (BoardCell) obj;
		return equals(other);
	}

	public boolean equals(BoardCell other)
	{
		if (symbol != other.symbol)
		{
			return false;
		}

		if (row != other.row)
		{
			return false;
		}

		if (column != other.column)
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "SOSBoardCell [row=" + row + ", column=" + column + ", symbol=" + symbol + "]";
	}

	private final int row;
	private final int column;
	private GameSymbol symbol;
}
