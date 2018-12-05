package alalim4.sos.view.hostGame;

import alalim4.sos.model.GameMode;
import alalim4.sos.view.Controller;
import alalim4.sos.view.View;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class HostGameViewController implements Controller
{
	public HostGameViewController(HostGameView hostGameView)
	{
		view = hostGameView;
		selectedGameMode = GameMode.Normal;
		selectedGameModeButton = view.getBtnNormal();
		hostGameLogic = new HostGameNetworkLogic();
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
		setupGameMode();
		setupHelpLabel();
		setupHostButton();
		setupPlayer();
		setupPort();
	}
	
	public GameMode getSelectedGameMode()
	{
		return selectedGameMode;
	}
	
	public int getPort()
	{
		return Integer.parseInt(view.getTfPort().getText());
	}
	
	public String getHostPlayerName()
	{
		return view.getTfPlayer().getText();
	}
	
	public int getBoardSize()
	{
		return view.getCbSize().getSelectionModel().getSelectedItem();
	}
	
	private void setupBackButton()
	{
		view.getBtnBack().setOnAction(e ->
		{
			view.getViewTransition().goBack();
		});
	}

	private void setupHostButton()
	{
		view.getBtnHost().disableProperty().bind(
				view.getTfPlayer().textProperty().isEmpty().or(
						view.getTfPort().textProperty().isEmpty()).or(
								view.getCbSize().valueProperty().isNull()));
		
		view.getBtnHost().setOnAction(e ->
		{
			hostGameLogic.hostGame(view, this);
		});
	}

	private void setupPlayer()
	{
		view.getTfPlayer().textProperty().addListener((args, oldText, newText) ->
		{
			if (newText.length() > 16)
				view.getTfPlayer().setText(oldText);
			else
				view.getTfPlayer().setText(newText);
		});
	}

	private void setupPort()
	{
		view.getTfPort().textProperty().addListener((args, oldText, newText) ->
		{
			if (newText.length() > 5)
			{
				view.getTfPort().setText(oldText);
			}
			else
			{
				try
				{
					int portValue = Integer.parseInt(newText);
					if(portValue < 0)
						throw new NumberFormatException();
				}
				catch(NumberFormatException e)
				{
					if(!newText.isEmpty())
					{
						view.getTfPort().setText(oldText);
					}
				}
			}
		});
	}
	
	private void setupGameMode()
	{
		setupGameModeButton(view.getBtnNormal(), GameMode.Normal);
		setupGameModeButton(view.getBtnExtreme(), GameMode.Extreme);
		setupGameModeButton(view.getBtnCombat(), GameMode.Combat);
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

	private void setupGameModeButton(Button modeButton, GameMode gameMode)
	{
		modeButton.setOnAction(e ->
		{
			if (modeButton != selectedGameModeButton)
			{
				selectedGameModeButton.setStyle("-fx-background-color: transparent; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
				selectedGameModeButton = modeButton;
				selectedGameMode = gameMode;
				modeButton.setStyle("-fx-background-color: #00B2FF; -fx-scale-x: 1.1; -fx-scale-y: 1.1;");
			}
		});

		modeButton.setOnMouseEntered(e ->
		{
			if (modeButton == selectedGameModeButton)
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
			if (modeButton == selectedGameModeButton)
			{
				modeButton.setStyle("-fx-background-color: #00B2FF; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
			}
			else
			{
				modeButton.setStyle("-fx-background-color: transparent; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
			}
		});
	}
	
	private final HostGameView view;
	private final HostGameNetworkLogic hostGameLogic;
	
	private GameMode selectedGameMode;
	private Button selectedGameModeButton;
}