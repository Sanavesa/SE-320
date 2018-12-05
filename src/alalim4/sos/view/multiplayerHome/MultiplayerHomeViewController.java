package alalim4.sos.view.multiplayerHome;

import alalim4.sos.view.Controller;
import alalim4.sos.view.View;
import alalim4.sos.view.hostGame.HostGameView;
import alalim4.sos.view.joinGame.JoinGameView;

public class MultiplayerHomeViewController implements Controller
{
	public MultiplayerHomeViewController(MultiplayerHomeView multiplayerHomeView)
	{
		view = multiplayerHomeView;
	}

	@Override
	public View getView()
	{
		return view;
	}

	@Override
	public void setupController()
	{
		setupHostGameButton();
		setupJoinGameButton();
		setupBackButton();
	}
	
	private void setupHostGameButton()
	{
		view.getBtnHostGame().setOnAction(e ->
		{
			View nextView = new HostGameView(view.getViewTransition());
			view.getViewTransition().gotoView(nextView);
		});
	}

	private void setupJoinGameButton()
	{
		view.getBtnJoinGame().setOnAction(e ->
		{
			View nextView = new JoinGameView(view.getViewTransition());
			view.getViewTransition().gotoView(nextView);
		});
	}

	private void setupBackButton()
	{
		view.getBtnBack().setOnAction(e ->
		{
			view.getViewTransition().goBack();
		});
	}
	
	private final MultiplayerHomeView view;
}