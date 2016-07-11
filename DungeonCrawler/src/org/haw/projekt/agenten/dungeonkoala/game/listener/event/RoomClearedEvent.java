package org.haw.projekt.agenten.dungeonkoala.game.listener.event;

import java.util.Objects;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Ein Event das über das Abschliessen eines Raums informiert.
 */
public class RoomClearedEvent extends GameEvent_A {
	private Room _room;
	
	public RoomClearedEvent(Room room) {
		_room = Objects.requireNonNull(room);
	}
	
	@Override
	public void fireEvent(GameListener_I l) {
		l.onRoomCleared(_room);
	}
}
