package org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.AttackAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Ein Verhalten, das die zugehörige Entität dazu veranlasst ausschließlich anzugreifen.
 */
public class AttackOnlyBehaviour implements EntityBehaviour_I {
	@Override
	public Action makeNextMove(Entity me, Entity opponent, List<Action> actions, Room room) {
		return new AttackAction(me, opponent, room);
	}
	
	@Override
	public String getDisplayName() {
		return "ATTACK EVERYTHING!";
	}
}
