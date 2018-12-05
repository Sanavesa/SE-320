package alalim4.sos.model.gameMode;

import alalim4.sos.model.Board;
import alalim4.sos.model.Game;
import alalim4.sos.model.GameMode;
import alalim4.sos.model.GameSymbol;
import alalim4.sos.model.Player;
import alalim4.sos.model.SOS;

public class NormalGame extends Game
{
	public NormalGame(Board gameBoard, Player p1, Player p2, boolean randomizeTurns)
	{
		super(GameMode.Normal, gameBoard, p1, p2, randomizeTurns);
	}

	@Override
	protected boolean playInternal(int row, int col, GameSymbol symbol)
	{
		double sosScore = getCurrentPlayerTurn().getScore();
		getCurrentPlayerTurn().playMove(getBoard(), row, col, symbol);
		checkForSOS(getCurrentPlayerTurn());
		double newSosScore = getCurrentPlayerTurn().getScore();

		if (newSosScore == sosScore)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	protected void restartGameInternal()
	{
		getBoard().clearBoard();
		getPlayer1().getSos().clear();
		getPlayer2().getSos().clear();
		
		if(randomizePlayerTurn)
			randomizeTurn();
	}

	@Override
	protected boolean addSOSToPlayer(Player player, SOS sosAdded)
	{
		Player opponent = getOtherPlayer(player);
		if (opponent.getSos().contains(sosAdded))
		{
			return false;
		}

		return super.addSOSToPlayer(player, sosAdded);
	}

	private void checkForSOS(Player player)
	{
		for (int row = 0; row < getBoard().getNumRows(); row++)
		{
			for (int col = 0; col < getBoard().getNumCols(); col++)
			{
				checkMultidirectionalSOS(player, row, col);
			}
		}
	}

	private void checkMultidirectionalSOS(Player player, int row, int col)
	{
		if (getSosChecker().isVerticalSOS(row, col))
		{
			SOS sos = getSosMaker().makeVerticalSOS(row, col, 1);
			addSOSToPlayer(player, sos);
		}

		if (getSosChecker().isHorizontalSOS(row, col))
		{
			SOS sos = getSosMaker().makeHorizontalSOS(row, col, 1);
			addSOSToPlayer(player, sos);
		}

		if (getSosChecker().isDiagonalLeftSOS(row, col))
		{
			SOS sos = getSosMaker().makeDiagonalLeftSOS(row, col, 1);
			addSOSToPlayer(player, sos);
		}

		if (getSosChecker().isDiagonalRightSOS(row, col))
		{
			SOS sos = getSosMaker().makeDiagonalRightSOS(row, col, 1);
			addSOSToPlayer(player, sos);
		}
	}
}
