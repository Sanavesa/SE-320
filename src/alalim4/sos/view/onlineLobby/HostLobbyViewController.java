package alalim4.sos.view.onlineLobby;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javafx.application.Platform;

import alalim4.sos.model.Game;
import alalim4.sos.model.GameState;
import alalim4.sos.model.Player;
import alalim4.sos.model.TurnDecision;
import alalim4.sos.networking.Client;
import alalim4.sos.networking.PacketHandler;
import alalim4.sos.networking.Server;
import alalim4.sos.view.game.GameView;
import packets.GameRestartPacket;
import packets.PacketHeader;
import packets.PlayerMovePacket;

public class HostLobbyViewController extends OnlineLobbyViewController
{
	public HostLobbyViewController(OnlineLobbyView onlineLobbyView)
	{
		super(onlineLobbyView);
	}
	
	public Server getServer()
	{
		return server;
	}

	public void setServer(Server server)
	{
		this.server = server;
	}

	@Override
	protected void setupBackButton()
	{
		view.getBtnBack().setOnAction(e ->
		{
			server.stop();
			view.getViewTransition().goBack();
		});
	}

	@Override
	protected void setupStartButton()
	{
		view.getBtnStart().setDisable(true);
		
		view.getBtnStart().setOnAction(e ->
		{
			Game game = createGame(true);
			GameView nextView = new GameView(view.getViewTransition(), game);
			
			hookTurnBegan(game, nextView);
			game.restartGame();
			
			Platform.runLater(() ->
			{
				view.getViewTransition().gotoView(nextView);
			});
			
			Client client = server.getClients().get(0);
			hookPlayerMovePacket(client.getPacketHandler(), game, nextView);
			hookNetworkedPlayerMove(client, game, nextView);
			hookNetworkedGameRestart(client, game);
			
			sendGameRestartPacket(client, game);
		});
	}

	private void hookPlayerMovePacket(PacketHandler handler, Game game, GameView gameView)
	{
		handler.setHandler(PacketHeader.PlayerMove, (client, packet) ->
		{
			PlayerMovePacket playerMovePacket = (PlayerMovePacket) packet;
			gameView.getGameBoard().drawSymbol(playerMovePacket.getRow(), playerMovePacket.getColumn(), playerMovePacket.getSymbol());
		});
	}
	
	private void hookNetworkedGameRestart(Client client, Game game)
	{
		Runnable oldHandler = game.getOnRestartedGame();
		game.setOnRestartedGame(() ->
		{
			oldHandler.run();
			sendGameRestartPacket(client, game);
		});
	}
	
	private void hookTurnBegan(Game game, GameView gameView)
	{
		Consumer<Player> oldHandler = game.getOnTurnBegan();
		game.setOnTurnBegan(player ->
		{
			oldHandler.accept(player);
			gameView.getGameBoard().setMouseTransparent(game.isPlayer2Turn());
			gameView.getGameBoard().setDisable(game.isPlayer2Turn());
			if(game.isPlayer1Turn())
				gameView.getGameBoard().setOpacity(1);
			else
				gameView.getGameBoard().setOpacity(0.5);
		});
	}
	
	private void hookNetworkedPlayerMove(Client client, Game game, GameView gameView)
	{
		BiConsumer<Player, TurnDecision> oldHandler = game.getOnPlayedTurn();
		game.setOnPlayedTurn((player, move) ->
		{
			oldHandler.accept(player, move);
			
			if(game.getPlayer1().equals(player))
			{
				sendPlayerMovePacket(client, move);
				gameView.getBtnRestart().setDisable(true);
			}
			else
			{
				gameView.getBtnRestart().setDisable(false);
			}
		});
		
		Consumer<GameState> oldCallback = game.getOnGameEnded();
		game.setOnGameEnded((gameState) ->
		{
			oldCallback.accept(gameState);
			
			gameView.getBtnRestart().setDisable(false);
		});
	}

	private void sendPlayerMovePacket(Client client, TurnDecision move)
	{
		client.sendData(move.toPlayerMovePacket());
	}

	private void sendGameRestartPacket(Client client, Game game)
	{
		GameRestartPacket gameRestartPacket = new GameRestartPacket(game.isPlayer1Turn());
		client.sendData(gameRestartPacket);
	}
	
	private Server server;
}