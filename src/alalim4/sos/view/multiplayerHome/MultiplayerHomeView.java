package alalim4.sos.view.multiplayerHome;

import java.net.InetAddress;
import java.net.UnknownHostException;

import alalim4.sos.view.Controller;
import alalim4.sos.view.LayoutUtil;
import alalim4.sos.view.View;
import alalim4.sos.view.ViewTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MultiplayerHomeView implements View
{
	public MultiplayerHomeView(ViewTransition transition)
	{
		viewTransition = transition;

		root = new AnchorPane();

		imgLogo = new ImageView("/res/images/sos.gif");
		btnHostGame = new Button("Host Game");
		btnJoinGame = new Button("Join Game");
		btnBack = new Button("Back");
		lblIpAddress = new Label();

		setupView();
		
		controller = new MultiplayerHomeViewController(this);
		controller.setupController();
	}

	@Override
	public Pane getRoot()
	{
		return root;
	}
	
	@Override
	public void setupView()
	{
		setupRoot();
		setupLogoImage();
		setupHostGameButton();
		setupJoinGameButton();
		setupBackButton();
		setupIPAdressLabel();
	}

	public Button getBtnHostGame()
	{
		return btnHostGame;
	}

	public Button getBtnJoinGame()
	{
		return btnJoinGame;
	}

	public Button getBtnBack()
	{
		return btnBack;
	}

	public ViewTransition getViewTransition()
	{
		return viewTransition;
	}

	private void setupLogoImage()
	{
		imgLogo.setFitWidth(400);
		imgLogo.setFitHeight(200);
		LayoutUtil.fixateX(viewTransition.getStage(), imgLogo, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), imgLogo, 0, 0, 30);
	}

	private void setupHostGameButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnHostGame, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), btnHostGame, 0.5, 0.5, 0);
		btnHostGame.setId("wide-button");
		btnHostGame.setTooltip(new Tooltip("Host a multiplayer game."));
	}

	private void setupJoinGameButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnJoinGame, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), btnJoinGame, 0.5, 0.5, 80);
		btnJoinGame.setId("wide-button");
		btnJoinGame.setTooltip(new Tooltip("Join a multiplayer game."));
	}

	private void setupBackButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnBack, 0, 0, 35);
		LayoutUtil.fixateY(viewTransition.getStage(), btnBack, 1, 1, -50);
		btnBack.setTooltip(new Tooltip("Returns to the Main Menu."));
	}
	
	private void setupIPAdressLabel()
	{
		String ip;
		try
		{
			ip = InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e)
		{
			ip = "UNKNOWN";
		}
		
		LayoutUtil.fixateX(viewTransition.getStage(), lblIpAddress, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), lblIpAddress, 0.5, 0.5, 141);
		lblIpAddress.setText("Your IP is " + ip.toString());
	}

	private void setupRoot()
	{
		root.getChildren().addAll(imgLogo, btnHostGame, btnJoinGame, btnBack, lblIpAddress);
	}

	private final AnchorPane root;
	private final ImageView imgLogo;
	private final Button btnHostGame;
	private final Button btnJoinGame;
	private final Button btnBack;
	private final Label lblIpAddress;
	private final ViewTransition viewTransition;
	private final Controller controller;
}