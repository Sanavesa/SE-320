package alalim4.sos;

import alalim4.sos.view.View;
import alalim4.sos.view.ViewTransition;
import alalim4.sos.view.home.HomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SOSProgram extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		setupStage(primaryStage);

		ViewTransition pageTransition = new ViewTransition(primaryStage);

		View initialPage = new HomeView(pageTransition);
		pageTransition.gotoView(initialPage);

		primaryStage.show();
	}

	private void setupStage(Stage stage)
	{
		stage.setTitle("SOS V4.0");

		stage.setMinWidth(600);
		stage.setWidth(600);

		stage.setMinHeight(600);
		stage.setHeight(600);

		Pane root = new Pane();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		stage.getIcons().add(new Image("/res/images/window_icon.png"));

		scene.getStylesheets().add("/res/stylesheets/style.css");
	}
}