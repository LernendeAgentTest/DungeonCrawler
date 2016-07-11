package org.haw.projekt.agenten.dungeonkoala.game.entity.action;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Eine Sammlung aller {@link Action Aktionen} im Spiel.
 */
public final class Actions {
	public static final Class<AttackAction> ATTACK_ACTION = AttackAction.class;
	public static final Class<HealAction> HEAL_ACTION = HealAction.class;
	public static final Class<BlockAction> BLOCK_ACTION = BlockAction.class;
	public static final Class<ChargeAction> CHARGE_ACTION = ChargeAction.class;
	public static final Class<SwitchToFireAction> FIRE_ELEMENT_ACTION = SwitchToFireAction.class;
	public static final Class<SwitchToWaterAction> WATER_ELEMENT_ACTION = SwitchToWaterAction.class;
	public static final Class<SwitchToGrassAction> GRASS_ELEMENT_ACTION = SwitchToGrassAction.class;
	
	private static final List<Class<? extends Action>> ALL_ACTIONS = new ArrayList<Class<? extends Action>>();
	
	static {
		ALL_ACTIONS.add(ATTACK_ACTION);
		ALL_ACTIONS.add(HEAL_ACTION);
		ALL_ACTIONS.add(BLOCK_ACTION);
		ALL_ACTIONS.add(CHARGE_ACTION);
		ALL_ACTIONS.add(FIRE_ELEMENT_ACTION);
		ALL_ACTIONS.add(WATER_ELEMENT_ACTION);
		ALL_ACTIONS.add(GRASS_ELEMENT_ACTION);
	}
	
	private Actions() {}
	
	/**
	 * Gibt eine unveränderliche Liste aller {@link Action Aktionen} zurück.
	 * @return Eine unveränderliche Liste mit allen bekannten Aktionen
	 */
	public static List<Class<? extends Action>> getAllActions() {
		return Collections.unmodifiableList(ALL_ACTIONS);
	}
	
	/**
	 * Erzeugt eine Liste mit allen konkreten Aktionen.
	 * @param me Die Entität zu der dieses Verhalten gehört
	 * @param opponent Der Gegner
	 * @return Eine Liste mit allen Aktionen
	 */
	public static List<Action> createAllActions(Entity me, Entity opponent, Room room) {
		List<Action> actions = new ArrayList<Action>();
		for(Class<? extends Action> actionClass : ALL_ACTIONS) {
			try {
				Action action;
				if(AttackAction.class.isAssignableFrom(actionClass)) {
					Constructor<? extends Action> actionConstructor = actionClass.getConstructor(Entity.class, Entity.class, Room.class);
					action = actionConstructor.newInstance(me, opponent, room);
				} else {
					Constructor<? extends Action> actionConstructor = actionClass.getConstructor(Entity.class, Room.class);
					action = actionConstructor.newInstance(me, room);
				}
				actions.add(action);
			} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
		return actions;
	}
}
