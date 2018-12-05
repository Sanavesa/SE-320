package alalim4.sos.view.home;

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

public class HomeView implements View
{
	public HomeView(ViewTransition transition)
	{
		viewTransition = transition;

		root = new AnchorPane();
		imgLogo = new ImageView("/res/images/sos.gif");
		btnLocalPlay = new Button("Local Play");
		btnMultiplayer = new Button("Multiplayer");
		btnQuit = new Button("Quit");
		lblCredits = new Label("By Mohammad Alali");
		setupView();
		
		controller = new HomeViewController(this);
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
		setupLocalPlayButton();
		setupMultiplayerButton();
		setupQuitButton();
		setupCreditsLabel();
	}
	
	public Button getBtnLocalPlay()
	{
		return btnLocalPlay;
	}

	public Button getBtnMultiplayer()
	{
		return btnMultiplayer;
	}

	public Button getBtnQuit()
	{
		return btnQuit;
	}

	public ViewTransition getViewTransition()
	{
		return viewTransition;
	}

	private void setupLocalPlayButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnLocalPlay, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), btnLocalPlay, 0.5, 0.5, 0);
		btnLocalPlay.setId("wide-button");
		btnLocalPlay.setTooltip(new Tooltip("Play locally."));
	}

	private void setupMultiplayerButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnMultiplayer, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), btnMultiplayer, 0.5, 0.5, 80);
		btnMultiplayer.setId("wide-button");
		btnMultiplayer.setTooltip(new Tooltip("Play online."));
	}

	private void setupQuitButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnQuit, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), btnQuit, 0.5, 0.5, 160);
		btnQuit.setId("wide-button");
		btnQuit.setTooltip(new Tooltip("Exits the game."));
	}

	private void setupLogoImage()
	{
		imgLogo.setFitWidth(400);
		imgLogo.setFitHeight(200);
		LayoutUtil.fixateX(viewTransition.getStage(), imgLogo, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), imgLogo, 0, 0, 30);
	}

	private void setupCreditsLabel()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), lblCredits, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), lblCredits, 1, 1, -38);
		lblCredits.setTooltip(new Tooltip("For Fall 2018 SE 320 Class."));
	}

	private void setupRoot()
	{
		root.getChildren().addAll(imgLogo, btnLocalPlay, btnMultiplayer, btnQuit, lblCredits);
	}
	
	private final AnchorPane root;
	private final ImageView imgLogo;
	private final Button btnLocalPlay;
	private final Button btnMultiplayer;
	private final Button btnQuit;
	private final Label lblCredits;
	private final ViewTransition viewTransition;
	private final Controller controller;
}