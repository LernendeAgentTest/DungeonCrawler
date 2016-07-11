package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger;

import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.AgentPanelUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.debugpanel.DebugPanelUI;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.explorationpanel.ExplorationUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.FeatureListUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable.QValueTableUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.rewardpanel.RewardUI_I;

/**
 * Schnittstelle für die Benutzeroberfläche des QLearner Debuggers.
 */
public interface QLearnerDebuggerUI_I {
	/**
	 * Fügt den übergebenen Listener dieser UI-Komponente hinzu.
	 * @param listener Der hinzuzufügende Listener
	 */
	void addQLearnerUIListener(QLearnerDebuggerUIListener_I listener);
	
	/**
	 * Entfernt den übergebenen Listener von dieser UI-Komponente.
	 * @param listener Der zu entfernende Listener
	 */
	void removeQLearnerUIListener(QLearnerDebuggerUIListener_I listener);
	
	/**
	 * Gibt das UI-Element für die verstellbaren Werte des Agenten zurück.
	 * @return Das UI-Element für den Agenten
	 */
	AgentPanelUI_I getAgentPanelUI();
	
	/**
	 * Gibt das UI-Element für die Feature-Liste zurück.
	 * @return Das UI-Element für die Feature-Liste
	 */
	FeatureListUI_I getFeatureListUI();
	
	/**
	 * Gibt das UI-Element für die QValue-Tabelle zurück.
	 * @return Das UI-Element für die QValue-Tabelle
	 */
	QValueTableUI_I getQValueTableUI();
	
	/**
	 * Gibt das UI-Element für die Reward Config zurück.
	 * @return das UI-Element für Reward Config
	 */
	RewardUI_I getRewardUI();
	
	/**
	 * Gibt das UI-Element für das Debug-Panel zurück.
	 * @return Das UI-Element für das Debug-Panel
	 */
	DebugPanelUI getDebugPanelUI();
	
	/**
	 * Gibt das UI-Element für das Exploration-Panel zurück.
	 * @return Das UI-Element für das Exploration-Panel
	 */
	ExplorationUI_I getExplorationUI();
	
	/**
	 * Zeigt die UI an.
	 */
	void show();
	
	/**
	 * Versteckt die UI.
	 */
	void hide();
}
