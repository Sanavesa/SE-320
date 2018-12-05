package alalim4.sos.model;

public class Board extends BaseBoard<BoardCell>
{
	public Board(int size)
	{
		super(size, size);
	}

	@Override
	public boolean isEmpty()
	{
		for (int row = 0; row < getNumRows(); row++)
		{
			for (int col = 0; col < getNumCols(); col++)
			{
				BoardCell cell = getAt(row, col);
				if (cell.getSymbol() != GameSymbol.Empty)
				{
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean isFull()
	{
		for (int row = 0; row < getNumRows(); row++)
		{
			for (int col = 0; col < getNumCols(); col++)
			{
				BoardCell cell = getAt(row, col);
				if (cell.getSymbol() == GameSymbol.Empty)
				{
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void clearBoard()
	{
		for (int row = 0; row < getNumRows(); row++)
		{
			for (int col = 0; col < getNumCols(); col++)
			{
				BoardCell cell = getAt(row, col);
				cell.setSymbol(GameSymbol.Empty);
			}
		}
	}

	@Override
	protected void initializeBoard()
	{
		BoardCell newMatrix[][] = new BoardCell[getNumRows()][getNumRows()];
		setMatrix(newMatrix);

		for (int row = 0; row < getNumRows(); row++)
		{
			for (int col = 0; col < getNumCols(); col++)
			{
				BoardCell cell = new BoardCell(row, col, GameSymbol.Empty);
				setAt(row, col, cell);
			}
		}
	}
}
