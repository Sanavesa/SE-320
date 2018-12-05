package alalim4.sos.model;

public class SOS
{
	public SOS(BoardCell sosStartCell, BoardCell sosMiddleCell, BoardCell sosEndcell, double sosScore)
	{
		startCell = sosStartCell;
		middleCell = sosMiddleCell;
		endCell = sosEndcell;
		score = sosScore;
	}

	public double getScore()
	{
		return score;
	}

	public BoardCell getStartCell()
	{
		return startCell;
	}

	public BoardCell getMiddleCell()
	{
		return middleCell;
	}

	public BoardCell getEndCell()
	{
		return endCell;
	}

	public boolean intersects(SOS other)
	{
		if (cellIntersects(startCell, other))
		{
			return true;
		}

		if (cellIntersects(middleCell, other))
		{
			return true;
		}

		if (cellIntersects(endCell, other))
		{
			return true;
		}

		return false;
	}

	private boolean cellIntersects(BoardCell startCell, SOS other)
	{
		return (startCell == other.startCell) || 
			(startCell == other.middleCell) ||
			(startCell == other.endCell);
	}

	@Override
	public int hashCode()
	{
		final int prime = 71;
		int result = 1;
		result = prime * result + ((endCell == null) ? 0 : endCell.hashCode());
		result = prime * result + ((middleCell == null) ? 0 : middleCell.hashCode());
		result = prime * result + ((startCell == null) ? 0 : startCell.hashCode());
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

		SOS other = (SOS) obj;
		return equals(other);
	}

	public boolean equals(SOS other)
	{
		if (endCell == null)
		{
			if (other.endCell != null)
			{
				return false;
			}
		}
		else if (!endCell.equals(other.endCell))
		{
			return false;
		}

		if (middleCell == null)
		{
			if (other.middleCell != null)
			{
				return false;
			}
		}
		else if (!middleCell.equals(other.middleCell))
		{
			return false;
		}

		if (startCell == null)
		{
			if (other.startCell != null)
			{
				return false;
			}
		}
		else if (!startCell.equals(other.startCell))
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "SOS [startCell=" + startCell + ", middleCell=" + middleCell + ", endCell=" + endCell + ", score="
				+ score + "]";
	}

	private final BoardCell startCell;
	private final BoardCell middleCell;
	private final BoardCell endCell;
	private final double score;
}
