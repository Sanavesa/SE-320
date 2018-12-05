package alalim4.sos.view.onlineLobby;

import alalim4.sos.model.GameMode;
import alalim4.sos.view.Controller;
import alalim4.sos.view.LayoutUtil;
import alalim4.sos.view.View;
import alalim4.sos.view.ViewTransition;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

public class OnlineLobbyView implements View
{
	public OnlineLobbyView(ViewTransition transition)
	{
		viewTransition = transition;

		root = new AnchorPane();
		imgWaiting = new ImageView("/res/images/triangle_waiting.gif");
		listViewPlayers = new ListView<>();
		
		btnBack = new Button("Back");
		btnStart = new Button("Start");
		btnGameMode = new Button();
		btnBoardSize = new Button();
		lblLobby = new Label("Lobby");
		
		setupView();
	}
	
	@Override
	public AnchorPane getRoot()
	{
		return root;
	}

	@Override
	public void setupView()
	{
		setupRoot();
		setupBackButton();
		setupStartButton();
		setupBoardSize();
		setupGameMode();
		setupPlayersList();
		setupLobbyLabel();
		setupWaitingImage();
	}
	
	public Controller getController()
	{
		return controller;
	}
	
	public void setController(Controller viewController)
	{
		controller = viewController;
	}

	public void setBoardSize(int size)
	{
		boardSize = size;
		
		Platform.runLater(() ->
		{
			if(boardSize != -1)
				btnBoardSize.setText("Board Size\n" + boardSize + "x" + boardSize);
			else
				btnBoardSize.setText("Board Size\n" + "Unknown");
		});
	}
	
	public void setGameMode(GameMode mode)
	{
		gameMode = mode;
		
		Platform.runLater(() ->
		{
			if(gameMode == GameMode.Normal)
			{
				btnGameMode.setText("Normal");
				btnGameMode.setGraphic(setupGameButtonGraphic("/res/images/normal.png"));
			}
			else if(gameMode == GameMode.Extreme)
			{
				btnGameMode.setText("Extreme");
				btnGameMode.setGraphic(setupGameButtonGraphic("/res/images/extreme.png"));
			}
			else if(gameMode == GameMode.Combat)
			{
				btnGameMode.setText("Combat");
				btnGameMode.setGraphic(setupGameButtonGraphic("/res/images/combat.png"));
			}
			else
			{
				btnGameMode.setText("Unknown Game Mode");
				btnGameMode.setGraphic(null);
			}
		});
	}

	public void setHostPlayerName(String hostName)
	{
		hostPlayerName = hostName;
		Platform.runLater(() ->
		{
			listViewPlayers.getItems().set(0, hostName);
		});
	}
	
	public void setClientPlayerName(String clientName)
	{
		clientPlayerName = clientName;
		Platform.runLater(() ->
		{
			listViewPlayers.getItems().set(1, clientName);
		});
	}
	
	public String getClientPlayerName()
	{
		return clientPlayerName;
	}

	public int getBoardSize()
	{
		return boardSize;
	}

	public GameMode getGameMode()
	{
		return gameMode;
	}

	public String getHostPlayerName()
	{
		return hostPlayerName;
	}

	public ViewTransition getViewTransition()
	{
		return viewTransition;
	}
	
	public Button getBtnGameMode()
	{
		return btnGameMode;
	}

	public Button getBtnBoardSize()
	{
		return btnBoardSize;
	}

	public ImageView getImgWaiting()
	{
		return imgWaiting;
	}

	public Button getBtnBack()
	{
		return btnBack;
	}

	public Button getBtnStart()
	{
		return btnStart;
	}
	
	private void setupBackButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnBack, 0, 0, 35);
		LayoutUtil.fixateY(viewTransition.getStage(), btnBack, 1, 1, -50);
		
		btnBack.setTooltip(new Tooltip("Returns to the previous screen."));
	}

	private void setupStartButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnStart, 1, 1, -45);
		LayoutUtil.fixateY(viewTransition.getStage(), btnStart, 1, 1, -50);
		btnStart.setDisable(true);
		
		btnStart.setTooltip(new Tooltip("Starts the game."));
	}

	private void setupPlayersList()
	{
		listViewPlayers.setMaxHeight(120);
		listViewPlayers.setMaxWidth(230);
		listViewPlayers.setMouseTransparent(true);
		listViewPlayers.setFocusTraversable(false);
		listViewPlayers.setTooltip(new Tooltip("Players in the lobby."));
		
		listViewPlayers.getItems().addAll("Waiting...", "Waiting...");
		
		LayoutUtil.fixateX(viewTransition.getStage(), listViewPlayers, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), listViewPlayers, 0.5, 0.5, -100);
	}

	private void setupBoardSize()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnBoardSize, 0.5, 0.5, -200);
		LayoutUtil.fixateY(viewTransition.getStage(), btnBoardSize, 0.5, 0.5, -100);
		
		btnBoardSize.setTextAlignment(TextAlignment.CENTER);
		btnBoardSize.setId("selectableButton");
		
		setBoardSize(-1);
	}

	private void setupGameMode()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnGameMode, 0.5, 0.5, 200);
		LayoutUtil.fixateY(viewTransition.getStage(), btnGameMode, 0.5, 0.5, -100);
		
		btnGameMode.setId("selectableButton");
		
		setGameMode(null);
	}
	
	private void setupLobbyLabel()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), lblLobby, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), lblLobby, 0.5, 0.5, -200);
		
		lblLobby.setStyle("-fx-underline: true;");
	}
	
	private void setupWaitingImage()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), imgWaiting, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), imgWaiting, 0.5, 0.5, 110);
		
		imgWaiting.setFitWidth(200);
		imgWaiting.setFitHeight(200);
	}

	private void setupRoot()
	{
		root.getChildren().addAll(btnBack, btnStart, listViewPlayers, btnGameMode, btnBoardSize, lblLobby, imgWaiting);
	}
	
	private ImageView setupGameButtonGraphic(String graphicFilename)
	{
		ImageView img = new ImageView(graphicFilename);
		img.setFitWidth(64);
		img.setPreserveRatio(true);
		img.setSmooth(true);
		return img;
	}

	private final ViewTransition viewTransition;
	private final AnchorPane root;
	private final Button btnBack;
	private final Button btnStart;
	private final ListView<String> listViewPlayers;
	private final Button btnGameMode;
	private final Button btnBoardSize;
	private final Label lblLobby;
	private final ImageView imgWaiting;
	private Controller controller;
	
	private int boardSize = -1;
	private GameMode gameMode = null;
	private String hostPlayerName = null;
	private String clientPlayerName = null;
}