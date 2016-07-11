package org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event;

import java.util.Objects;
import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.EntityListener_I;

/**
 * Event das darüber informiert, dass eine {@link Action Aktion} der {@link Entity Entität} an der gelauscht wird
 * ausgeführt wurde. Zum Zeitpunkt des Events wurde die Aktion bereits ausgeführt.
 */
public class AfterActionExecutedEvent extends AbstractEntityEvent {
	private Action _action;
	
	public AfterActionExecutedEvent(Entity entity, Action action) {
		super(entity);
		_action = Objects.requireNonNull(action);
	}
	
	@Override
	public void fireEvent(EntityListener_I l) {
		l.onAfterActionExecuted(_entity, _action);
	}
}
