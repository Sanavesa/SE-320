package alalim4.sos.view;

import java.util.Deque;
import java.util.LinkedList;

import javafx.stage.Stage;

public class ViewTransition
{
	public ViewTransition(Stage primaryStage)
	{
		stage = primaryStage;
		views = new LinkedList<View>();
	}

	public View getCurrentView()
	{
		return views.getLast();
	}

	public void goBack()
	{
		if (views.size() <= 1)
			return;

		views.removeLast();
		loadLastViewOntoStage();
	}

	public void gotoView(View view)
	{
		views.addLast(view);
		loadLastViewOntoStage();
	}

	private void loadLastViewOntoStage()
	{
		View lastPage = getCurrentView();
		stage.getScene().setRoot(lastPage.getRoot());
	}
	
	public Stage getStage()
	{
		return stage;
	}

	private final Stage stage;
	private final Deque<View> views;
}