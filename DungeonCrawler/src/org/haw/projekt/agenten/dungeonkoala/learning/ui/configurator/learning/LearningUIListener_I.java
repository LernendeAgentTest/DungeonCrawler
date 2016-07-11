package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.learning;

public interface LearningUIListener_I {
	/**
	 * Wird aufgerufen, wenn die Anzahl der zu lernenden Iterationen geändert wurde.
	 * @param iterations Die Anzahl der Iterationen
	 */
	void onIterationsChanged(int iterations);
}
