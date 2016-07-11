package org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable;

/**
 * Ein Listener, um an {@link QValueTable}s zu lauschen.
 */
public interface QValueTableListener_I {
	/**
	 * Wird aufgerufen, wenn sich ein Wert im {@link QValueTable} verändert hat.
	 * @param state Der Zustand zu dem der Wert gehört, der sich geändert hat
	 * @param action Die Aktion zu der der Wert gehört, der sich geändert hat
	 * @param value Der Wert der sich geändert hat
	 */
	void onQValueChanged(int state, int action, double value);
	
	void onNewStateReached(int reachedStates,int maxStates);
}
