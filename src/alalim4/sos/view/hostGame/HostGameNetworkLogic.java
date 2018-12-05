package alalim4.sos.view.hostGame;

import java.io.IOException;

import alalim4.sos.model.GameMode;
import alalim4.sos.networking.Client;
import alalim4.sos.networking.PacketHandler;
import alalim4.sos.networking.Server;
import alalim4.sos.view.onlineLobby.HostLobbyViewController;
import alalim4.sos.view.onlineLobby.OnlineLobbyView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import packets.ClientNamePacket;
import packets.GameDetailsPacket;
import packets.PacketHeader;

public class HostGameNetworkLogic
{
	public void hostGame(HostGameView view, HostGameViewController controller)
	{
		Runnable runnable = () ->
		{
			int port = controller.getPort();
			
			int boardSize = controller.getBoardSize();
			String hostPlayerName = controller.getHostPlayerName();
			GameMode gameMode = controller.getSelectedGameMode();
			
			try
			{
				OnlineLobbyView nextView = new OnlineLobbyView(view.getViewTransition());
				HostLobbyViewController nextController = new HostLobbyViewController(nextView);
				nextView.setController(nextController);
				
				Server server = new Server(port);
				server.setClientPacketHandler(() -> createPacketHandler(nextView));
				server.setOnConnectedClient(client -> onClientConnected(client, nextView, nextController));
				
				nextController.setServer(server);
				nextController.setupController();
				
				view.getViewTransition().getStage().setOnCloseRequest(request ->
				{
					server.stop();
				});
				
				Platform.runLater(() ->
				{
					view.getViewTransition().gotoView(nextView);
					
					nextView.setBoardSize(boardSize);
					nextView.setHostPlayerName(hostPlayerName);
					nextView.setGameMode(gameMode);
				});
			}
			catch (IOException e)
			{
				Platform.runLater(() ->
				{
					hostGameError(port, e);
				});
			}
		};
		
		Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		thread.start();
	}
	
	private void onClientConnected(Client client, OnlineLobbyView view, HostLobbyViewController controller)
	{
		sendGameDetailsPacket(client, view);
		hookClientNamePacket(client.getPacketHandler(), view);
		
		Platform.runLater(() ->
		{
			view.getBtnStart().setDisable(false);
		});
	}

	private void hookClientNamePacket(PacketHandler packetHandler, OnlineLobbyView view)
	{
		packetHandler.setHandler(PacketHeader.ClientName, (client, packet) ->
		{
			ClientNamePacket clientNamePacket = (ClientNamePacket) packet;
			
			Platform.runLater(() ->
			{
				view.setClientPlayerName(clientNamePacket.getName());
			});
		});
	}

	private void sendGameDetailsPacket(Client client, OnlineLobbyView view)
	{
		String hostName = view.getHostPlayerName();
		int boardSize = view.getBoardSize();
		GameMode gameMode = view.getGameMode();
		
		GameDetailsPacket gameDetailsPacket = new GameDetailsPacket(boardSize, gameMode, hostName);
		client.sendData(gameDetailsPacket);
	}

	private PacketHandler createPacketHandler(OnlineLobbyView nextView)
	{
		PacketHandler handler = new PacketHandler();
		
		handler.setHandler(PacketHeader.ClientName, (client, packet) ->
		{
			ClientNamePacket clientNamePacket = (ClientNamePacket) packet;
			
			Platform.runLater(() ->
			{
				nextView.setClientPlayerName(clientNamePacket.getName());
			});
		});
		
		return handler;
	}

	private void hostGameError(int port, IOException exception)
	{
		Alert alert = new Alert(AlertType.ERROR,
				"Error encountered when hosting on " + port + ".\n"
				+ "Reason is " + exception.getMessage(),
				ButtonType.OK);
		
		alert.setTitle("Network Error");
		alert.setHeaderText("Failed to host!");
		alert.show();
	}
}