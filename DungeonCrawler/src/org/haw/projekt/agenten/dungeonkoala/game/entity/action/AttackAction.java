package org.haw.projekt.agenten.dungeonkoala.game.entity.action;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Die aktive Entität greift die passive Entität an. Dabei wird das Leben der
 * passiven Entität um die Angriffskraft(abzüglich möglichen Blocks) der aktiven
 * Entität reduziert.
 */
public class AttackAction extends Action_A {
	/**
	 * Das Ziel der Aktion ist eine andere Entität
	 */
	private Entity _opponent;

	public AttackAction(Entity me, Entity opponent, Room room) {
		super(me, room);
		_opponent = opponent;
	}

	@Override
	public void execute() {

		double factor = (_me.getElement().versusElement(_opponent.getElement()));

		int attackDamage = (int) ((_me.getAttackPower() + _me.getEnergyBonus()) * factor);

		if (_opponent.isBlocking()) {
			attackDamage = (int) ((attackDamage / (1 + _opponent.getBlockLevel())));
		}
		_opponent.alterHealth(-attackDamage);
		_me.resetEnergy();
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public String toString() {
		return "Attack";
	}
}
