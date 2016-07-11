package org.haw.projekt.agenten.dungeonkoala.game.entity.action;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Heilt die aktive Entität um den Wert ihrer Angriffskraft.
 */
public class HealAction extends Action_A {

	public HealAction(Entity me, Room room) {
		super(me, room);
	}

	@Override
	public void execute() {
		int healValue = (int) ((_me.getAttackPower() + _me.getEnergyBonus()));
		_me.alterHealth(+healValue);
		_me.resetEnergy();
	}

	@Override
	public int getPriority() {
		return 1;
	}

	@Override
	public String toString() {
		return "Heal";
	}
}
