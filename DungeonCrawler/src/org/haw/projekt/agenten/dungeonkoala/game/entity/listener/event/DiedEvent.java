package org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.EntityListener_I;

/**
 * Ein Event das darüber informiert, dass eine Entität gestorben ist.
 */
public class DiedEvent extends AbstractEntityEvent {
	public DiedEvent(Entity entity) {
		super(entity);
	}

	@Override
	public void fireEvent(EntityListener_I l) {
		l.onDied(_entity);
	}
}
