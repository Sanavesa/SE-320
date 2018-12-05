package alalim4.sos.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class LayoutUtil
{
	public static void fixateX(Stage stage, Region region, double positionPercentage, double anchorPercentage, double padding)
	{
		region.translateXProperty().bind(stage.widthProperty().multiply(positionPercentage).subtract(region.widthProperty().multiply(anchorPercentage)).add(padding));
	}
	
	public static void fixateY(Stage stage, Region region, double positionPercentage, double anchorPercentage, double padding)
	{
		region.translateYProperty().bind(stage.heightProperty().multiply(positionPercentage).subtract(region.heightProperty().multiply(anchorPercentage)).add(padding));
	}
	
	public static void fixateX(Stage stage, ImageView imageView, double positionPercentage, double anchorPercentage, double padding)
	{
		imageView.translateXProperty().bind(stage.widthProperty().multiply(positionPercentage).subtract(imageView.fitWidthProperty().multiply(anchorPercentage)).add(padding));
	}
	
	public static void fixateY(Stage stage, ImageView imageView, double positionPercentage, double anchorPercentage, double padding)
	{
		imageView.translateYProperty().bind(stage.heightProperty().multiply(positionPercentage).subtract(imageView.fitHeightProperty().multiply(anchorPercentage)).add(padding));
	}
}
