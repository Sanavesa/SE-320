package alalim4.sos.view.joinGame;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import alalim4.sos.model.Game;
import alalim4.sos.model.Player;
import alalim4.sos.model.TurnDecision;
import alalim4.sos.networking.Client;
import alalim4.sos.networking.PacketHandler;
import alalim4.sos.view.game.GameView;
import alalim4.sos.view.onlineLobby.JoinLobbyViewController;
import alalim4.sos.view.onlineLobby.OnlineLobbyView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import packets.ClientNamePacket;
import packets.GameDetailsPacket;
import packets.GameRestartPacket;
import packets.PacketHeader;
import packets.PlayerMovePacket;

public class JoinGameNetworkLogic
{
	public void joinGame(JoinGameView view, JoinGameViewController controller)
	{
		Runnable runnable = () ->
		{
			String ip = controller.getIPAdress();
			int port = controller.getPort();
			String clientName = controller.getClientPlayerName();
			
			try
			{
				OnlineLobbyView nextView = new OnlineLobbyView(view.getViewTransition());
				JoinLobbyViewController nextController = new JoinLobbyViewController(nextView);
				nextView.setController(nextController);
				
				PacketHandler handler = createPacketHandler(nextView, nextController);
				Client client = new Client(handler, ip, port);
				
				nextController.setClient(client);
				nextController.setupController();
				
				view.getViewTransition().getStage().setOnCloseRequest(request ->
				{
					client.disconnect();
				});
				
				sendClientNamePacket(clientName, client);
				
				Platform.runLater(() ->
				{
					view.getViewTransition().gotoView(nextView);
					
					nextView.setClientPlayerName(clientName);
				});
			}
			catch(IOException e)
			{
				Platform.runLater(() ->
				{
					joinGameError(ip, port, e);
				});
			}
		};
		
		Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		thread.start();
	}

	private void sendClientNamePacket(String clientName, Client client)
	{
		client.sendData(new ClientNamePacket(clientName));
	}

	private PacketHandler createPacketHandler(OnlineLobbyView view, JoinLobbyViewController controller)
	{
		PacketHandler handler = new PacketHandler();
		
		hookGameDetailsPacket(view, handler);
		hookGameRestartPacket(view, controller, handler);
		
		return handler;
	}
	
	private void hookGameDetailsPacket(OnlineLobbyView view, PacketHandler handler)
	{
		handler.setHandler(PacketHeader.GameDetails, (client, packet) ->
		{
			GameDetailsPacket gameDetailsPacket = (GameDetailsPacket) packet;
			
			view.setBoardSize(gameDetailsPacket.getBoardSize());
			view.setGameMode(gameDetailsPacket.getGameMode());
			view.setHostPlayerName(gameDetailsPacket.getHostName());
		});
	}
	
	private void hookGameRestartPacket(OnlineLobbyView view, JoinLobbyViewController controller, PacketHandler handler)
	{
		handler.setHandler(PacketHeader.GameRestart, (client, packet) ->
		{
			GameRestartPacket gameRestartPacket = (GameRestartPacket) packet;
			processGameStartPacket(view, controller, handler, client, gameRestartPacket);
		});
	}

	private void processGameStartPacket(OnlineLobbyView view, JoinLobbyViewController controller,
			PacketHandler handler, Client client, GameRestartPacket gameRestartPacket)
	{
		Game game = controller.createGame(false);
		GameView nextView = new GameView(view.getViewTransition(), game);
		
		Platform.runLater(() ->
		{
			view.getViewTransition().gotoView(nextView);
		});
		
		hookPlayerMovePacket(handler, game, nextView);
		hookGameRestartPacket(handler, game);
		hookNetworkedPlayerMove(client, game);
		hookTurnBegan(game, nextView);
		
		Platform.runLater(() ->
		{
			nextView.getBtnRestart().setDisable(true);
		});
		
		game.restartGame();
		game.setTurn(gameRestartPacket.isHostTurn());
	}

	private void hookNetworkedPlayerMove(Client client, Game game)
	{
		BiConsumer<Player, TurnDecision> oldHandler = game.getOnPlayedTurn();
		game.setOnPlayedTurn((player, move) ->
		{
			oldHandler.accept(player, move);
			
			if(game.getPlayer2().equals(player))
			{
				sendPlayerMovePacket(client, move);
			}
		});
		
	}
	
	private void hookTurnBegan(Game game, GameView gameView)
	{
		Consumer<Player> oldHandler = game.getOnTurnBegan();
		game.setOnTurnBegan(player ->
		{
			oldHandler.accept(player);
			gameView.getGameBoard().setMouseTransparent(game.isPlayer1Turn());
			gameView.getGameBoard().setDisable(game.isPlayer1Turn());
			if(game.isPlayer2Turn())
				gameView.getGameBoard().setOpacity(1);
			else
				gameView.getGameBoard().setOpacity(0.5);
		});
	}

	private void sendPlayerMovePacket(Client client, TurnDecision move)
	{
		client.sendData(move.toPlayerMovePacket());
	}

	private void hookGameRestartPacket(PacketHandler handler, Game game)
	{
		handler.setHandler(PacketHeader.GameRestart, (client, packet) ->
		{
			GameRestartPacket gameRestartPacket2 = (GameRestartPacket) packet;
			game.restartGame();
			game.setTurn(gameRestartPacket2.isHostTurn());
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

	private void joinGameError(String ip, int port, IOException exception)
	{
		Alert alert = new Alert(AlertType.ERROR,
				"Error encountered when connecting to " + ip + ": " + port + ".\n"
				+ "Reason is " + exception.getMessage(),
				ButtonType.OK);
		
		alert.setTitle("Network Error");
		alert.setHeaderText("Failed to join!");
		alert.show();
	}
}