package org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;

/**
 * Eine Sammlung aller zustandslosen {@link EntityBehaviour_I Verhaltensmuster}.
 */
public final class Behaviours {
	public static final EntityBehaviour_I ANALYTICAL  	= new AnalyticalBehaviour();
	public static final EntityBehaviour_I ATTACK_ONLY 	= new AttackOnlyBehaviour();
	public static final EntityBehaviour_I RANDOM      	= new RandomBehaviour();
	public static final EntityBehaviour_I FIRST_DEFENSE = new FirstDefenseBehaviour();
	public static final EntityBehaviour_I MAX_DPS		= new MaxDpsBehaviour();
	
	private static final List<EntityBehaviour_I> BEHAVIOURS = new ArrayList<EntityBehaviour_I>();
	
	static {
		BEHAVIOURS.add(ANALYTICAL);
		BEHAVIOURS.add(ATTACK_ONLY);
		BEHAVIOURS.add(RANDOM);
//		BEHAVIOURS.add(FIRST_DEFENSE);
		BEHAVIOURS.add(MAX_DPS);
	}
	
	private Behaviours() {}
	
	/**
	 * Gibt eine unveränderliche Liste aller zustandslosen Verhaltensmuster zurück.
	 * @return Eine unveränderliche Liste aller zustandslosen Verhaltensmuster
	 */
	public static List<EntityBehaviour_I> getBehaviours() {
		return Collections.unmodifiableList(BEHAVIOURS);
	}
	
	/**
	 * Gibt ein zufälliges Verhalten zurück.
	 * @return Das Verhalten
	 */
	public static EntityBehaviour_I randomBehaviour() {
		int selection = (int)(Math.random()*BEHAVIOURS.size());
		return BEHAVIOURS.get(selection);
	}
}