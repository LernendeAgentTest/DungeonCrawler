package org.haw.projekt.agenten.dungeonkoala.learning.policy;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTable;

/**
 * Interface for a decision Policy to be used with a Q-Learner.
 * Requires the list to be the same as the one given to the Q-Learning behaviour at initialization. Won't work otherwise.
 */
public interface Policy_I {
	/**
	 * Wählt eine Aktion aus der übergebenen Liste aus.
	 * @param state Der aktuelle Zustand des Agenten
	 * @param possibleActions Eine Liste aller möglichen Aktionen in dem Zustand
	 * @param qValues Die {@link QValueTable} in der die Bewertung der Aktionen pro Zustand enthalten ist
	 * @return Die ausgewählte Aktion
	 */
	int chooseAction(int state, List<Integer> possibleActions, QValueTable qValues);
	
	String getName();
}
