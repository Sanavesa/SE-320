package alalim4.sos.model;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class Game
{
	public Game(GameMode mode, Board gameBoard, Player p1, Player p2, boolean randomizeTurns)
	{
		gameMode = mode;
		board = gameBoard;
		player1 = p1;
		player2 = p2;
		sosChecker = new SOSChecker(board);
		sosMaker = new SOSMaker(board);
		random = new Random();

		onGameEndedCallback = null;
		onAddedSOSCallback = null;
		onRestartedGameCallback = null;
		onTurnBeganCallback = null;
		onTurnEndedCallback = null;
		
		randomizePlayerTurn = randomizeTurns;
		
		if(randomizePlayerTurn)
			randomizeTurn();
	}

	public void randomizeTurn()
	{
		if (random.nextBoolean())
		{
			currentPlayerTurn = player1;
		}
		else
		{
			currentPlayerTurn = player2;
		}

		if (onTurnBeganCallback != null)
		{
			onTurnBeganCallback.accept(currentPlayerTurn);
		}
	}
	
	public void setTurn(boolean isPlayerOneTurn)
	{
		if (onTurnEndedCallback != null)
		{
			onTurnEndedCallback.accept(currentPlayerTurn);
		}
		
		if(isPlayerOneTurn)
			currentPlayerTurn = player1;
		else
			currentPlayerTurn = player2;
		
		if (onTurnBeganCallback != null)
		{
			onTurnBeganCallback.accept(currentPlayerTurn);
		}
	}

	public boolean isPlayer1Turn()
	{
		return (player1 == currentPlayerTurn);
	}

	public boolean isPlayer2Turn()
	{
		return (player2 == currentPlayerTurn);
	}

	public Player getCurrentPlayerTurn()
	{
		return currentPlayerTurn;
	}

	public Board getBoard()
	{
		return board;
	}

	public Player getPlayer1()
	{
		return player1;
	}

	public Player getPlayer2()
	{
		return player2;
	}
	
	public GameMode getGameMode()
	{
		return gameMode;
	}

	public Player getOtherPlayer(Player player)
	{
		if (player1.equals(player))
		{
			return player2;
		}
		else
		{
			return player1;
		}
	}

	public GameState getGameState()
	{
		if (board.isFull())
		{
			if (player1.getScore() > player2.getScore())
			{
				return GameState.WinP1;
			}
			else if (player2.getScore() > player1.getScore())
			{
				return GameState.WinP2;
			}
			else
			{
				return GameState.Tie;
			}
		}
		else
		{
			return GameState.Ongoing;
		}
	}

	public Runnable getOnRestartedGame()
	{
		return onRestartedGameCallback;
	}

	public void setOnRestartedGame(Runnable callback)
	{
		onRestartedGameCallback = callback;
	}

	public BiConsumer<Player, SOS> getOnAddedSOS()
	{
		return onAddedSOSCallback;
	}

	public void setOnAddedSOS(BiConsumer<Player, SOS> callback)
	{
		onAddedSOSCallback = callback;
	}

	public BiConsumer<Player, SOS> getOnRemovedSOS()
	{
		return onRemovedSOSCallback;
	}

	public void setOnRemovedSOS(BiConsumer<Player, SOS> callback)
	{
		onRemovedSOSCallback = callback;
	}

	public Consumer<GameState> getOnGameEnded()
	{
		return onGameEndedCallback;
	}

	public void setOnGameEnded(Consumer<GameState> callback)
	{
		onGameEndedCallback = callback;
	}

	public Consumer<Player> getOnTurnBegan()
	{
		return onTurnBeganCallback;
	}

	public void setOnTurnBegan(Consumer<Player> callback)
	{
		onTurnBeganCallback = callback;
	}

	public Consumer<Player> getOnTurnEnded()
	{
		return onTurnEndedCallback;
	}

	public void setOnTurnEnded(Consumer<Player> callback)
	{
		onTurnEndedCallback = callback;
	}

	public BiConsumer<Player, TurnDecision> getOnPlayedTurn()
	{
		return onPlayedTurnCallback;
	}

	public void setOnPlayedTurn(BiConsumer<Player, TurnDecision> callback)
	{
		onPlayedTurnCallback = callback;
	}

	public void restartGame()
	{
		restartGameInternal();

		if (onRestartedGameCallback != null)
		{
			onRestartedGameCallback.run();
		}

		if (onTurnBeganCallback != null)
		{
			onTurnBeganCallback.accept(currentPlayerTurn);
		}
	}
	
	public void play(TurnDecision turnDecision)
	{
		play(turnDecision.getRow(), turnDecision.getColumn(), turnDecision.getSymbol());
	}

	public void play(int row, int col, GameSymbol symbol)
	{
		GameState gameState = getGameState();
		Player player = getCurrentPlayerTurn();
		boolean shouldAlternateTurn = false;

		if (onPlayedTurnCallback != null)
		{
			TurnDecision turnDecision = new TurnDecision(row, col, symbol);
			onPlayedTurnCallback.accept(player, turnDecision);
		}

		if (gameState == GameState.Ongoing)
		{
			shouldAlternateTurn = playInternal(row, col, symbol);
		}

		gameState = getGameState();

		if (gameState != GameState.Ongoing)
		{
			if (onGameEndedCallback != null)
			{
				onGameEndedCallback.accept(gameState);
			}
		}
		else
		{
			if (shouldAlternateTurn)
			{
				alternateTurn();
			}
		}
	}

	@Override
	public String toString()
	{
		return "Game [player1=" + player1 + ", player2=" + player2 + ", currentPlayerTurn=" + currentPlayerTurn + "]";
	}

	protected boolean addSOSToPlayer(Player player, SOS sosAdded)
	{
		boolean added = player.addSOS(sosAdded);
		if (added && onAddedSOSCallback != null)
		{
			onAddedSOSCallback.accept(player, sosAdded);
		}

		return added;
	}

	protected boolean removeSOSFromPlayer(Player player, SOS sosRemove)
	{
		boolean removed = player.removeSOS(sosRemove);
		if (removed && onRemovedSOSCallback != null)
		{
			onRemovedSOSCallback.accept(player, sosRemove);
		}

		return removed;
	}

	protected Random getRandom()
	{
		return random;
	}

	protected SOSChecker getSosChecker()
	{
		return sosChecker;
	}

	protected SOSMaker getSosMaker()
	{
		return sosMaker;
	}

	protected abstract boolean playInternal(int row, int col, GameSymbol symbol);

	protected abstract void restartGameInternal();

	private void alternateTurn()
	{
		if (onTurnEndedCallback != null)
		{
			onTurnEndedCallback.accept(currentPlayerTurn);
		}

		if (isPlayer1Turn())
		{
			currentPlayerTurn = player2;
		}
		else
		{
			currentPlayerTurn = player1;
		}

		if (onTurnBeganCallback != null)
		{
			onTurnBeganCallback.accept(currentPlayerTurn);
		}
	}

	protected final boolean randomizePlayerTurn;
	private final Board board;
	private final Player player1;
	private final Player player2;
	private final Random random;
	private final SOSChecker sosChecker;
	private final SOSMaker sosMaker;
	private final GameMode gameMode;

	private Runnable onRestartedGameCallback;
	private BiConsumer<Player, SOS> onAddedSOSCallback;
	private BiConsumer<Player, SOS> onRemovedSOSCallback;
	private Consumer<GameState> onGameEndedCallback;
	private Consumer<Player> onTurnBeganCallback;
	private Consumer<Player> onTurnEndedCallback;
	private BiConsumer<Player, TurnDecision> onPlayedTurnCallback;
	private Player currentPlayerTurn;
}