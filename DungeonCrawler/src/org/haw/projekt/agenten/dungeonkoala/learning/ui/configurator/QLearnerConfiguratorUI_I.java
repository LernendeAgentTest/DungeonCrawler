package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator;

import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.feature.editor.FeatureEditorUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.learning.LearningUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.statistics.StatisticUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.AgentPanelUI_I;

public interface QLearnerConfiguratorUI_I {
	/**
	 * Registriert den übergebenen Listener an diesem UI-Element.
	 * @param listener Der zu registrierende Listener
	 */
	void addQLearnerConfiguratorUIListener(QLearnerConfiguratorUIListener_I listener);
	
	/**
	 * Entfernt diesen Listener von diesem UI-Element.
	 * @param listener Der zu entfernende Listener
	 */
	void removeQLearnerConfiguratorUIListener(QLearnerConfiguratorUIListener_I listener);
	
	/**
	 * Gibt das UI-Element für die Agenten-Konfigurations-Schaltflächen.
	 * @return Das UI-Element für die Agenten-Konfigurations-Schaltflächen
	 */
	AgentPanelUI_I getAgentPanelUI();
	
	/**
	 * Gibt das UI-Element für die Bearbeitungs-Schaltflächen für die Feature-Liste zurück.
	 * @return Das UI-Element für die Bearbeitungs-Schaltflächen
	 */
	FeatureEditorUI_I getFeatureEditorUI();
	
	/**
	 * Gibt das UI-Element für das Learning-Panel zurück.
	 * @return Das UI-Element für das Learning-Panel
	 */
	LearningUI_I getLearningUI();

	StatisticUI_I getStatisticUI();
	
	/**
	 * Gibt den aktuellen Fortschritt des Fortschritt-Balkens zurück.
	 * @return Der aktuelle Fortschritt
	 */
	int getProgress();
	
	/**
	 * Setzt die Grenzen für den Fortschritts-Balken.
	 * @param min Der Minimalwert
	 * @param max Der Maximalwert
	 */
	void initProgress(int min, int max);
	
	/**
	 * Setzt den Fortschritt des Fortschritt-Balkens um eins weiter.
	 */
	void updateProgress();
	
	/**
	 * Setzt den aktuellen Fortschritt für den Fortschritts-Balken.
	 * @param current Der aktuelle Wert
	 */
	void updateProgress(int current);
	
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
	
	/**
	 * Zeigt die UI an.
	 */
	void show();
	
	/**
	 * Versteckt die UI.
	 */
	void hide();
}
