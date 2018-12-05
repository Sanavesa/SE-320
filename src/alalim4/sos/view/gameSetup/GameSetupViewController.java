package alalim4.sos.view.gameSetup;

import alalim4.sos.model.Board;
import alalim4.sos.model.Game;
import alalim4.sos.model.Player;
import alalim4.sos.model.gameMode.NormalGame;
import alalim4.sos.model.gameMode.CombatGame;
import alalim4.sos.model.gameMode.ExtremeGame;
import alalim4.sos.view.Controller;
import alalim4.sos.view.View;
import alalim4.sos.view.game.GameView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class GameSetupViewController implements Controller
{
	public GameSetupViewController(GameSetupView gameSetupView)
	{
		view = gameSetupView;
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
		setupGameMode();
		setupPlayer1();
		setupPlayer2();
		setupHelpLabel();
	}
	
	private void setupBackButton()
	{
		view.getBtnBack().setOnAction(e ->
		{
			view.getViewTransition().goBack();
		});
	}

	private void setupStartButton()
	{
		view.getBtnStart().setOnAction(e ->
		{
			Game game = createGameFromInput();
			View nextView = new GameView(view.getViewTransition(), game);
			view.getViewTransition().gotoView(nextView);
		});
		
		view.getBtnStart().disableProperty().bind(
				view.getTfPlayer1().textProperty().isEmpty().or(
				view.getTfPlayer2().textProperty().isEmpty().or(
				view.getCbSize().valueProperty().isNull())));
	}

	private void setupPlayer1()
	{
		view.getTfPlayer1().textProperty().addListener((args, oldText, newText) ->
		{
			if (newText.length() > 16)
				view.getTfPlayer1().setText(oldText);
			else
				view.getTfPlayer1().setText(newText);
		});
	}

	private void setupPlayer2()
	{
		view.getTfPlayer2().textProperty().addListener((args, oldText, newText) ->
		{
			if (newText.length() > 16)
				view.getTfPlayer2().setText(oldText);
			else
				view.getTfPlayer2().setText(newText);
		});
	}

	private void setupGameMode()
	{
		setupGameModeButton(view.getBtnNormal());
		setupGameModeButton(view.getBtnExtreme());
		setupGameModeButton(view.getBtnCombat());
		
		modeSelection = view.getBtnNormal();
	}

	private void setupHelpLabel()
	{
		view.getLblHelp().setOnMouseClicked(e ->
		{
			Alert alert = new Alert(AlertType.INFORMATION,
					"Controls:\n"
					+ "Left Mouse Click: Places a S\n"
					+ "Right Mouse Click: Places an O\n\n"
					+ "Game Rules:\n"
					+ "Objective of the game is to create as many horizontally, vertically, or diagonally consecutive SOSes.\n"
					+ "If you make an SOS, you get to play another turn.",
					ButtonType.OK);
			
			alert.setTitle("Help");
			alert.setHeaderText("How To Play:");
			alert.show();
		});
	}
	
	private void setupGameModeButton(Button modeButton)
	{
		modeButton.setOnAction(e ->
		{
			if (modeButton != modeSelection)
			{
				modeSelection.setStyle("-fx-background-color: transparent; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
				modeSelection = modeButton;
				modeButton.setStyle("-fx-background-color: #00B2FF; -fx-scale-x: 1.1; -fx-scale-y: 1.1;");
			}
		});

		modeButton.setOnMouseEntered(e ->
		{
			if (modeButton == modeSelection)
			{
				modeButton.setStyle("-fx-background-color: #00B2FF; -fx-scale-x: 1.1; -fx-scale-y: 1.1;");
			}
			else
			{
				modeButton.setStyle("-fx-background-color: #FAE03C; -fx-scale-x: 1.1; -fx-scale-y: 1.1;");
			}
		});

		modeButton.setOnMouseExited(e ->
		{
			if (modeButton == modeSelection)
			{
				modeButton.setStyle("-fx-background-color: #00B2FF; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
			}
			else
			{
				modeButton.setStyle("-fx-background-color: transparent; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
			}
		});
	}

	private Game createGameFromInput()
	{
		Board board = new Board(view.getCbSize().getValue());
		Player p1 = new Player(view.getTfPlayer1().getText());
		Player p2 = new Player(view.getTfPlayer2().getText());
		Game game = null;

		if (modeSelection == view.getBtnNormal())
		{
			game = new NormalGame(board, p1, p2, true);
		}
		else if (modeSelection == view.getBtnCombat())
		{
			game = new CombatGame(board, p1, p2, true);
		}
		else
		{
			game = new ExtremeGame(board, p1, p2, true);
		}

		return game;
	}
	
	private final GameSetupView view;
	private Button modeSelection;
}