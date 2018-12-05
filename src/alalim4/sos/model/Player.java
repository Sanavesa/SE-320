package alalim4.sos.model;

import java.util.HashSet;
import java.util.Set;

public class Player
{
	public Player(String pName)
	{
		name = pName;
		sos = new HashSet<SOS>();
	}

	public void playMove(Board board, int row, int col, GameSymbol symbol)
	{
		BoardCell cell = board.getAt(row, col);
		cell.setSymbol(symbol);
	}

	public boolean addSOS(SOS newSOS)
	{
		synchronized (sos)
		{
			return sos.add(newSOS);
		}
	}

	public boolean removeSOS(SOS sosRemove)
	{
		synchronized (sos)
		{
			return sos.remove(sosRemove);
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String pName)
	{
		name = pName;
	}

	public Set<SOS> getSos()
	{
		synchronized (sos)
		{
			return sos;
		}
	}

	public double getScore()
	{
		synchronized (sos)
		{
			return sos.stream().mapToDouble(e -> e.getScore()).sum();
		}
	}

	@Override
	public String toString()
	{
		return "Player [name=" + name + ", SOSs=" + getScore() + "]";
	}

	private String name;
	private volatile Set<SOS> sos;
}
