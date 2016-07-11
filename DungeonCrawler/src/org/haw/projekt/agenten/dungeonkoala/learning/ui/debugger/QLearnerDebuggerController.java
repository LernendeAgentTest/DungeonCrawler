package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger;

import java.util.List;
import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.learning.QLearningAgent;
import org.haw.projekt.agenten.dungeonkoala.learning.QLearningBehaviour;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.FeatureSet_I;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.AgentPanelController;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.debugpanel.DebugPanelController;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.explorationpanel.ExplorationController;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable.QValueTableController;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.rewardpanel.RewardController;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.swing.SwingQLearnerDebuggerUI;

public class QLearnerDebuggerController implements QLearnerDebuggerUIListener_I {
	private QLearnerDebuggerUI_I _ui;
	private AgentPanelController _agentController;
	private DebugPanelController _debugController;
	private QValueTableController _qValuesController;
	private RewardController _rewardController;
	private ExplorationController _explorationController;
	
	private QLearningAgent _agent;
	
	public QLearnerDebuggerController() {
		_ui = new SwingQLearnerDebuggerUI();
		_ui.addQLearnerUIListener(this);
		_agentController = new AgentPanelController(_ui.getAgentPanelUI());
		_debugController = new DebugPanelController(_ui.getDebugPanelUI());
		_qValuesController = new QValueTableController(_ui.getQValueTableUI());
		_rewardController = new RewardController(_ui.getRewardUI());
		_explorationController = new ExplorationController(_ui.getExplorationUI());
		
		initListeners();
	}
	
	private void initListeners() {
		_ui.getAgentPanelUI().addAgentPanelListener(_explorationController);
	}

	public void setAgent(QLearningAgent agent, FeatureSet_I featureSet, List<Class<? extends Action>> actionList) {
		_agent = Objects.requireNonNull(agent);
		_ui.getFeatureListUI().setFeatureSet(featureSet);
		_qValuesController.setQValues(_agent.getQValues(), featureSet, actionList);
		_agentController.setAgent(_agent);
		_debugController.setAgent(_agent);
	}
	
	public void setEnvironment(QLearningBehaviour environment){
		_rewardController.setEnvironment(environment);
	}

	public void setPolicy(Policy_I policy) {
		_explorationController.setPolicy(policy);
	}
	
	public void show() {
		_ui.show();
	}
	
	public void hide() {
		_ui.hide();
	}
	
	public QLearnerDebuggerUI_I getUI() {
		return _ui;
	}
}
