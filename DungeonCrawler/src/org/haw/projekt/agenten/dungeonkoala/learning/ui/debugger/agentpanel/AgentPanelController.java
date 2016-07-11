package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel;

import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.learning.QLearningAgent;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policies;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;

public class AgentPanelController implements AgentPanelUIListener_I {
	private AgentPanelUI_I _ui;
	private QLearningAgent _agent;
	
	public AgentPanelController(AgentPanelUI_I ui) {
		_ui = Objects.requireNonNull(ui);
		_ui.addAgentPanelListener(this);
		_ui.setPolicies(Policies.getAllPolicies());
	}
	
	public void setAgent(QLearningAgent agent) {
		_agent = agent;
		if(_agent != null) {
			_ui.setPolicy(_agent.getPolicy());
			_ui.setLearningRate(_agent.getLearningRate());
			_ui.setDiscountFactor(_agent.getDiscountFactor());
		}
	}
	
	@Override
	public void onPolicyChanged(Policy_I policy) {
		if(_agent != null) {
			_agent.setPolicy(policy);
		}
	}
	
	@Override
	public void onLearningRateChanged(double learningRate) {
		if(_agent != null) {
			_agent.setLearningRate(learningRate);
		}
	}
	
	@Override
	public void onDiscountFactorChanged(double discountFactor) {
		if(_agent != null) {
			_agent.setDiscountFactor(discountFactor);
		}
	}
}
