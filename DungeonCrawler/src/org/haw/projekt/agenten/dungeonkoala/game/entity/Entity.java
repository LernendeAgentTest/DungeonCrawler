package org.haw.projekt.agenten.dungeonkoala.game.entity;

import java.util.List;
import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.datatypes.bounded.BoundedInteger;
import org.haw.projekt.agenten.dungeonkoala.datatypes.bounded.MutableBoundedInteger;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Actions;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.EntityListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event.AfterActionExecutedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event.AttackPowerChangedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event.BeforeActionExecutedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event.BlockLevelChangedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event.DiedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event.EnergyChangedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.event.HealthChangedEvent;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

/**
 * Repräsentiert eine Entität im Spiel. Eine Entität zeichnet sich dadurch aus,
 * dass sie im Rahmen des Spiels am Kampf teilnehmen kann.
 */
public class Entity {
	private EventDispatcher<EntityListener_I> _eventDispatcher;
	private EntityBehaviour_I _behaviour;
	private String _name;

	private MutableBoundedInteger _health;
	private MutableBoundedInteger _energy;
	private MutableBoundedInteger _blockLevel;
	private int _attackPower;
	
	private Element _element;
	
	/**
	 * Initialisiert die Entität.
	 * @param behaviour Das Verhalten der Entität, darf nicht {@code null} sein
	 * @param name Der Name der Entität
	 * @param health Das Leben der Entität
	 * @param attackPower Die Angriffskraft der Entität
	 */
	public Entity(EntityBehaviour_I behaviour, String name, int health, int attackPower, Element element) {
		_eventDispatcher = new EventDispatcher<EntityListener_I>();
		_behaviour = Objects.requireNonNull(behaviour);
		_name = name;
		_health = new MutableBoundedInteger(health);
		_energy = new MutableBoundedInteger(3, 0);
		_blockLevel = new MutableBoundedInteger(_energy.getMaximum()+1, 0); // Ein mehr als Energy, da 0 bedeutet dass nicht geblockt wird
		_attackPower = attackPower;
		_element = element;
	}
	
	public Element getElement(){
		return _element;
	}
	
	public void setElement(Element element){
		_element = element;
	}
	
	/**
	 * Ermöglicht das Registrieren eines Listeners an der Entität.
	 * Die Listener werden über bestimmte Ereignisse von der Entität benachrichtigt.
	 * @param listener Der zu registrierende Listener, darf nicht {@code null} sein
	 * @see EntityListener_I
	 */
	public void addEntityListener(EntityListener_I listener) {
		_eventDispatcher.addListener(listener);
	}
	
	/**
	 * Trägt den übergebenen Listener aus, sodass er fortan keine weiteren Benachrichtigungen
	 * über Ereignisse der Entität erhält.
	 * @param listener Der auszutragende Listener, kann {@code null} sein (das hat dann aber keine Auswirkung )
	 */
	public void removeEntityListener(EntityListener_I listener) {
		_eventDispatcher.removeListener(listener);
	}
	
	/**
	 * Gibt das Verhalten der Entität zurück. Das Verhalten steuert, welche Aktionen
	 * eine Entität im Kampf einsetzt.
	 * @return Das Verhalten (niemals {@code null})
	 */
	public EntityBehaviour_I getBehaviour() {
		return _behaviour;
	}
	
	/**
	 * Gibt den Namen der Entität zurück.
	 * @return Der Name der Entität
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Gibt das aktuelle Leben der Entität zurück.
	 * @return Das Leben der Entität
	 */
	public BoundedInteger getHealth() {
		return _health;
	}

	/**
	 * Gibt das Energielevel der Entität zurück.
	 * @return Das Energielevel der Entität
     */
	public int getEnergy() {
		return _energy.getValue();
	}

	/**
	 * Gibt das Blocklevel zum Reduzieren des Schadens zurück.
	 * @return Das Blocklevel
	 */
	public int getBlockLevel() {
		return _blockLevel.getValue();
	}

	/**
	 * Gibt die Angriffskraft der Entität zurück.
	 * @return Die Angriffskraft der Entität
	 */
	public int getAttackPower() {
		return _attackPower;
	}
	
	/**
	 * Berechnet den Aktions-Bonus abhängig vom aktuellen Energielevel.
	 * Reduziert Energie auf 0.
	 * @return Der Aktions-Bonus
	 */
	public int getEnergyBonus() {
		double bonus = getAttackPower() * getEnergy() * Math.log(getEnergy()) + (getAttackPower() * getEnergy() / 2);
		return (int)Math.round(bonus);
	}
	
	/**
	 * Prüft, ob die Entität am Leben ist. Wenn die Lebenspunkte der Entität auf 0
	 * fallen, ist die Entität nicht mehr am Leben.
	 * @return {@code true} wenn die Entität am Leben ist, ansonsten {@code false}
	 * @see #getHealth()
	 */
	public boolean isAlive() {
		return !_health.isValueAtMinimum();
	}

	/**
	 * Prüft ob die Entität in Verteidigungstellung ist.
	 * @return {@code true} wenn die Entität verteidigt, ansonsten {@code false}
     */
	public boolean isBlocking() {
		return !_blockLevel.isValueAtMinimum();
	}
	
	/**
	 * Verändert das Leben der Entität um die angegebene Menge. Positive Werte erhöhen das Leben und
	 * negative Werte reduzieren es.
	 * <p>{@link EntityListener_I Listener} werden über das Verändern des Wertes informiert. Sollte
	 * die Entität sterben, werden sie ebenfalls benachrichtigt.
	 * @param amount Die Menge
	 */
	public void alterHealth(int amount) {
		int oldHealth = _health.getValue();
		_health.alterValue(amount);
		_eventDispatcher.fireEvent(new HealthChangedEvent(this, oldHealth, _health.getValue()));
		if(!isAlive()) {
			_eventDispatcher.fireEvent(new DiedEvent(this));
		}
	}

	/**
	 * Verändert den Energielevel der Entität um die angegebene Menge. Positive Werte erhöhen die
	 * Energie und negative reduzieren sie.
	 * <p>{@link EntityListener_I Listener} werden über das Verändert des Wertes informiert.
	 * @param amount Die Menge
	 */
	public void alterEnergy(int amount) {
		int oldEnergy = _energy.getValue();
		_energy.alterValue(amount);
		_eventDispatcher.fireEvent(new EnergyChangedEvent(this, oldEnergy, _energy.getValue()));
	}
	
	/**
	 * Setzt den Energielevel der Entität zurück (auf 0).
	 * <p>{@link EntityListener_I Listener} werden über das Verändert des Wertes informiert.
	 */
	public void resetEnergy() {
		int oldEnergy = _energy.getValue();
		_energy.setValueToMinimum();
		_eventDispatcher.fireEvent(new EnergyChangedEvent(this, oldEnergy, _energy.getValue()));
	}

	/**
	 * Setzt den aktuellen Block-Level der Entität auf den übergebenen Wert.
	 * <p>{@link EntityListener_I Listener} werden über das Verändert des Wertes informiert.
	 * @param blockLevel Der neue Blocklevel
	 */
	public void setBlockLevel(int blockLevel) {
		int oldBlock = _blockLevel.getValue();
		_blockLevel.setValue(blockLevel+1);
		_eventDispatcher.fireEvent(new BlockLevelChangedEvent(this, oldBlock, _blockLevel.getValue()));
	}
	
	/**
	 * Setzt den Blocklevel der Entität zurück (auf 0).
	 * <p>{@link EntityListener_I Listener} werden über das Verändert des Wertes informiert.
	 */
	public void resetBlockLevel() {
		int oldBlock = _blockLevel.getValue();
		_blockLevel.setValueToMinimum();
		_eventDispatcher.fireEvent(new BlockLevelChangedEvent(this, oldBlock, _blockLevel.getValue()));
	}

	/**
	 * Setzt die Angriffskraft der Entität auf den übergebenen Wert.
	 * @param attackPower Der neue Wert für die Angriffskraft der Entität
	 */
	public void setAttackPower(int attackPower) {
		int oldAttackPower = _attackPower;
		_attackPower = attackPower;
		_eventDispatcher.fireEvent(new AttackPowerChangedEvent(this, oldAttackPower, _attackPower));
	}
	
	/**
	 * Wird vom Spiel immer dann aufgerufen, wenn es eine Aktion von einer {@link Entity Entität}
	 * benötigt. Hebt temporäre Eigenschaften auf.
	 * @param me Die Entität, die eine Aktion ausführt, darf nicht {@code null} sein
	 * @param opponent Eine eventuell an der Aktion beteiligte Entität, sollte auch nicht {@code null} sein
	 * @return Die von der Entität gewählte Aktion, darf nicht {@code null} sein
	 */
	public Action makeNextMove(Entity me, Entity opponent, Room room) {
		resetBlockLevel();
		List<Action> actions = Actions.createAllActions(me, opponent, room);
		return _behaviour.makeNextMove(me, opponent, actions, room);
	}
	
	/**
	 * Führt die übergebene {@link Action Aktion} aus. Dabei werden {@link EntityListener_I Listener}
	 * auch darüber benachrichtigt, dass die Entität eine Aktion ausführt.
	 * @param action Die auszuführende Aktion
	 */
	public void performAction(Action action) {
		_eventDispatcher.fireEvent(new BeforeActionExecutedEvent(this, action));
		action.execute();
		_eventDispatcher.fireEvent(new AfterActionExecutedEvent(this, action));
	}
}
