package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.explorationpanel;

import java.util.Objects;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.explorationpolicy.ExplorationPolicyListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.explorationpolicy.StaticExplorationPolicy;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.AgentPanelUIListener_I;

public class ExplorationController implements ExplorationUIListener_I, AgentPanelUIListener_I, ExplorationPolicyListener_I {

	private ExplorationUI_I _ui;
	private StaticExplorationPolicy _policy;
	
	public ExplorationController(ExplorationUI_I ui) {
		_ui = Objects.requireNonNull(ui);
		_ui.addExplorationListener(this);
	}
	
	public void setPolicy(Policy_I policy){
		if(policy != null && policy instanceof StaticExplorationPolicy){
			if(_policy != null) {
				_policy.removeExplorationListener(this);
			}
			_policy = (StaticExplorationPolicy)policy;
			_policy.setExplorationRate(_ui.getExplorationRate());
			_policy.addExplorationListener(this);
		}
	}
	
	@Override
	public void onExplorationRateChanged(double defaultReward) {
		if(_policy != null){
			_policy.setExplorationRate(defaultReward);
		}
	}

	@Override
	public void onPolicyChanged(Policy_I policy) {
		if(policy instanceof StaticExplorationPolicy) {
			_ui.setExplorationRateEnabled(true);
			setPolicy(policy);
		} else {
			_ui.setExplorationRateEnabled(false);
		}
	}

	@Override
	public void onLearningRateChanged(double learningRate) {
	}

	@Override
	public void onDiscountFactorChanged(double discountFactor) {
	}

	@Override
	public void onExplored() {
		_ui.setExplorationCounter(_ui.getExplorationCounter() + 1.0);
	}
}
