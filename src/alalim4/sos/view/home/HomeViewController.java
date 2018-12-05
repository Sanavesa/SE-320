package alalim4.sos.view.home;

import alalim4.sos.view.Controller;
import alalim4.sos.view.View;
import alalim4.sos.view.gameSetup.GameSetupView;
import alalim4.sos.view.multiplayerHome.MultiplayerHomeView;
import javafx.application.Platform;

public class HomeViewController implements Controller
{
	public HomeViewController(HomeView homeView)
	{
		view = homeView;
	}
	
	@Override
	public void setupController()
	{
		setupLocalPlayButton();
		setupMultiplayerButton();
		setupQuitButton();
	}
	
	@Override
	public View getView()
	{
		return view;
	}
	
	private void setupLocalPlayButton()
	{
		view.getBtnLocalPlay().setOnAction(e ->
		{
			View nextView = new GameSetupView(view.getViewTransition());
			view.getViewTransition().gotoView(nextView);
		});
	}

	private void setupMultiplayerButton()
	{
		view.getBtnMultiplayer().setOnAction(e ->
		{
			View nextView = new MultiplayerHomeView(view.getViewTransition());
			view.getViewTransition().gotoView(nextView);
		});
	}

	private void setupQuitButton()
	{
		view.getBtnQuit().setOnAction(e ->
		{
			Platform.exit();
		});
	}
	
	private final HomeView view;
}