package org.haw.projekt.agenten.dungeonkoala.game;

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;
import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.Enemy;
import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.generator.SimpleEnemyGenerator;
import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.listener.event.GameEndedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.listener.event.GameStartedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.listener.event.RoomClearedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.listener.event.RoomEnteredEvent;
import org.haw.projekt.agenten.dungeonkoala.game.listener.event.RoundEndedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.listener.event.RoundStartedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;
import org.haw.projekt.agenten.dungeonkoala.game.room.generator.RoomGenerator_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.generator.SimpleRoomGenerator;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

/**
 * Repräsentiert einen kompletten Spielzyklus vom Start bis zum Ende.
 * 
 * <p>
 * Das Spiel erwartet eine Schnittstelle für die Anzeige und eine für die
 * Interaktion mit dem Spiel. Die Trennung der Anzeige von der Interaktion ist
 * hier explizite erwünscht, da wir die Möglichkeit haben wollen einen Menschen
 * per GUI mit dem Spiel interagieren zu lassen, aber auch einen Computer.
 * Allerdings möchten wir wenn der Computer spielt unter Umständen auch sehen
 * was er gerade tut.
 */
public class GameSession {
	private static final int ROOM_CAP = 100;

	private EventDispatcher<GameListener_I> _eventDispatcher;

	private RoomGenerator_I _roomGenerator;
	
	private boolean _running;
	
	public GameSession() {
		this(new SimpleRoomGenerator(new SimpleEnemyGenerator()));
	}
	
	public GameSession(RoomGenerator_I roomGenerator) {
		_eventDispatcher = new EventDispatcher<>();
		_roomGenerator = Objects.requireNonNull(roomGenerator);
	}
	
	/**
	 * Ermöglicht das Registrieren eines {@link GameListener_I} an der GameSession.
	 * Der Listener wird über diverse Ereignisse im Spielablauf benachrichtigt
	 * @param listener Der zu registrierende Listener
	 */
	public void addGameListener(GameListener_I listener) {
		_eventDispatcher.addListener(listener);
	}
	
	/**
	 * Entfernt den übergebenen Listener von dieser GameSession.
	 * @param listener Der zu entfernende Listener
	 */
	public void removeGameListener(GameListener_I listener) {
		_eventDispatcher.removeListener(listener);
	}

	/**
	 * Startet das Spiel. Das Spiel übernimmt dann die Kontrolle über den
	 * aktuellen Thread bis das Spiel vorbei ist (der Spieler wurde besiegt oder
	 * hat das Spielende erreicht). Es kann nur noch über den registrierten
	 * Listener in das Spielgeschehen eingegriffen werden wenn das Spiel dies
	 * wünscht.
	 * @param playerBehaviour Das {@link EntityBehaviour_I Behaviour} für den Spieler, darf nicht {@code null} sein
	 */
	public void start(EntityBehaviour_I playerBehaviour) {
		_running = true;
		gameLoop(playerBehaviour);
	}
	
	/**
	 * Beendet das Spiel.
	 * Das Spiel kann nicht an der entsprechenden Stelle fortgesetzt werden.
	 */
	public void stop() {
		_running = false;
	}

	/**
	 * Der Spielzyklus.
	 */
	private void gameLoop(EntityBehaviour_I playerBehaviour) {
		Player player = new Player(playerBehaviour, 100, 15, Element.FIRE);
		
		_eventDispatcher.fireEvent(new GameStartedEvent(player, ROOM_CAP));
		while(_running && player.isAlive() && _roomGenerator.getGeneratedRoomsCount() < ROOM_CAP) {
			Room currentRoom = _roomGenerator.generateRoom();
			_eventDispatcher.fireEvent(new RoomEnteredEvent(currentRoom));
			while(_running && player.isAlive() && !currentRoom.isCleared()) {
				_eventDispatcher.fireEvent(new RoundStartedEvent());
				fight(player, currentRoom);
				_eventDispatcher.fireEvent(new RoundEndedEvent());
			}
			if(player.isAlive()) {
				_eventDispatcher.fireEvent(new RoomClearedEvent(currentRoom));
			}

		}
		_eventDispatcher.fireEvent(new GameEndedEvent(player, _roomGenerator.getGeneratedRoomsCount()));
	}

	/**
	 * Regelt den Ablauf einer Kampfrunde im Spiel.
	 * @param player Der Spieler
	 * @param room Der Raum in dem der Spieler kämpft
	 */
	private void fight(Player player, Room room) {
		Enemy enemy = room.getEnemy();

		Action playerAction = player.makeNextMove(player, enemy, room);
		Action enemyAction = enemy.makeNextMove(enemy, player, room);
		
		// Direktes Abbrechen, wenn das Spiel beendet wurde, während einer der Spieler
		// noch beim Auswählen der nächsten Aktion war. Durch das Beenden kann es mit
		// der Standard-UI vorkommen, dass keine Aktion (also null) ausgewählt wird.
		// Das führt unweigerlich zu einem Nullpointer. Da es nach dem Beenden aber
		// ohnehin egal ist, was für eine Aktion ausgewählt oder ausgeführt werden soll,
		// wird hier "fast-exited".
		if(!_running)
			return;
		
		Queue<Action> actionQueue = new PriorityQueue<Action>((o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority()));
		actionQueue.add(playerAction);
		actionQueue.add(enemyAction);

		while(player.isAlive() && !room.isCleared() && !actionQueue.isEmpty()) {
			Action a = actionQueue.poll();
			a.getPerformer().performAction(a);
		}
	}
}
