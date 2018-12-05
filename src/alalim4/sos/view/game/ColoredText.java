package alalim4.sos.view.game;

import javafx.scene.paint.Color;

public class ColoredText
{
	public ColoredText(String textString, Color textColor)
	{
		text = textString;
		color = textColor;
	}

	public String getText()
	{
		return text;
	}

	public Color getColor()
	{
		return color;
	}

	private final String text;
	private final Color color;
}
