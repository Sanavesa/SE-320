package alalim4.sos.view.onlineLobby;

import alalim4.sos.networking.Client;

public class JoinLobbyViewController extends OnlineLobbyViewController
{
	public JoinLobbyViewController(OnlineLobbyView onlineLobbyView)
	{
		super(onlineLobbyView);
	}
	
	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	@Override
	protected void setupBackButton()
	{
		view.getBtnBack().setOnAction(e ->
		{
			client.disconnect();
			view.getViewTransition().goBack();
		});
	}

	@Override
	protected void setupStartButton()
	{
		view.getBtnStart().setDisable(true);
	}
	
	private Client client;
}