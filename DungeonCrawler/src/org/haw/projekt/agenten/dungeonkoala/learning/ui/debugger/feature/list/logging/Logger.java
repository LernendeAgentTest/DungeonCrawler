package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.logging;

import java.util.LinkedList;
import java.util.List;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;
import org.haw.projekt.agenten.dungeonkoala.learning.QLearningBehaviourListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.FeatureSetListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.Feature_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

/**
 * Ein Logger der alle Änderungen der Features Protokolliert.
 */
public class Logger implements QLearningBehaviourListener_I, FeatureSetListener_I, GameListener_I {
	public static final Logger logger = new Logger(false);
	
	private final boolean _verbose;
	private EventDispatcher<LoggerListener> _dispatcher;
	private List<String> _log = new LinkedList<>();

	private Logger() {
		this(true);
	}
	
	/**
	 * Instanziiert einen neues Logger und bestimmt ob dieser seine Ausgaben beschränken soll({@code verbose = false}), oder nicht.
	 * @param verbose Soll der Logger alle möglichen Ausgaben tätigen?
	 */
	private Logger(boolean verbose) {
		_dispatcher = new EventDispatcher<>();
		_verbose = verbose;
	}

	public void addListener(LoggerListener l) {
		_dispatcher.addListener(l);
	}

	public void removeListener(LoggerListener l) {
		_dispatcher.removeListener(l);
	}

	public void logFeature(Feature_I f, int oldState, int newState) {
		if (oldState != newState && _verbose) {
			String message = f.getName() + ": " + oldState + " -> " + newState + " / "
					+ (f.getPossibleAllocations() - 1);
			// _log.add(message);
			System.out.println(message);
			_dispatcher.fireEvent((l) -> l.onLog(message));
		}
	}

	public List<String> getLog() {
		return _log;
	}

	@Override
	public void onActionChosen(Action action) {
		if(_verbose) {
			String message = "Chosen Action: " + action.toString();
			// _log.add(message);
			System.out.println(message);
			_dispatcher.fireEvent((l) -> l.onLog(message));
		}
	}

	@Override
	public void onStateChanged(int newState) {
		if(_verbose) {
			String message = "Current State: " + newState;
			// _log.add(message);
			System.out.println(message);
			_dispatcher.fireEvent((l) -> l.onLog(message));
		}
	}

	@Override
	public void onFeatureStateChanged(Feature_I feature, int oldState, int newState) {
		logFeature(feature, oldState, newState);
	}

	@Override
	public void onGameStarted(Player player, int rooms) {
		if(_verbose) {
			String message = "\r\n====================== GAME  STARTED ======================\r\n";
			System.out.println(message);
			_dispatcher.fireEvent((l) -> l.onLog(message));
		}
	}

	@Override
	public void onGameEnded(Player player, int rooms) {
		String message = "\r\n======================= GAME  ENDED / MADE IT TO ROOM " + rooms + " =======================\r\n";
		System.out.println(message);
		_dispatcher.fireEvent((l) -> l.onLog(message));
	}

	@Override
	public void onRoomEntered(Room room) {
		if(_verbose) {
			String message = "====================== ROOM  ENTERED ======================";
			System.out.println(message);
			_dispatcher.fireEvent((l) -> l.onLog(message));
		}
	}

	@Override
	public void onRoomCleared(Room room) {
		if(_verbose) {
			String message = "====================== ROOM  CLEARED ======================";
			System.out.println(message);
			_dispatcher.fireEvent((l) -> l.onLog(message));
		}
	}

	@Override
	public void onRoundStarted() {
		if(_verbose) {
			String message = "====================== ROUND STARTED ======================";
			System.out.println(message);
			_dispatcher.fireEvent((l) -> l.onLog(message));
		}
	}

	@Override
	public void onRoundEnded() {
		if(_verbose) {
			String message = "======================= ROUND ENDED =======================";
			System.out.println(message);
			_dispatcher.fireEvent((l) -> l.onLog(message));
		}
	}
}
