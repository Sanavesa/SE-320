package alalim4.sos.view.joinGame;

import alalim4.sos.view.Controller;
import alalim4.sos.view.LayoutUtil;
import alalim4.sos.view.View;
import alalim4.sos.view.ViewTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class JoinGameView implements View
{
	public JoinGameView(ViewTransition transition)
	{
		viewTransition = transition;

		root = new AnchorPane();
		imgWaiting = new ImageView("/res/images/zoidberg_waiting.gif");
		btnBack = new Button("Back");
		btnJoin = new Button("Join");
		lblPlayer = new Label("Player Name");
		tfPlayer = new TextField();
		playerHBox = new HBox(10, lblPlayer, tfPlayer);
		lblIPAddress = new Label("IP Address");
		lblIPAddressDot1 = new Label(".");
		lblIPAddressDot2 = new Label(".");
		lblIPAddressDot3 = new Label(".");
		tfIPAddress1 = new TextField();
		tfIPAddress2 = new TextField();
		tfIPAddress3 = new TextField();
		tfIPAddress4 = new TextField();
		ipAddressHBox = new HBox(0, lblIPAddress, tfIPAddress1, lblIPAddressDot1, tfIPAddress2, 
				lblIPAddressDot2, tfIPAddress3, lblIPAddressDot3, tfIPAddress4);
		lblPort = new Label("Online Port");
		tfPort = new TextField();
		portHBox = new HBox(10, lblPort, tfPort);
		lblHelp = new Label();
		
		setupView();
		
		controller = new JoinGameViewController(this);
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
		setupWaitingImage();
		setupBackButton();
		setupJoinButton();
		setupPlayer();
		setupIPAddress();
		setupPort();
		setupHelpLabel();
	}
	
	public ViewTransition getViewTransition()
	{
		return viewTransition;
	}

	public Button getBtnBack()
	{
		return btnBack;
	}

	public Button getBtnJoin()
	{
		return btnJoin;
	}

	public TextField getTfPlayer()
	{
		return tfPlayer;
	}

	public TextField getTfPort()
	{
		return tfPort;
	}

	public TextField getTfIPAddress1()
	{
		return tfIPAddress1;
	}

	public TextField getTfIPAddress2()
	{
		return tfIPAddress2;
	}

	public TextField getTfIPAddress3()
	{
		return tfIPAddress3;
	}

	public TextField getTfIPAddress4()
	{
		return tfIPAddress4;
	}

	public Label getLblHelp()
	{
		return lblHelp;
	}

	private void setupWaitingImage()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), imgWaiting, 0.5, 0.5, 22);
		LayoutUtil.fixateY(viewTransition.getStage(), imgWaiting, 0.5, 0.5, -150);
		
		imgWaiting.setFitWidth(200);
		imgWaiting.setFitHeight(200);
	}

	private void setupBackButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnBack, 0, 0, 35);
		LayoutUtil.fixateY(viewTransition.getStage(), btnBack, 1, 1, -50);
		
		btnBack.setTooltip(new Tooltip("Returns to the Multiplayer home screen."));
	}

	private void setupJoinButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnJoin, 1, 1, -45);
		LayoutUtil.fixateY(viewTransition.getStage(), btnJoin, 1, 1, -50);

		btnJoin.setTooltip(new Tooltip("Joins the game."));
	}

	private void setupPlayer()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), playerHBox, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), playerHBox, 0.5, 0.5, -10);
		HBox.setMargin(lblPlayer, new Insets(10, 0, 0, 0));
		
		tfPlayer.setTooltip(new Tooltip("Player's name."));
		tfPlayer.setPromptText("Name");
	}
	
	private void setupIPAddress()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), ipAddressHBox, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), ipAddressHBox, 0.5, 0.5, 70);
		HBox.setMargin(lblIPAddress, new Insets(10, 30, 0, 0));
		HBox.setMargin(lblIPAddressDot1, new Insets(14, 3, 0, 3));
		HBox.setMargin(lblIPAddressDot2, new Insets(14, 3, 0, 3));
		HBox.setMargin(lblIPAddressDot3, new Insets(14, 3, 0, 3));
		
		tfIPAddress1.setText("127");
		tfIPAddress2.setText("0");
		tfIPAddress3.setText("0");
		tfIPAddress4.setText("1");
		
		tfIPAddress1.setId("ip-field");
		tfIPAddress2.setId("ip-field");
		tfIPAddress3.setId("ip-field");
		tfIPAddress4.setId("ip-field");
		
		lblIPAddress.relocate(45, 320);
		tfIPAddress1.relocate(220, 310);
		tfIPAddress2.relocate(298, 310);
		tfIPAddress3.relocate(376, 310);
		tfIPAddress4.relocate(454, 310);
		lblIPAddressDot1.relocate(290, 330);
		lblIPAddressDot2.relocate(368, 330);
		lblIPAddressDot3.relocate(446, 330);
		
		lblIPAddressDot1.setStyle("-fx-font-weight: bold;");
	}
	
	private void setupPort()
	{
		LayoutUtil.fixateX(viewTransition.getStage(),portHBox, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), portHBox, 0.5, 0.5, 150);
		HBox.setMargin(lblPort, new Insets(10, 15, 0, 0));
		
		tfPort.setText("25565");
		tfPort.setTooltip(new Tooltip("Online port is an integer value from 1028 to 65535."));
		tfPort.setPromptText("1028-65535");
	}
	
	private void setupHelpLabel()
	{
		ImageView img = new ImageView("/res/images/help.gif");
		img.setFitWidth(64);
		img.setPreserveRatio(true);
		lblHelp.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		lblHelp.setGraphic(img);
		lblHelp.setTooltip(new Tooltip("LMB-Place S\nRMB-Place O"));
		
		LayoutUtil.fixateX(viewTransition.getStage(), lblHelp, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), lblHelp, 1, 1, -50);
	}

	private void setupRoot()
	{
		root.getChildren().addAll(imgWaiting, btnBack, btnJoin, playerHBox, ipAddressHBox, portHBox, lblHelp);
	}

	private final HBox playerHBox;
	private final HBox ipAddressHBox;
	private final HBox portHBox;
	private final ViewTransition viewTransition;
	private final AnchorPane root;
	private final ImageView imgWaiting;
	private final Button btnBack;
	private final Button btnJoin;
	private final Label lblPlayer;
	private final TextField tfPlayer;
	private final Label lblPort;
	private final TextField tfPort;
	private final Label lblIPAddress;
	private final TextField tfIPAddress1, tfIPAddress2, tfIPAddress3, tfIPAddress4;
	private final Label lblIPAddressDot1, lblIPAddressDot2, lblIPAddressDot3; 
	private final Label lblHelp;
	private final Controller controller;
}