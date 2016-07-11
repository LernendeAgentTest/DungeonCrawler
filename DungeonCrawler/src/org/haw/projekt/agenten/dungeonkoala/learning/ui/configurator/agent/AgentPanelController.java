package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.agent;

import java.util.List;
import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.learning.QLearningAgent;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.AgentPanelUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.AgentPanelUI_I;

public class AgentPanelController implements AgentPanelUIListener_I {
	private AgentPanelUI_I _ui;
	private Policy_I _policy;
	private double _learningRate;
	private double _discountFactor;
	
	public AgentPanelController(AgentPanelUI_I ui, List<Policy_I> policies) {
		_learningRate = QLearningAgent.DEFAULT_LEARNING_RATE;
		_discountFactor = QLearningAgent.DEFAULT_DISCOUNT_FACTOR;
		_policy = policies.get(0);
		
		_ui = Objects.requireNonNull(ui);
		_ui.addAgentPanelListener(this);
		
		_ui.setPolicies(Objects.requireNonNull(policies));
		_ui.setLearningRate(_learningRate);
		_ui.setDiscountFactor(_discountFactor);
		_ui.setPolicy(_policy);
	}
	
	public Policy_I getPolicy() {
		return _policy;
	}
	
	public double getLearningRate() {
		return _learningRate;
	}
	
	public double getDiscountFactor() {
		return _discountFactor;
	}
	
	public void setEnabled(boolean enable) {
		_ui.setEnabled(enable);
	}
	
	@Override
	public void onPolicyChanged(Policy_I policy) {
		_policy = policy;
	}
	
	@Override
	public void onLearningRateChanged(double learningRate) {
		_learningRate = learningRate;
	}
	
	@Override
	public void onDiscountFactorChanged(double discountFactor) {
		_discountFactor = discountFactor;
	}
}
