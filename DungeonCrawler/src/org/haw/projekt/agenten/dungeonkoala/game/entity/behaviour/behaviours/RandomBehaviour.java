package org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Ein Verhalten, das seine Aktionen zufällig wählt.
 */
public class RandomBehaviour implements EntityBehaviour_I {
	@Override
	public Action makeNextMove(Entity me, Entity opponent, List<Action> actions, Room room) {
		int selection = (int)(Math.random()*actions.size());
		return actions.get(selection);
	}
	
	@Override
	public String getDisplayName() {
		return "random";
	}
}
