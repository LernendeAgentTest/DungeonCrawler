package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator;

import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.feature.editor.FeatureEditorUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.learning.LearningUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.AgentPanelUIListener_I;

public interface QLearnerConfiguratorUIListener_I extends AgentPanelUIListener_I, FeatureEditorUIListener_I, LearningUIListener_I {
	/**
	 * Wird aufgerufen wenn der Debugger-Button auf der UI gedrückt wurde.
	 */
	void onShowDebugger();
	
	/**
	 * Wird aufgerufen, wenn der Lernen-Button auf der UI gedrückt wurde.
	 */
	void onLearn();
	
	/**
	 * Wird aufgerufen, wenn der Start-Button auf der UI gedrückt wurde.
	 */
	void onStart();
	
	void onDoStatistics();
}
