package org.haw.projekt.agenten.dungeonkoala.game.listener.event;

import java.util.Objects;
import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;

/**
 * Ein Event das über den Start des Spiels informiert.
 */
public class GameStartedEvent extends GameEvent_A {
	private Player _player;
	private int _rooms;
	
	public GameStartedEvent(Player player, int rooms) {
		_player = Objects.requireNonNull(player);
		_rooms = rooms;
	}
	
	@Override
	public void fireEvent(GameListener_I l) {
		l.onGameStarted(_player, _rooms);
	}
}
