package org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable;

import java.util.HashSet;
import java.util.Set;

import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

/**
 * Repräsentiert die Q-Werte, anhand derer ein QLearningAgent lernt.
 */
public class QValueTable {
	private EventDispatcher<QValueTableListener_I> _eventDispatcher;
	private double[][] _qValues;
	private Set<Integer> _visitedStates;
	
	/**
	 * Creates a 2-dimensional array representing the Q-Values for all (state,action) pairs.
	 * @param stateCount Number of achievable states.
	 * @param actionCount Number of executable Actions.
	 * @param defaultQValue Default value for every (state,action) pair.
	 */
	public QValueTable(int stateCount, int actionCount, double defaultQValue) {
		_eventDispatcher = new EventDispatcher<>();
		
		_visitedStates = new HashSet<>();
		
		_qValues = new double[stateCount][actionCount];
		for(int x=0; x<stateCount; x++) {
			for(int y=0; y<actionCount; y++) {
				_qValues[x][y] = defaultQValue;
			}
		}
	}
	
	/**
	 * Setzt den Q-Wert für die Kombination aus dem übergebenen Zustand und der
	 * Aktion.
	 * @param state Der Zustand
	 * @param action Die Aktion
	 * @param newQValue Der zu setzende Q-Wert
	 */
	public void setQValue(int state, int action, double newQValue) {
		if(!_visitedStates.contains(state)){
			_visitedStates.add(state);
			_eventDispatcher.fireEvent(l->l.onNewStateReached(_visitedStates.size(),getStateCount()));
		}
		
		_qValues[state][action] = newQValue;
		_eventDispatcher.fireEvent(l -> l.onQValueChanged(state, action, _qValues[state][action]));
	}
	
	/**
	 * Gibt den Q-Wert für die Kombination aus dem übergebenen Zustand und der
	 * Aktion zurück.
	 * @param state Der Zustand
	 * @param action Die Aktion
	 * @return Der Q-Wert für den Zustand und die Aktion
	 */
	public double getQValue(int state, int action) {
		return _qValues[state][action];
	}
	
	/**
	 * Gibt den optimalen Q-Wert für den übergebenen Zustand zurück.
	 */
	public double getMaxQValueOfState(int state) {
		double[] actionQValues = _qValues[state];
		double maxQ = -Double.MAX_VALUE;
		for (double d : actionQValues) {
			if(d > maxQ) {
				maxQ = d;
			}
		}
		return maxQ;
	}
	
	/**
	 * Gibt die Anzahl der Zustände zurück, die diese QValueTable verwaltet.
	 * @return Die Anzahl der Zustände
	 */
	public int getStateCount() {
		return _qValues.length;
	}
	
	/**
	 * Gibt die Anzahl der Aktionen zurück, die diese QValueTable verwaltet.
	 * @return Die Anzahl der Aktionen
	 */
	public int getActionCount() {
		return _qValues[0].length;
	}
	
	/**
	 * Registriert den übergebenen Listener an dieser QValueTable.
	 * @param listener Der zu registrierende Listener
	 */
	public void addQValueTableListener(QValueTableListener_I listener) {
		_eventDispatcher.addListener(listener);
	}
	
	/**
	 * Trägt den übergebenen Listener aus der QValueTable aus.
	 * @param listener Der auszutragende Listener
	 */
	public void removeQValueTableListener(QValueTableListener_I listener) {
		_eventDispatcher.removeListener(listener);
	}
}
