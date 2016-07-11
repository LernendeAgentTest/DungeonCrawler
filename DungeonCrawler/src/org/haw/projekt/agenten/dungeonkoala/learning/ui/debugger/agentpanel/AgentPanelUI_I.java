package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;

public interface AgentPanelUI_I {
	/**
	 * Registriert den übergebenen Listener an diesem UI-Element.
	 * @param listener Der zu registrierende Listener
	 */
	void addAgentPanelListener(AgentPanelUIListener_I listener);
	
	/**
	 * Entfernt den übergebenen Listener von diesem UI-Element.
	 * @param listener Der zu entfernende Listener
	 */
	void removeAgentPanelListener(AgentPanelUIListener_I listener);
	
	/**
	 * Legt die Policies fest, die über diese Benutzeroberfläche ausgewählt werden können.
	 * @param policies Eine Liste von {@link Policy_I Policies}
	 */
	void setPolicies(List<Policy_I> policies);
	
	/**
	 * Gibt die aktuell auf der Benutzeroberfläche ausgewählte {@link Policy_I} zurück.
	 * @return Die ausgewählte Policy
	 */
	Policy_I getPolicy();
	
	/**
	 * Legt die auf der Benutzeroberfläche ausgewählte {@link Policy_I} fest.
	 * @param policy Die ausgewählte Policy
	 */
	void setPolicy(Policy_I policy);
	
	/**
	 * Gibt die aktuell auf der Benutzeroberfläche ausgewählte Lernrate zurück.
	 * @return Die ausgewählte Lernrate
	 */
	double getLearningRate();
	
	/**
	 * Legt die auf der Benutzeroberfläche ausgewählte Lernrate fest.
	 * @param learningRate Die ausgewählte Lernrate
	 */
	void setLearningRate(double learningRate);
	
	/**
	 * Gibt den aktuell auf der Benutzeroberfläche ausgewählten Discount-Faktor zurück.
	 * @return Der ausgewählte Discount-Faktor
	 */
	double getDiscountFactor();
	
	/**
	 * Legt den auf der Benutzerfläche ausgewählten Discount-Faktor fest.
	 * @param discountFactor Der ausgewählte Discount-Factor
	 */
	void setDiscountFactor(double discountFactor);
	
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
}
