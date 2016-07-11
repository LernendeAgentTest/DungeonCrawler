package org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;

/**
 * Abstraktes Oberklasse für Events die über ein Ereignis informieren, bei dem sich ein
 * Wert von einem alten zu einem neuen Wert ändert.
 */
abstract class AbstractEntityValueChangedEvent extends AbstractEntityEvent {
	protected final int _oldValue;
	protected final int _newValue;
	
	public AbstractEntityValueChangedEvent(Entity entity, int oldValue, int newValue) {
		super(entity);
		_oldValue = oldValue;
		_newValue = newValue;
	}
}
