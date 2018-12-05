package alalim4.sos.view.gameSetup;

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

public class GameSetupView implements View
{
	public GameSetupView(ViewTransition transition)
	{
		viewTransition = transition;

		root = new AnchorPane();
		btnBack = new Button("Back");
		btnStart = new Button("Start");
		lblMode = new Label("Game Mode");
		btnNormal = new Button("Normal");
		btnCombat = new Button("Combat");
		btnExtreme = new Button("Extreme");
		lblPlayer1 = new Label("Player 1 Name");
		tfPlayer1 = new TextField();
		player1HBox = new HBox(10, lblPlayer1, tfPlayer1);
		lblPlayer2 = new Label("Player 2 Name");
		tfPlayer2 = new TextField();
		player2HBox = new HBox(10, lblPlayer2, tfPlayer2);
		lblSize = new Label("Board Size");
		cbSize = new ComboBox<Integer>();
		boardSizeHBox = new HBox(10, lblSize, cbSize);
		lblHelp = new Label();
		
		setupView();
		
		controller = new GameSetupViewController(this);
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
		setupStartButton();
		setupBoardSize();
		setupGameMode();
		setupPlayer1();
		setupPlayer2();
		setupHelpLabel();
	}
	
	public Button getBtnBack()
	{
		return btnBack;
	}

	public Button getBtnStart()
	{
		return btnStart;
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

	public TextField getTfPlayer1()
	{
		return tfPlayer1;
	}

	public TextField getTfPlayer2()
	{
		return tfPlayer2;
	}

	public ComboBox<Integer> getCbSize()
	{
		return cbSize;
	}

	public Label getLblHelp()
	{
		return lblHelp;
	}
	
	public ViewTransition getViewTransition()
	{
		return viewTransition;
	}

	private void setupBackButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnBack, 0, 0, 35);
		LayoutUtil.fixateY(viewTransition.getStage(), btnBack, 1, 1, -50);

		btnBack.setTooltip(new Tooltip("Returns to the Home screen."));
	}

	private void setupStartButton()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), btnStart, 1, 1, -45);
		LayoutUtil.fixateY(viewTransition.getStage(), btnStart, 1, 1, -50);

		btnStart.setTooltip(new Tooltip("Starts the game."));
	}

	private void setupPlayer1()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), player1HBox, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), player1HBox, 0.5, 0.5, -10);
		HBox.setMargin(lblPlayer1, new Insets(10, 0, 0, 0));
		
		tfPlayer1.setTooltip(new Tooltip("Player 1's name."));
		tfPlayer1.setPromptText("P1 Name");
	}

	private void setupPlayer2()
	{
		LayoutUtil.fixateX(viewTransition.getStage(), player2HBox, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), player2HBox, 0.5, 0.5, 70);
		HBox.setMargin(lblPlayer2, new Insets(10, 0, 0, 0));
		
		tfPlayer2.setTooltip(new Tooltip("Player 2's name."));
		tfPlayer2.setPromptText("P2 Name");
	}

	private void setupBoardSize()
	{
		LayoutUtil.fixateX(viewTransition.getStage(),boardSizeHBox, 0.5, 0.5, 0);
		LayoutUtil.fixateY(viewTransition.getStage(), boardSizeHBox, 0.5, 0.5, 150);
		HBox.setMargin(lblSize, new Insets(10, 40, 0, 0));
		
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
		root.getChildren().addAll(btnBack, btnStart, lblMode, btnNormal, btnExtreme, btnCombat, player1HBox,
				player2HBox, boardSizeHBox, lblHelp);
	}

	private ImageView setupGameButtonGraphic(String graphicFilename)
	{
		ImageView img = new ImageView(graphicFilename);
		img.setFitWidth(64);
		img.setPreserveRatio(true);
		img.setSmooth(true);
		return img;
	}

	private final HBox player1HBox;
	private final HBox player2HBox;
	private final HBox boardSizeHBox;
	private final ViewTransition viewTransition;
	private final AnchorPane root;
	private final Button btnBack;
	private final Button btnStart;
	private final Label lblMode;
	private final Button btnNormal;
	private final Button btnExtreme;
	private final Button btnCombat;
	private final Label lblPlayer1;
	private final TextField tfPlayer1;
	private final Label lblPlayer2;
	private final TextField tfPlayer2;
	private final Label lblSize;
	private final ComboBox<Integer> cbSize;
	private final Label lblHelp;
	private final Controller controller;
}