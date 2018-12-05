package alalim4.sos.view.game;

import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import alalim4.sos.model.GameState;
import alalim4.sos.model.Player;
import alalim4.sos.model.SOS;
import alalim4.sos.model.TurnDecision;
import alalim4.sos.view.Controller;
import alalim4.sos.view.View;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.util.Duration;

public class GameViewController implements Controller
{
	public GameViewController(GameView gameView)
	{
		view = gameView;
		
		tlClock = new Timeline();
		sosSoundClip = loadSOSAudio("sos.mp3");
		time = 0;
		lastSosMadeTime = System.currentTimeMillis();
		soundExecutor = createSoundExecutor();
	}

	@Override
	public View getView()
	{
		return view;
	}

	@Override
	public void setupController()
	{
		setupClock();
		setupBackButton();
		setupRestartButton();
		
		setupModel();
		
		view.getGame().restartGame();
	}
	
	private void setupClock()
	{
		tlClock.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e ->
		{
			time++;
			view.getLblClock().setText(time + "s");
		}));
		tlClock.setCycleCount(Timeline.INDEFINITE);
		tlClock.play();
	}
	
	private void setupBackButton()
	{
		view.getBtnBack().setOnAction(e ->
		{
			view.getViewTransition().goBack();
		});
	}
	
	private void setupRestartButton()
	{
		view.getBtnRestart().setOnAction(e ->
		{
			view.getGame().restartGame();
		});
	}
	
	private void setupModel()
	{
		view.getGame().setOnTurnBegan(player ->
		{
			Platform.runLater(() ->
			{
				onTurnBegan(player);
			});
		});

		view.getGame().setOnTurnEnded(player ->
		{
			Platform.runLater(() ->
			{
				onTurnEnded(player);
			});
		});

		view.getGame().setOnPlayedTurn((player, turnDecision) ->
		{
			Platform.runLater(() ->
			{
				setOnPlayedTurn(player, turnDecision);
			});
		});

		view.getGame().setOnGameEnded(state ->
		{
			Platform.runLater(() ->
			{
				setOnGameEnded(state);
			});
		});

		view.getGame().setOnRestartedGame(() ->
		{
			Platform.runLater(() ->
			{
				setOnRestartedGame();
			});
		});

		view.getGame().setOnAddedSOS((player, sos) ->
		{
			Platform.runLater(() ->
			{
				setOnAddedSOS(player, sos);
			});
		});

		view.getGame().setOnRemovedSOS((player, sos) ->
		{
			Platform.runLater(() ->
			{
				setOnRemovedSOS(player, sos);
			});
		});
	}

	private void setOnRemovedSOS(Player player, SOS sos)
	{
		view.getGameBoard().removeSOS(sos);
		view.getHistoryConsole().printToConsole("\t\t\t" + player.getName() + " lost an SOS!", Color.BLACK);
	}

	private void setOnAddedSOS(Player player, SOS sos)
	{
		Color lineColor = (view.getGame().getPlayer1().equals(player)) ? Color.RED : Color.BLUE;
		view.getGameBoard().drawSOS(sos, lineColor);
		playSOSAudio();
		view.getHistoryConsole().printToConsole("\t\t\t" + player.getName() + " gained an SOS!", Color.BLACK);

		view.getLblPlayer1Score().setText(String.format("(%.0f)", view.getGame().getPlayer1().getScore()));
		view.getLblPlayer2Score().setText(String.format("(%.0f)", view.getGame().getPlayer2().getScore()));
		view.getHistoryConsole().printToConsole(
				String.format("\t\t%s's score is %.0f", view.getGame().getPlayer1().getName(), view.getGame().getPlayer1().getScore()),
				Color.RED);
		view.getHistoryConsole().printToConsole(
				String.format("\t\t%s's score is %.0f", view.getGame().getPlayer2().getName(), view.getGame().getPlayer2().getScore()),
				Color.BLUE);
	}

	private void setOnRestartedGame()
	{
		view.getLblPlayer1Score().setText("(0)");
		view.getLblPlayer2Score().setText("(0)");

		view.getGameBoard().resetVisuals();
		time = 0;
		view.getLblClock().setText("0s");
		tlClock.playFromStart();
		view.getHistoryConsole().clearConsole();
	}

	private void setOnGameEnded(GameState state)
	{
		view.getHistoryConsole().printToConsole("[" + time + "s] - Turn Ended for " + view.getGame().getCurrentPlayerTurn().getName(), Color.BLACK);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game is over!");
		alert.setHeaderText("Game is over!");
		alert.setGraphic(null);
		alert.initModality(Modality.APPLICATION_MODAL);

		if (state == GameState.Tie)
		{
			alert.setContentText("It is a tie!");
			view.getHistoryConsole().printToConsole("[" + time + "s] - Game Ended: Tie!", Color.BLACK);
		}
		else if (state == GameState.WinP1)
		{
			alert.setContentText(view.getGame().getPlayer1().getName() + " wins!");
			view.getHistoryConsole().printToConsole("[" + time + "s] - Game Ended: " + view.getGame().getPlayer1().getName() + " wins!", Color.RED);
		}
		else if (state == GameState.WinP2)
		{
			alert.setContentText(view.getGame().getPlayer2().getName() + " wins!");
			view.getHistoryConsole().printToConsole("[" + time + "s] - Game Ended: " + view.getGame().getPlayer2().getName() + " wins!", Color.BLUE);
		}
		alert.setResizable(false);
		alert.setResult(ButtonType.OK);
		alert.show();

		view.getHistoryConsole().printToConsole("Game took " + time + " seconds!", Color.BLACK);

		tlClock.stop();
	}

	private void setOnPlayedTurn(Player player, TurnDecision turnDecision)
	{
		view.getLblPlayer1Score().setText(String.format("(%.0f)", view.getGame().getPlayer1().getScore()));
		view.getLblPlayer2Score().setText(String.format("(%.0f)", view.getGame().getPlayer2().getScore()));
		view.getHistoryConsole().printToConsole(String.format("\t[" + time + "s] - %s placed an %s at (%d, %d)", player.getName(),
				turnDecision.getSymbol().toString(), turnDecision.getColumn() + 1,
				view.getGame().getBoard().getNumRows() - turnDecision.getRow()), Color.BLACK);
		view.getHistoryConsole().printToConsole(
				String.format("\t\t%s's score is %.0f", view.getGame().getPlayer1().getName(), view.getGame().getPlayer1().getScore()),
				Color.RED);
		view.getHistoryConsole().printToConsole(
				String.format("\t\t%s's score is %.0f", view.getGame().getPlayer2().getName(), view.getGame().getPlayer2().getScore()),
				Color.BLUE);
	}

	private void onTurnEnded(Player player)
	{
		if(player == null)
			return;
		
		view.getHistoryConsole().printToConsole("[" + time + "s] - Turn Ended for " + player.getName(), Color.BLACK);
	}

	private void onTurnBegan(Player player)
	{
		if(player == null)
			return;
		
		view.getHistoryConsole().printToConsole("[" + time + "s] - Turn Started for " + player.getName(), Color.BLACK);
		if (view.getGame().getPlayer1().equals(player))
		{
			view.getLblPlayer1().setEffect(view.getTurnEffect());
			view.getLblPlayer1Score().setEffect(view.getTurnEffect());
			view.getLblPlayer2().setEffect(null);
			view.getLblPlayer2Score().setEffect(null);
			
			view.getLblPlayer1().setStyle("-fx-font-size: 30px; -fx-text-fill: red");
			view.getLblPlayer1Score().setStyle("-fx-font-size: 30px; -fx-text-fill: red");
			view.getLblPlayer2().setStyle("-fx-font-size: 20px; -fx-text-fill: blue");
			view.getLblPlayer2Score().setStyle("-fx-font-size: 20px; -fx-text-fill: blue");
		}
		else
		{
			view.getLblPlayer1().setEffect(null);
			view.getLblPlayer1Score().setEffect(null);
			view.getLblPlayer2().setEffect(view.getTurnEffect());
			view.getLblPlayer2Score().setEffect(view.getTurnEffect());
			
			view.getLblPlayer1().setStyle("-fx-font-size: 20px; -fx-text-fill: red");
			view.getLblPlayer1Score().setStyle("-fx-font-size: 20px; -fx-text-fill: red");
			view.getLblPlayer2().setStyle("-fx-font-size: 30px; -fx-text-fill: blue");
			view.getLblPlayer2Score().setStyle("-fx-font-size: 30px; -fx-text-fill: blue");
		}
	}
	
	private AudioClip loadSOSAudio(String audioFilename)
	{
		String soundFilename = "src/res/sounds/" + audioFilename;
		return new AudioClip(Paths.get(soundFilename).toUri().toString());
	}

	private void playSOSAudio()
	{
		soundExecutor.execute(() ->
		{
			while((System.currentTimeMillis() - lastSosMadeTime < 350))
			{
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException e){}
			}
			
			sosSoundClip.play();
			lastSosMadeTime = System.currentTimeMillis();
		});
	}
	
	private ExecutorService createSoundExecutor()
	{
		return Executors.newFixedThreadPool(5, new ThreadFactory()
		{
			@Override
			public Thread newThread(Runnable r)
			{
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});
	}
	
	private final GameView view;
	private final ExecutorService soundExecutor;
	private final Timeline tlClock;
	private final AudioClip sosSoundClip;
	
	private volatile long lastSosMadeTime;
	private int time;
}