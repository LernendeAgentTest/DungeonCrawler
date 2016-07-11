package org.haw.projekt.agenten.dungeonkoala.learning;

import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTableListener_I;

/**
 * Eine Schnittstelle um Änderungen eines {@link QLearningAgent} mitzubekommen.
 */
public interface QLearningAgentListener_I extends QValueTableListener_I {
	/**
	 * Wird dann vom Agenten aufgerufen, wenn er eine Aktion ausgewählt hat.
	 * @param state Der Zustand in dem sich der Agent für die Aktion entschieden hat
	 * @param choosenAction Die ausgewählte Aktion
	 */
	void onActionChoosen(int state, int choosenAction);
	
	/**
	 * Wird dann vom Agenten aufgerufen, wenn sich seine Policy geändert hat.
	 * @param policy Die neue Policy
	 */
	void onPolicyChanged(Policy_I policy);
	
	/**
	 * Wird dann vom Agenten aufgerufen, wenn sich seine Lernrate geändert hat.
	 * @param learningRate Die neue Lernrate
	 */
	void onLearningRateChanged(double learningRate);
	
	/**
	 * Wird dann vom Agenten aufgerufen, wenn sich sein Discount-Faktor geändert hat.
	 * @param discountFactor Der neue Discount-Faktor
	 */
	void onDiscountFactorChanged(double discountFactor);
}
