package alalim4.sos.view.joinGame;

import alalim4.sos.view.Controller;
import alalim4.sos.view.View;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class JoinGameViewController implements Controller
{
	public JoinGameViewController(JoinGameView joinGameView)
	{
		view = joinGameView;
		joinGameLogic = new JoinGameNetworkLogic();
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
		setupJoinButton();
		setupHelpLabel();
		setupIPAddress();
		setupPlayer();
		setupPort();
	}
	
	public String getIPAdress()
	{
		return 	String.format("%s.%s.%s.%s",
				view.getTfIPAddress1().getText(),
				view.getTfIPAddress2().getText(),
				view.getTfIPAddress3().getText(),
				view.getTfIPAddress4().getText());
	}
	
	public int getPort()
	{
		return Integer.parseInt(view.getTfPort().getText());
	}
	
	public String getClientPlayerName()
	{
		return view.getTfPlayer().getText();
	}
	
	private void setupBackButton()
	{
		view.getBtnBack().setOnAction(e ->
		{
			view.getViewTransition().goBack();
		});
	}
	
	private void setupJoinButton()
	{
		view.getBtnJoin().disableProperty().bind(
				view.getTfPlayer().textProperty().isEmpty().or(
				view.getTfIPAddress1().textProperty().isEmpty()).or(
				view.getTfIPAddress2().textProperty().isEmpty()).or(
				view.getTfIPAddress3().textProperty().isEmpty()).or(
				view.getTfIPAddress4().textProperty().isEmpty()).or(
				view.getTfPort().textProperty().isEmpty()));
		
		view.getBtnJoin().setOnAction(e ->
		{
			joinGameLogic.joinGame(view, this);
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
	
	private void setupIPAddress()
	{
		setupIPAddressTextField(view.getTfIPAddress1());
		setupIPAddressTextField(view.getTfIPAddress2());
		setupIPAddressTextField(view.getTfIPAddress3());
		setupIPAddressTextField(view.getTfIPAddress4());
	}
	
	private void setupIPAddressTextField(TextField textField)
	{
		textField.textProperty().addListener((args, oldText, newText) ->
		{
			if (newText.length() > 3)
			{
				textField.setText(oldText);
			}
			else
			{
				try
				{
					int ipValue = Integer.parseInt(newText);
					if(ipValue < 0 || ipValue > 255)
						throw new NumberFormatException();
				}
				catch(NumberFormatException e)
				{
					if(!newText.isEmpty())
					{
						textField.setText(oldText);
					}
				}
			}
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
					if(portValue < 0 || portValue > 65535)
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
	
	private final JoinGameView view;
	private final JoinGameNetworkLogic joinGameLogic; 
}