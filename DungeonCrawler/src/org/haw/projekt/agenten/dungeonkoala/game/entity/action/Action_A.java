package org.haw.projekt.agenten.dungeonkoala.game.entity.action;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

public abstract class Action_A implements Action {
	/**
	 * Die Entität von der die Aktion ausgeht.
	 */
	protected Entity _me;
	protected Room _room;

	public Action_A(Entity me, Room room) {
		_me = me;
		_room = room;
	}

	public void setMe(Entity me) {
		_me = me;
	}

	@Override
	public Entity getPerformer() {
		return _me;
	}
}
