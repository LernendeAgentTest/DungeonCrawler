package org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.EntityListener_I;

/**
 * Ein Event das darüber informiert, dass sich der Angriffskraft-Wert geändert hat.
 */
public class AttackPowerChangedEvent extends AbstractEntityValueChangedEvent {
	public AttackPowerChangedEvent(Entity entity, int oldValue, int newValue) {
		super(entity, oldValue, newValue);
	}
	
	@Override
	public void fireEvent(EntityListener_I l) {
		l.onAttackPowerChanged(_entity, _oldValue, _newValue);
	}
}
