package alalim4.sos.model;

public class SOSMaker
{
	public SOSMaker(Board sosBoard)
	{
		board = sosBoard;
	}

	public SOS makeVerticalSOS(int row, int col, double score)
	{
		if (board.isOutOfBounds(row, col) || board.isOutOfBounds(row + 2, col))
		{
			return null;
		}

		BoardCell startCell = board.getAt(row, col);
		BoardCell middleCell = board.getAt(row + 1, col);
		BoardCell endCell = board.getAt(row + 2, col);

		return new SOS(startCell, middleCell, endCell, score);
	}

	public SOS makeHorizontalSOS(int row, int col, double score)
	{
		if (board.isOutOfBounds(row, col) || board.isOutOfBounds(row, col + 2))
		{
			return null;
		}

		BoardCell startCell = board.getAt(row, col);
		BoardCell middleCell = board.getAt(row, col + 1);
		BoardCell endCell = board.getAt(row, col + 2);

		return new SOS(startCell, middleCell, endCell, score);
	}

	public SOS makeDiagonalRightSOS(int row, int col, double score)
	{
		if (board.isOutOfBounds(row, col) || board.isOutOfBounds(row + 2, col + 2))
		{
			return null;
		}

		BoardCell startCell = board.getAt(row, col);
		BoardCell middleCell = board.getAt(row + 1, col + 1);
		BoardCell endCell = board.getAt(row + 2, col + 2);

		return new SOS(startCell, middleCell, endCell, score);
	}

	public SOS makeDiagonalLeftSOS(int row, int col, double score)
	{
		if (board.isOutOfBounds(row, col) || board.isOutOfBounds(row + 2, col - 2))
		{
			return null;
		}

		BoardCell startCell = board.getAt(row, col);
		BoardCell middleCell = board.getAt(row + 1, col - 1);
		BoardCell endCell = board.getAt(row + 2, col - 2);

		return new SOS(startCell, middleCell, endCell, score);
	}

	private final Board board;
}
