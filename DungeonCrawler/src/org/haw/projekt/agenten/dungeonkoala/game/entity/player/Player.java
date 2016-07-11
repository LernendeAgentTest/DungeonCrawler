package org.haw.projekt.agenten.dungeonkoala.game.entity.player;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;

/**
 * Repräsentiert den Spieler des Spiels.
 */
public class Player extends Entity {
	
	public Player(EntityBehaviour_I behaviour, int health, int attackPower, Element elementDef) {
		super(behaviour, Player.class.getSimpleName(), health, attackPower, elementDef);
	}
}
