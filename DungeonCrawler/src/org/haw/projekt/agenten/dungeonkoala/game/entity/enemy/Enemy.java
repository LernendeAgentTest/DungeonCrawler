package org.haw.projekt.agenten.dungeonkoala.game.entity.enemy;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;

/**
 * Repräsentiert einen Gegner im Spiel.
 */
public class Enemy extends Entity {

	public Enemy(EntityBehaviour_I behaviour, int health, int attackPower, Element elementDef) {
		super(behaviour, Enemy.class.getSimpleName(), health, attackPower, elementDef);
	}

}
