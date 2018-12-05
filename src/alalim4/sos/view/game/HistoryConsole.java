package alalim4.sos.view.game;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

public class HistoryConsole extends ListView<ColoredText>
{
	public HistoryConsole()
	{
		setCellFactory(lv -> new ListCell<ColoredText>()
		{
			@Override
			protected void updateItem(ColoredText item, boolean empty)
			{
				super.updateItem(item, empty);
				lv.setId("history-console-cell");
				if (item == null)
				{
					setText(null);
				}
				else
				{
					setText(item.getText());
					setTextFill(item.getColor());
				}
			}
		});
	}

	public void printToConsole(String message, Color color)
	{
		ColoredText entry = new ColoredText(message, color);
		getItems().add(entry);
		scrollTo(entry);
	}

	public void clearConsole()
	{
		getItems().clear();
	}
}
