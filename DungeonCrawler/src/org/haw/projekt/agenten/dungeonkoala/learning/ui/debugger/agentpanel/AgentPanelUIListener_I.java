package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel;

import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;

public interface AgentPanelUIListener_I {
	/**
	 * Wird aufgerufen, wenn eine andere Policy auf der Benutzeroberfläche ausgewählt wurde.
	 * @param policy Die neue Policy
	 */
	void onPolicyChanged(Policy_I policy);
	
	/**
	 * Wird aufgerufen, wenn sich die Lernrate auf der Benutzeroberfläche geändert hat.
	 * @param learningRate Die neue Lernrate
	 */
	void onLearningRateChanged(double learningRate);
	
	/**
	 * Wird aufgerufen, wenn sich der Discount-Faktor auf der Benutzeroberfläche geändert hat.
	 * @param discountFactor Der neue Discount-Faktor
	 */
	void onDiscountFactorChanged(double discountFactor);
}
