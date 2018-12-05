package alalim4.sos.view.onlineLobby;

import alalim4.sos.model.Board;
import alalim4.sos.model.Game;
import alalim4.sos.model.GameMode;
import alalim4.sos.model.Player;
import alalim4.sos.model.gameMode.CombatGame;
import alalim4.sos.model.gameMode.ExtremeGame;
import alalim4.sos.model.gameMode.NormalGame;
import alalim4.sos.view.Controller;
import alalim4.sos.view.View;

public abstract class OnlineLobbyViewController implements Controller
{
	public OnlineLobbyViewController(OnlineLobbyView onlineLobbyView)
	{
		view = onlineLobbyView;
	}

	@Override
	public View getView()
	{
		return view;
	}

	@Override
	public void setupController()
	{
		setupBackButton();
		setupStartButton();
	}
	
	public Game createGame(boolean randomizeTurns)
	{
		GameMode gameMode = view.getGameMode();
		int boardSize = view.getBoardSize();
		String hostName = view.getHostPlayerName();
		String clientName = view.getClientPlayerName();
		
		Board board = new Board(boardSize);
		Player hostPlayer = new Player(hostName);
		Player clientPlayer = new Player(clientName);
		
		if(gameMode == GameMode.Normal)
			return new NormalGame(board, hostPlayer, clientPlayer, randomizeTurns);
		else if(gameMode == GameMode.Combat)
			return new CombatGame(board, hostPlayer, clientPlayer, randomizeTurns);
		else if(gameMode == GameMode.Extreme)
			return new ExtremeGame(board, hostPlayer, clientPlayer, randomizeTurns);
		else
			return null;
	}
	
	protected abstract void setupBackButton();
	protected abstract void setupStartButton();
	
	protected final OnlineLobbyView view;
}