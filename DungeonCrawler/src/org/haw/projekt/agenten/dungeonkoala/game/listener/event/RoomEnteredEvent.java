package org.haw.projekt.agenten.dungeonkoala.game.listener.event;

import java.util.Objects;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Ein Event das über das Betreten eines Raums informiert.
 */
public class RoomEnteredEvent extends GameEvent_A {
	private Room _room;
	
	public RoomEnteredEvent(Room room) {
		_room = Objects.requireNonNull(room);
	}
	
	@Override
	public void fireEvent(GameListener_I l) {
		l.onRoomEntered(_room);
	}
}
