package alalim4.sos.view.game;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GraphicalBoardCell extends StackPane
{
	public GraphicalBoardCell(int xpos, int ypos, int cellSizePixels)
	{
		cellSize = cellSizePixels;

		rect = new Rectangle(cellSize, cellSize, Color.rgb(180, 180, 180));
		text = new Text("");

		x = xpos;
		y = ypos;
		getChildren().addAll(rect, text);

		setupRectangle();
		setupText();
	}

	public Rectangle getRect()
	{
		return rect;
	}

	public Text getText()
	{
		return text;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	private void setupRectangle()
	{
		rect.setPickOnBounds(true);
		rect.setId("board-cell");
	}

	private void setupText()
	{
		int fontSize = (int) (22 * (cellSize / 30.0));
		text.setFill(Color.BLACK);
		text.setStyle("-fx-font-size: " + fontSize + "px;");
		text.setMouseTransparent(true);
	}

	private final Rectangle rect;
	private final Text text;
	private final int x;
	private final int y;
	private final int cellSize;
}
