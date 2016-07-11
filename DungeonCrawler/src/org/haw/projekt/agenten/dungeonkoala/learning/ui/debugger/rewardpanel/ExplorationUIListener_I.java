package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.rewardpanel;

public interface ExplorationUIListener_I {
	void onDefaultRewardChanged(double defaultReward);
	void onEnemyDeadRewardChanged(double enemyDeadReward);
	void onPlayerDeadRewardChanged(double playerDeadReward);
	void onGivePlayerDeadRewardChanged(boolean enabled);
}
