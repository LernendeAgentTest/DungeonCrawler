package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.explorationpanel;

import java.awt.Component;

public interface ExplorationUI_I {
	
	Component getComponent();
	
	double getExplorationRate();
	double getExplorationCounter();
	
	void setExplorationRate(double reward);
	void setExplorationCounter(double reward);
	void setExplorationRateEnabled(boolean enabled);
	
	void addExplorationListener(ExplorationUIListener_I listener);
	void removeExplorationListener(ExplorationUIListener_I listener);
}
