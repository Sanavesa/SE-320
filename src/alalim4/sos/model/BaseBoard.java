package alalim4.sos.model;

public abstract class BaseBoard<T>
{
	public BaseBoard(int rows, int cols)
	{
		numRows = rows;
		numCols = cols;

		initializeBoard();
	}

	public T getAt(int row, int col)
	{
		return matrix[row][col];
	}

	public void setAt(int row, int col, T newValue)
	{
		matrix[row][col] = newValue;
	}

	public int getNumRows()
	{
		return numRows;
	}

	public int getNumCols()
	{
		return numCols;
	}

	public boolean isOutOfBounds(int row, int col)
	{
		return (row < 0) || (col < 0) || (row >= numRows) || (col >= numCols);
	}

	@Override
	public String toString()
	{
		return "BaseBoard [numRows=" + numRows + ", numCols=" + numCols + "]";
	}

	public abstract boolean isEmpty();

	public abstract boolean isFull();

	public abstract void clearBoard();

	protected abstract void initializeBoard();

	protected void setMatrix(T[][] newMatrix)
	{
		matrix = newMatrix;
	}

	private T[][] matrix;
	private final int numRows;
	private final int numCols;
}
