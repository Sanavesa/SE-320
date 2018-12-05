package alalim4.sos.model;

public class SOSChecker
{
	public SOSChecker(Board sosBoard)
	{
		board = sosBoard;
	}

	public boolean isVerticalSOS(int row, int col)
	{
		if (board.isOutOfBounds(row, col) || board.isOutOfBounds(row + 2, col))
		{
			return false;
		}

		BoardCell startCell = board.getAt(row, col);
		BoardCell middleCell = board.getAt(row + 1, col);
		BoardCell endCell = board.getAt(row + 2, col);

		return isSOS(startCell, middleCell, endCell);
	}

	public boolean isHorizontalSOS(int row, int col)
	{
		if (board.isOutOfBounds(row, col) || board.isOutOfBounds(row, col + 2))
		{
			return false;
		}

		BoardCell startCell = board.getAt(row, col);
		BoardCell middleCell = board.getAt(row, col + 1);
		BoardCell endCell = board.getAt(row, col + 2);

		return isSOS(startCell, middleCell, endCell);
	}

	public boolean isDiagonalRightSOS(int row, int col)
	{
		if (board.isOutOfBounds(row, col) || board.isOutOfBounds(row + 2, col + 2))
		{
			return false;
		}

		BoardCell startCell = board.getAt(row, col);
		BoardCell middleCell = board.getAt(row + 1, col + 1);
		BoardCell endCell = board.getAt(row + 2, col + 2);

		return isSOS(startCell, middleCell, endCell);
	}

	public boolean isDiagonalLeftSOS(int row, int col)
	{
		if (board.isOutOfBounds(row, col) || board.isOutOfBounds(row + 2, col - 2))
		{
			return false;
		}

		BoardCell startCell = board.getAt(row, col);
		BoardCell middleCell = board.getAt(row + 1, col - 1);
		BoardCell endCell = board.getAt(row + 2, col - 2);

		return isSOS(startCell, middleCell, endCell);
	}

	private boolean isSOS(BoardCell startCell, BoardCell middleCell, BoardCell endCell)
	{
		return (startCell.getSymbol() == GameSymbol.S) && (middleCell.getSymbol() == GameSymbol.O)
				&& (endCell.getSymbol() == GameSymbol.S);
	}

	private final Board board;
}
