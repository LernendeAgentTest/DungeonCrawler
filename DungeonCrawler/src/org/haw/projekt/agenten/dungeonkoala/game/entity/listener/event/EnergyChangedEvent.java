package org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.EntityListener_I;

/**
 * Ein Event das darüber informiert, dass sich der Energiewert verändert hat.
 */
public class EnergyChangedEvent extends AbstractEntityValueChangedEvent {
	public EnergyChangedEvent(Entity entity, int oldValue, int newValue) {
		super(entity, oldValue, newValue);
	}
	
	@Override
	public void fireEvent(EntityListener_I l) {
		l.onEnergyChanged(_entity, _oldValue, _newValue);
	}
}
