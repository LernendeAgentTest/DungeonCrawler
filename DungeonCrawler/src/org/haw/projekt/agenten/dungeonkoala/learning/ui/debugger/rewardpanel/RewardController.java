package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.rewardpanel;

import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.learning.QLearningBehaviour;

public class RewardController implements ExplorationUIListener_I {

	private RewardUI_I _ui;
	private QLearningBehaviour _environment;
	
	public RewardController(RewardUI_I ui) {
		_ui = Objects.requireNonNull(ui);
		_ui.addRewardListener(this);
	}
	
	public void setEnvironment(QLearningBehaviour environment){
		if(environment != null){
			_environment = environment;
			_environment.setDefaultReward(_ui.getDefaultReward());
			_environment.setEnemyDeadReward(_ui.getEnemyDeadReward());
			_environment.setPlayerDeadReward(_ui.getPlayerDeadReward());
		}
	}
	
	@Override
	public void onDefaultRewardChanged(double defaultReward) {
		if(_environment != null){
			_environment.setDefaultReward(defaultReward);
		}
	}

	@Override
	public void onEnemyDeadRewardChanged(double enemyDeadReward) {
		if(_environment != null) {
			_environment.setEnemyDeadReward(enemyDeadReward);
		}
	}

	@Override
	public void onPlayerDeadRewardChanged(double playerDeadReward) {
		if(_environment != null) {
			_environment.setPlayerDeadReward(playerDeadReward);
		}
	}

	@Override
	public void onGivePlayerDeadRewardChanged(boolean enabled) {

	}

}
