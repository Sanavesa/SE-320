package alalim4.sos.view.game;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import alalim4.sos.model.Game;
import alalim4.sos.view.Controller;
import alalim4.sos.view.LayoutUtil;
import alalim4.sos.view.View;
import alalim4.sos.view.ViewTransition;

public class GameView implements View
{
	public GameView(ViewTransition transition, Game sosGame)
	{
		viewTransition = transition;
		game = sosGame;
		
		turnEffect = new DropShadow(12, 0, 0, Color.BLACK);
		
		root = new AnchorPane();
		btnBack = new Button("Back");
		btnRestart = new Button("Restart");
		lblClock = new Label("0s");
		historyConsole = new HistoryConsole();
		lblPlayer1 = new Label("");
		lblPlayer1Score = new Label("(0)");
		lblPlayer2 = new Label("");
		lblPlayer2Score = new Label("(0)");
		lblVersus = new Label("VS");
		lblGameMode = new Label(game.getGameMode().toString());
		gameBoard = new GraphicalGameBoard(game, (int) (30 * (10.0 / game.getBoard().getNumCols())), transition.getStage());

		setupView();
		
		controller = new GameViewController(this);
		controller.setupController();
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
		setupGameModeLabel();
		setupBackButton();
		setupRestartButton();
		setupPlayer1();
		setupPlayer2();
		setupVersus();
		setupHistory();
		setupGameBoard();
		setupClock();
	}

	public ViewTransition getViewTransition()
	{
		return viewTransition;
	}

	public Button getBtnBack()
	{
		return btnBack;
	}

	public Button getBtnRestart()
	{
		return btnRestart;
	}

	public Label getLblClock()
	{
		return lblClock;
	}

	public HistoryConsole getHistoryConsole()
	{
		return historyConsole;
	}

	public Label getLblPlayer1()
	{
		return lblPlayer1;
	}

	public Label getLblPlayer1Score()
	{
		return lblPlayer1Score;
	}

	public Label getLblPlayer2()
	{
		return lblPlayer2;
	}

	public Label getLblPlayer2Score()
	{
		return lblPlayer2Score;
	}

	public GraphicalGameBoard getGameBoard()
	{
		return gameBoard;
	}

	public Game getGame()
	{
		return game;
	}
	
	public Effect getTurnEffect()
	{
		return turnEffect;
	}
	
	private void setupGameModeLabel()
	{
		lblGameMode.setStyle("-fx-font-size: 24px;");
		
		LayoutUtil.fixateX(viewTransition.getStage(), lblGameMode, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), lblGameMode, 0, 0, 0);
	}

	private void setupGameBoard()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), gameBoard, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), gameBoard, 0.5, 0.5, -80);
	}

	private void setupVersus()
	{
		lblVersus.setStyle("-fx-font-size: 48px;");
		
		LayoutUtil.fixateX(viewTransition.getStage(), lblVersus, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), lblVersus, 0, 0, 15);
	}

	private void setupPlayer1()
	{
		lblPlayer1.setText(game.getPlayer1().getName());
		lblPlayer1.setStyle("-fx-text-fill: red");
		lblPlayer1Score.setStyle("-fx-text-fill: red");
		
		LayoutUtil.fixateX(viewTransition.getStage(), lblPlayer1, 0, 0, 15);
		LayoutUtil.fixateY(viewTransition.getStage(), lblPlayer1, 0, 0, 10);
		LayoutUtil.fixateX(viewTransition.getStage(), lblPlayer1Score, 0, 0, 15);
		LayoutUtil.fixateY(viewTransition.getStage(), lblPlayer1Score, 0, 0, 35);
	}

	private void setupPlayer2()
	{
		lblPlayer2.setText(game.getPlayer2().getName());
		lblPlayer2.setStyle("-fx-text-fill: blue");
		lblPlayer2Score.setStyle("-fx-text-fill: blue");
		
		LayoutUtil.fixateX(viewTransition.getStage(), lblPlayer2, 1, 1, -30);
		LayoutUtil.fixateY(viewTransition.getStage(), lblPlayer2, 0, 0, 10);
		LayoutUtil.fixateX(viewTransition.getStage(), lblPlayer2Score, 1, 1, -30);
		LayoutUtil.fixateY(viewTransition.getStage(), lblPlayer2Score, 0, 0, 35);
	}

	private void setupHistory()
	{
		historyConsole.minWidthProperty().bind(viewTransition.getStage().widthProperty().divide(600.0/510.0));
		historyConsole.maxWidthProperty().bind(viewTransition.getStage().widthProperty().divide(600.0/510.0));
		historyConsole.minHeightProperty().bind(viewTransition.getStage().heightProperty().divide(600.0/100.0));
		historyConsole.maxHeightProperty().bind(viewTransition.getStage().heightProperty().divide(600.0/100.0));
		
		LayoutUtil.fixateX(viewTransition.getStage(), historyConsole, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), historyConsole, 1, 1, -120);
	}

	private void setupClock()
	{
		lblClock.setStyle("-fx-font-size: 26px;");
		lblClock.setTooltip(new Tooltip("Time in seconds since the game started."));
		
		LayoutUtil.fixateX(viewTransition.getStage(), lblClock, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), lblClock, 1, 1, -50);
	}

	private void setupBackButton()
	{
		btnBack.setTooltip(new Tooltip("Returns to the Game Lobby."));
		
		LayoutUtil.fixateX(viewTransition.getStage(), btnBack, 0, 0, 35);
		LayoutUtil.fixateY(viewTransition.getStage(), btnBack, 1, 1, -50);
	}
	
	private void setupRestartButton()
	{
		btnRestart.setTooltip(new Tooltip("Restarts the game."));
		
		LayoutUtil.fixateX(viewTransition.getStage(), btnRestart, 1, 1, -45);
		LayoutUtil.fixateY(viewTransition.getStage(), btnRestart, 1, 1, -50);
	}

	private void setupRoot()
	{
		root.getChildren().addAll(lblGameMode, lblClock, lblVersus, lblPlayer1, lblPlayer2, lblPlayer1Score, lblPlayer2Score,
				gameBoard, historyConsole, btnBack, btnRestart);
	}

	private final ViewTransition viewTransition;
	private final AnchorPane root;
	private final Button btnBack;
	private final Button btnRestart;
	private final Label lblClock;
	private final HistoryConsole historyConsole;
	private final Label lblPlayer1;
	private final Label lblPlayer1Score;
	private final Label lblPlayer2;
	private final Label lblPlayer2Score;
	private final Label lblVersus;
	private final GraphicalGameBoard gameBoard;
	private final Game game;
	private final Controller controller;
	private final Effect turnEffect;
	private final Label lblGameMode;
}