package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.learning;

public interface LearningUI_I {
	/**
	 * Fügt den übergebenen Listener als Listener an diesem UI-Element hinzu.
	 * @param listener Der hinzuzufügende Listener
	 */
	void addLearningUIListener(LearningUIListener_I listener);
	
	/**
	 * Entfernt den übergebenen Listener als Listener an diesem UI-Element.
	 * @param listener Der zu entfernende Listener
	 */
	void removeLearningUIListener(LearningUIListener_I listener);
	
	/**
	 * Legt die auf diesem UI-Element ausgewählte Anzahl der Iterationen fest.
	 * @param iterations Die Anzahl der Iterationen
	 */
	void setIterations(int iterations);
	
	/**
	 * Gibt die auf diesem UI-Element konfigurierte Anzahl an Iterationen zurück.
	 * @return Die Anzahl der Iterationen
	 */
	int getIterations();
	
	/**
	 * Gibt zurück, ob ändernde Schaltflächen aktiviert (klickbar) sind.
	 * @return {@code true} wenn ändernde Schaltflächen aktiviert sind, ansonsten {@code false}
	 */
	boolean isEnabled();
	
	/**
	 * Legt fest, ob ändernde Schaltflächen aktiviert (klickbar) sein sollen.
	 * @param enable {@code true} wenn ändernde Schaltflächen aktiviert sein sollen, ansonsten {@code false}
	 */
	void setEnabled(boolean enable);
	
	void setReachedStates(int reachedStates,int maxReachableStates);
}
