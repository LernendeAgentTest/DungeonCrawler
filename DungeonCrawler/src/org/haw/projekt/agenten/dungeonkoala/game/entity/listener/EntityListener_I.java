package org.haw.projekt.agenten.dungeonkoala.game.entity.listener;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;

/**
 * Schnittstelle zum Lauschen auf {@link Entity Entitäten}. Dadurch kann auf Ereignisse
 * die zu einer Entität auftreten reagiert werden.
 */
public interface EntityListener_I {
	/**
	 * Wird immer dann aufgerufen, wenn sich der aktuelle Gesundheitswert der Entität
	 * ändert, an der gelauscht wird.
	 * @param entity Die Entität deren Gesundheitswert sich verändert hat
	 * @param oldValue Der alte Gesundheitswert
	 * @param newValue Der neue Gesundheitswert
	 */
	void onHealthChanged(Entity entity, int oldValue, int newValue);
	
	/**
	 * Wird aufgerufen, wenn die Entität, an der gelauscht wird, stirbt.
	 * @param entity Die Entität die gestorben ist
	 */
	void onDied(Entity entity);
	
	/**
	 * Wird immer dann aufgerufen, wenn sich der aktuelle Energiewert der Entität
	 * ändert, an der gelauscht wird.
	 * @param entity Die Entität deren Energielevel sich verändert hat
	 * @param oldValue Der alte Energiewert
	 * @param newValue Der neue Energiewert
	 */
	void onEnergyChanged(Entity entity, int oldValue, int newValue);
	
	/**
	 * Wird immer dann aufgerufen, wenn sich der aktuelle Blocklevel der Entität
	 * ändert, an der gelauscht wird.
	 * @param entity Die Entität deren Blocklevel sich verändert hat
	 * @param oldValue Der alte Blocklevel
	 * @param newValue Der neue Blocklevel
	 */
	void onBlockLevelChanged(Entity entity, int oldValue, int newValue);
	
	/**
	 * Wird immer dann aufgerufen, wenn sich die aktuelle Angriffskraft der Entität
	 * ändert, an der Gelauscht wird.
	 * @param entity Die Entität, deren Angriffskraft sich verändert hat
	 * @param oldValue Der alte Angriffskraft-Wert
	 * @param newValue Der neue Angriffskraft-Wert
	 */
	void onAttackPowerChanged(Entity entity, int oldValue, int newValue);
	
	/**
	 * Wird immer dann aufgerufen, wenn eine Aktion der Entität an der gelauscht wird
	 * ausgeführt wird. Zu diesem Zeitpunkt ist die Aktion noch nicht durchgeführt.
	 * @param entity Die Entität, die die Aktion ausführt
	 * @param action Die auszuführende Aktion
	 */
	void onBeforeActionExecuted(Entity entity, Action action);
	
	/**
	 * Wird immer dann aufgerufen, wenn eine Aktion der Entität an der gelauscht wird
	 * ausgeführt wird. Zu diesem Zeitpunkt ist die Aktion bereits durchgeführt.
	 * @param entity Die Entität, die die Aktion ausgeführt hat
	 * @param action Die ausgeführte Aktion
	 */
	void onAfterActionExecuted(Entity entity, Action action);
}
