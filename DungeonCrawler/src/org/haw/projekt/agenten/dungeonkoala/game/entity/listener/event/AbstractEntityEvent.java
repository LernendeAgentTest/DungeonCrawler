package org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event;

import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.EntityListener_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.Event;

/**
 * Abstrakte Oberklasse für alle Events die zu einer {@link Entity Entität} auftreten können.
 */
abstract class AbstractEntityEvent implements Event<EntityListener_I> {
	protected Entity _entity;
	
	public AbstractEntityEvent(Entity entity) {
		_entity = Objects.requireNonNull(entity);
	}
}
