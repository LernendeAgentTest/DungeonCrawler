package org.haw.projekt.agenten.dungeonkoala.game.listener.event;

import java.util.Objects;
import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;

/**
 * Ein Event das über das Ende des Spiels informiert.
 */
public class GameEndedEvent extends GameEvent_A {
	private Player _player;
	private int _rooms;
	
	public GameEndedEvent(Player player, int rooms) {
		_player = Objects.requireNonNull(player);
		_rooms = rooms;
	}
	
	@Override
	public void fireEvent(GameListener_I l) {
		l.onGameEnded(_player, _rooms);
	}
}
