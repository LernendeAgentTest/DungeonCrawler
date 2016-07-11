package org.haw.projekt.agenten.dungeonkoala.learning;

import java.util.List;
import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTable;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

/**
 * Eine allgemeine Implementation für einen Q-Learning-Agenten. Der Agent bietet Schnittstellen
 * zum Belohnen (bzw. Bestrafen) und dem Auswählen einer Aktion.
 */
public class QLearningAgent {
	public static final double DEFAULT_START_QVALUE = 0.0;
	public static final double DEFAULT_LEARNING_RATE = 0.2;
	public static final double DEFAULT_DISCOUNT_FACTOR = 0.9;
	
	private EventDispatcher<QLearningAgentListener_I> _dispatcher;
	
	private Policy_I _policy;
	private QValueTable _qValues;
	private double _learningRate;
	private double _discountFactor;
	
	/**
	 * Instanziiert einen neuen QLearning Agenten.
	 * @param policy Die Policy, nach der der Agent Aktionen auswählt
	 * @param stateCount Die Anzahl der möglichen, unterschiedlichen Zustände
	 * @param actionCount Die Anzahl der möglichen, unterschiedlichen Aktionen
	 */
	public QLearningAgent(Policy_I policy, int stateCount, int actionCount) {
		this(policy, stateCount, actionCount, DEFAULT_START_QVALUE, DEFAULT_LEARNING_RATE, DEFAULT_DISCOUNT_FACTOR);
	}
	
	/**
	 * Instanziiert einen neuen QLearning Agenten.
	 * @param policy Die Policy, nach der der Agent Aktionen auswählt
	 * @param stateCount Die Anzahl der möglichen, unterschiedlichen Zustände
	 * @param actionCount Die Anzahl der möglichen, unterschiedlichen Aktionen
	 * @param startQValue Der Startwert für alle Einträge der internen {@link QValueTable}
	 */
	public QLearningAgent(Policy_I policy, int stateCount, int actionCount, double startQValue) {
		this(policy, stateCount, actionCount, startQValue, DEFAULT_LEARNING_RATE, DEFAULT_DISCOUNT_FACTOR);
	}
	
	/**
	 * Instanziiert einen neuen QLearning Agenten.
	 * @param policy Die Policy, nach der der Agent Aktionen auswählt
	 * @param stateCount Die Anzahl der möglichen, unterschiedlichen Zustände
	 * @param actionCount Die Anzahl der möglichen, unterschiedlichen Aktionen
	 * @param startQValue Der Startwert für alle Einträge der internen {@link QValueTable}
	 * @param learningRate Die Lernrate
	 * @param discountFactor Der Discount-Faktor
	 */
	public QLearningAgent(Policy_I policy, int stateCount, int actionCount, double startQValue, double learningRate, double discountFactor) {
		_dispatcher = new EventDispatcher<QLearningAgentListener_I>();
		_policy = Objects.requireNonNull(policy);
		_qValues = new QValueTable(stateCount, actionCount, startQValue);
		_learningRate = learningRate;
		_discountFactor = discountFactor;
	}
	
	/**
	 * Wählt anhand der hinterlegten {@link Policy_I} eine Aktion aus den übergebenen möglichen Aktionen aus.
	 * @param possibleActions Eine Liste der möglichen Aktionen
	 * @return Die ausgewählte Aktion
	 */
	public int chooseAction(int state, List<Integer> possibleActions) {
		int action = _policy.chooseAction(state, possibleActions, _qValues);
		_dispatcher.fireEvent(l -> l.onActionChoosen(state, action));
		return action;
	}
	
	/**
	 * Erhält eine Belohnung (oder Bestrafung bei negativen Werten) für die übergebene Aktion
	 * die in dem übergebenen Zustand getätigt wurde.
	 * @param state Der Zustand für den die Belohnung gelten soll
	 * @param currentState Der Zustand in dem sich der Agent gerade befindet (der Zustand nach {@code state})
	 * @param action Die Aktion für die die Belohnung gelten soll
	 * @param reward Die Belohnung, negative Werte entsprechen einer Bestrafung
	 */
	public void reward(int state, int currentState, int action, double reward) {
		double oldQValue = _qValues.getQValue(state, action);
		double newQValue = oldQValue + _learningRate * (reward + (_discountFactor * _qValues.getMaxQValueOfState(currentState)) - oldQValue);
		_qValues.setQValue(state, action, newQValue);
	}
	
	/**
	 * Registriert den übergebenen Listener an diesem Agenten.
	 * @param listener Der hinzuzufügende Listener
	 */
	public void addQLearningAgentListener(QLearningAgentListener_I listener) {
		_dispatcher.addListener(listener);
		_qValues.addQValueTableListener(listener);
	}
	
	/**
	 * Entfernt den übergebenen Listener von diesem Agenten.
	 * @param listener Der zu entfernende Listener
	 */
	public void removeQLearningAgentListener(QLearningAgentListener_I listener) {
		_dispatcher.removeListener(listener);
		_qValues.removeQValueTableListener(listener);
	}
	
	/**
	 * Gibt die aktuelle Policy anhand derer der Agent Entscheidungen trifft zurück.
	 * @return Die aktuelle Policy
	 */
	public Policy_I getPolicy() {
		return _policy;
	}
	
	/**
	 * Gibt die {@link QValueTable} zurück, die der Agent nutzt.
	 * @return Die {@link QValueTable}
	 */
	public QValueTable getQValues() {
		return _qValues;
	}
	
	/**
	 * Gibt die aktuelle Lernrate zurück. Die Lernrate legt fest, zu welchem Grad neue
	 * Q-Werte alte überschreiben
	 * @return Die aktuelle Lernrate
	 */
	public double getLearningRate() {
		return _learningRate;
	}
	
	/**
	 * Gibt den aktuellen Discount-Faktor zurück.
	 * Der Discount-Faktor bestimmt die Wichtigkeit zukünftiger Belohnungen / Bestrafungen.
	 * @return Der aktuelle Discount-Faktor
	 */
	public double getDiscountFactor() {
		return _discountFactor;
	}
	
	/**
	 * Stellt die Policy anhand derer der Agent Entscheidungen trifft auf die übergebene um.
	 * @param policy Die neue Policy
	 */
	public void setPolicy(Policy_I policy) {
		_policy = Objects.requireNonNull(policy);
		_dispatcher.fireEvent(l -> l.onPolicyChanged(_policy));
	}
	
	/**
	 * Setzt die Lernrate auf den übergebenen Wert. Die Lernrate legt fest, zu welchem Grad
	 * neue Q-Werte alte überschreiben
	 * @param learningRate Der neue Wert für die Lernrate
	 */
	public void setLearningRate(double learningRate) {
		_learningRate = learningRate;
		_dispatcher.fireEvent(l -> l.onLearningRateChanged(_learningRate));
	}
	
	/**
	 * Setzt den Discount-Faktor auf den übergebenen Wert.
	 * Der Discount-Faktor bestimmt die Wichtigkeit zukünftiger Belohnungen / Bestrafungen.
	 * @param discountFactor Der neue Wert für den Discount-Faktor
	 */
	public void setDiscountFactor(double discountFactor) {
		_discountFactor = discountFactor;
		_dispatcher.fireEvent(l -> l.onDiscountFactorChanged(_discountFactor));
	}
}
