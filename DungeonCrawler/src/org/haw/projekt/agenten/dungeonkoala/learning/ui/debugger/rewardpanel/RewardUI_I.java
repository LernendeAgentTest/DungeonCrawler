package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.rewardpanel;

import java.awt.Component;

public interface RewardUI_I {
	
	Component getComponent();
	
	double getDefaultReward();
	double getPlayerDeadReward();
	double getEnemyDeadReward();
	
	void setDeafaultReward(double reward);
	void setPlayerDeadReward(double reward);
	void setEnemyDeadReward(double reward);
	
	boolean isPlayerDeadRewardEnabled();
	void setPlayerDeadRewardEnabled(boolean enabled);

	void addRewardListener(ExplorationUIListener_I listener);
	void removeRewardListener(ExplorationUIListener_I listener);
}
