package alalim4.sos.view.hostGame;

import alalim4.sos.view.Controller;
import alalim4.sos.view.LayoutUtil;
import alalim4.sos.view.View;
import alalim4.sos.view.ViewTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class HostGameView implements View
{
	public HostGameView(ViewTransition transition)
	{
		viewTransition = transition;

		root = new AnchorPane();
		btnBack = new Button("Back");
		btnHost = new Button("Host");
		lblMode = new Label("Game Mode");
		btnNormal = new Button("Normal");
		btnCombat = new Button("Combat");
		btnExtreme = new Button("Extreme");
		lblPlayer = new Label("Player Name");
		tfPlayer = new TextField();
		playerHBox = new HBox(10, lblPlayer, tfPlayer);
		lblPort = new Label("Online Port");
		tfPort = new TextField();
		portHBox = new HBox(10, lblPort, tfPort);
		lblSize = new Label("Board Size");
		cbSize = new ComboBox<Integer>();
		boardSizeHBox = new HBox(10, lblSize, cbSize);
		lblHelp = new Label();

		setupView();
		
		controller = new HostGameViewController(this);
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
		setupBackButton();
		setupHostButton();
		setupBoardSize();
		setupGameMode();
		setupPlayer();
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

	public Button getBtnHost()
	{
		return btnHost;
	}

	public Button getBtnNormal()
	{
		return btnNormal;
	}

	public Button getBtnExtreme()
	{
		return btnExtreme;
	}

	public Button getBtnCombat()
	{
		return btnCombat;
	}

	public TextField getTfPlayer()
	{
		return tfPlayer;
	}

	public TextField getTfPort()
	{
		return tfPort;
	}

	public ComboBox<Integer> getCbSize()
	{
		return cbSize;
	}

	public Label getLblHelp()
	{
		return lblHelp;
	}

	private void setupBackButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnBack, 0, 0, 35);
		LayoutUtil.fixateY(viewTransition.getStage(), btnBack, 1, 1, -50);
		
		btnBack.setTooltip(new Tooltip("Returns to the Multiplayer home screen."));
	}

	private void setupHostButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnHost, 1, 1, -45);
		LayoutUtil.fixateY(viewTransition.getStage(), btnHost, 1, 1, -50);
		
		btnHost.setTooltip(new Tooltip("Hosts the game."));
	}

	private void setupPlayer()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), playerHBox, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), playerHBox, 0.5, 0.5, -10);
		HBox.setMargin(lblPlayer, new Insets(10, 0, 0, 0));
		
		tfPlayer.setTooltip(new Tooltip("Player's name."));
		tfPlayer.setPromptText("Name");
	}

	private void setupPort()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), portHBox, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), portHBox, 0.5, 0.5, 70);
		HBox.setMargin(lblPort, new Insets(10, 10, 0, 0));
		
		tfPort.setText("25565");
		tfPort.setTooltip(new Tooltip("Online port is an integer value from 1028 to 65535."));
		tfPort.setPromptText("1028-65535");
	}
	
	private void setupBoardSize()
	{
		LayoutUtil.fixateX(viewTransition.getStage(),boardSizeHBox, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), boardSizeHBox, 0.5, 0.5, 150);
		HBox.setMargin(lblSize, new Insets(10, 20, 0, 0));
		
		cbSize.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10);
		cbSize.setPromptText("Select Size");
		cbSize.setMinWidth(300);
		cbSize.setTooltip(new Tooltip("Sets the game board size."));

		Callback<ListView<Integer>, ListCell<Integer>> cellFactory = new Callback<ListView<Integer>, ListCell<Integer>>()
		{
			@Override
			public ListCell<Integer> call(ListView<Integer> param)
			{
				return new ListCell<Integer>()
				{
					@Override
					protected void updateItem(Integer item, boolean empty)
					{
						super.updateItem(item, empty);
						if (item == null || empty)
						{
							setGraphic(null);
						}
						else
						{
							setText(item + " x " + item);
						}
					}
				};
			}
		};

		cbSize.setCellFactory(cellFactory);
		cbSize.setButtonCell(cellFactory.call(null));
	}

	private void setupGameMode()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), lblMode, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), lblMode, 0.5, 0, -250);
		lblMode.setStyle("-fx-underline: true;");

		LayoutUtil.fixateX(viewTransition.getStage(), btnNormal, 0.5, 0.5, -175);
		LayoutUtil.fixateY(viewTransition.getStage(), btnNormal, 0.5, 0.5, -140);
		btnNormal.setStyle("-fx-background-color: #00B2FF; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
		btnNormal.setId("selectableButton");
		btnNormal.setTooltip(new Tooltip("Normal SOS."));

		LayoutUtil.fixateX(viewTransition.getStage(), btnExtreme, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), btnExtreme, 0.5, 0.5, -140);
		btnExtreme.setId("selectableButton");
		btnExtreme.setTooltip(new Tooltip("SOS points double after each SOS."));

		LayoutUtil.fixateX(viewTransition.getStage(), btnCombat, 0.5, 0.5, 175);
		LayoutUtil.fixateY(viewTransition.getStage(), btnCombat, 0.5, 0.5, -140);
		btnCombat.setId("selectableButton");
		btnCombat.setTooltip(new Tooltip("Steal opponent's SOS."));

		ImageView imgClassical = setupGameButtonGraphic("/res/images/normal.png");
		btnNormal.setGraphic(imgClassical);

		ImageView imgExtreme = setupGameButtonGraphic("/res/images/extreme.png");
		btnExtreme.setGraphic(imgExtreme);

		ImageView imgCombat = setupGameButtonGraphic("/res/images/combat.png");
		btnCombat.setGraphic(imgCombat);
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
		root.getChildren().addAll(btnBack, btnHost, lblMode, btnNormal, btnExtreme, btnCombat,
				playerHBox, portHBox, boardSizeHBox, lblHelp);
	}

	private ImageView setupGameButtonGraphic(String graphicFilename)
	{
		ImageView img = new ImageView(graphicFilename);
		img.setFitWidth(64);
		img.setPreserveRatio(true);
		img.setSmooth(true);
		return img;
	}

	private final HBox playerHBox;
	private final HBox portHBox;
	private final HBox boardSizeHBox;
	private final ViewTransition viewTransition;
	private final AnchorPane root;
	private final Button btnBack;
	private final Button btnHost;
	private final Label lblMode;
	private final Button btnNormal;
	private final Button btnExtreme;
	private final Button btnCombat;
	private final Label lblPlayer;
	private final TextField tfPlayer;
	private final Label lblPort;
	private final TextField tfPort;
	private final Label lblSize;
	private final ComboBox<Integer> cbSize;
	private final Label lblHelp;
	private final Controller controller;
}