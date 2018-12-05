package alalim4.sos.view.game;

import java.util.HashMap;
import java.util.Map;

import alalim4.sos.model.BoardCell;
import alalim4.sos.model.Game;
import alalim4.sos.model.GameSymbol;
import alalim4.sos.model.Player;
import alalim4.sos.model.SOS;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GraphicalGameBoard extends StackPane
{
	public GraphicalGameBoard(Game sosGame, int cellSizePixels, Stage stage)
	{
		game = sosGame;
		cellSize = cellSizePixels;

		gpBoard = new GridPane();
		apLines = new AnchorPane();
		
		getChildren().addAll(gpBoard, apLines);
		scaleXProperty().bind(scaleYProperty());
		scaleYProperty().bind(stage.heightProperty().divide(600.0));
		
		sosToLine = new HashMap<SOS, Line>();

		int boardSize = game.getBoard().getNumCols();
		boardCells = new GraphicalBoardCell[boardSize][boardSize];

		setupBoard();
		setupLines();
	}

	public AnchorPane getLinesAnchorPane()
	{
		return apLines;
	}

	public GridPane getGameBoardGridPane()
	{
		return gpBoard;
	}

	public void drawSOS(SOS sos, Color color)
	{
		BoardCell startCell = sos.getStartCell();
		BoardCell endCell = sos.getEndCell();
		GraphicalBoardCell graphicalStartCell = boardCells[startCell.getRow()][startCell.getColumn()];
		GraphicalBoardCell graphicalEndCell = boardCells[endCell.getRow()][endCell.getColumn()];

		Line line = new Line(graphicalStartCell.getX(), graphicalStartCell.getY(), graphicalEndCell.getX(),
				graphicalEndCell.getY());

		line.setStrokeWidth(3 * (cellSize / 30));
		line.setEffect(new DropShadow());
		line.setStroke(color);

		sosToLine.put(sos, line);

		apLines.getChildren().add(line);
	}

	public void removeSOS(SOS sos)
	{
		Line line = sosToLine.get(sos);
		if (line != null)
		{
			apLines.getChildren().remove(line);
		}
	}
	
	public void drawSymbol(int row, int column, GameSymbol symbol)
	{
		BoardCell cell = game.getBoard().getAt(row, column);
		GraphicalBoardCell boardCell = boardCells[row][column];

		Rectangle rect = boardCell.getRect();
		Text text = boardCell.getText();
		
		if(text.getText().isEmpty())
		{
			Player player = game.getCurrentPlayerTurn();
			game.play(row, column, symbol);

			rect.setScaleX(1.0);
			rect.setScaleY(1.0);
			rect.setFill(Color.rgb(140, 140, 140));

			text.setText(cell.getSymbol().toString());

			if (game.getPlayer1().equals(player))
			{
				text.setFill(Color.RED);
			}
			else
			{
				text.setFill(Color.BLUE);
			}
		}
	}

	public void resetVisuals()
	{
		apLines.getChildren().clear();
		sosToLine.clear();

		int boardSize = game.getBoard().getNumCols();
		for (int row = 0; row < boardSize; row++)
		{
			for (int column = 0; column < boardSize; column++)
			{
				GraphicalBoardCell boardCell = boardCells[row][column];
				Rectangle rect = boardCell.getRect();
				rect.setFill(Color.rgb(180, 180, 180));

				Text text = boardCell.getText();
				text.setFill(Color.BLACK);
				text.setText("");
			}
		}
	}

	private void setupBoard()
	{
		gpBoard.setFocusTraversable(false);

		int boardSize = game.getBoard().getNumCols();

		for (int row = 0; row < boardSize; row++)
		{
			for (int column = 0; column < boardSize; column++)
			{
				int x = cellSize / 2 + cellSize * column;
				int y = cellSize / 2 + cellSize * row;

				GraphicalBoardCell boardCell = new GraphicalBoardCell(x, y, cellSize);
				boardCells[row][column] = boardCell;
				gpBoard.add(boardCell, column, row);

				setupCellLogic(row, column);
			}
		}
	}

	private void setupLines()
	{
		apLines.setMouseTransparent(true);
	}

	private void setupCellLogic(int row, int column)
	{
		BoardCell cell = game.getBoard().getAt(row, column);
		GraphicalBoardCell boardCell = boardCells[row][column];

		Rectangle rect = boardCell.getRect();
		Text text = boardCell.getText();

		rect.setOnMouseClicked(e ->
		{
			if (cell.getSymbol() == GameSymbol.Empty)
			{
				GameSymbol symbol = (e.getButton() == MouseButton.PRIMARY) ? GameSymbol.S : GameSymbol.O;

				Player player = game.getCurrentPlayerTurn();
				game.play(row, column, symbol);

				rect.setScaleX(1.0);
				rect.setScaleY(1.0);
				rect.setFill(Color.rgb(140, 140, 140));

				text.setText(cell.getSymbol().toString());

				if (game.getPlayer1().equals(player))
				{
					text.setFill(Color.RED);
				}
				else
				{
					text.setFill(Color.BLUE);
				}
			}
		});

		rect.setOnMouseEntered(e ->
		{
			if (cell.getSymbol() == GameSymbol.Empty)
			{
				rect.setScaleX(1.02);
				rect.setScaleY(1.02);
				rect.setFill(Color.rgb(215, 215, 215));
			}
		});

		rect.setOnMouseExited(e ->
		{
			if (cell.getSymbol() == GameSymbol.Empty)
			{
				rect.setScaleX(1.0);
				rect.setScaleY(1.0);
				rect.setFill(Color.rgb(180, 180, 180));
			}
		});
	}

	private final Map<SOS, Line> sosToLine;
	private final AnchorPane apLines;
	private final GridPane gpBoard;
	private final int cellSize;
	private final Game game;
	private final GraphicalBoardCell[][] boardCells;
}
