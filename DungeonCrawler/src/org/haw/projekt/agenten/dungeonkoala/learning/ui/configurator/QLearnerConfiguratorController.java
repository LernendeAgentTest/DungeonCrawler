package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.game.GameSession;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Actions;
import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.generator.RandomPooledEnemyGenerator;
import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameAdapter;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;
import org.haw.projekt.agenten.dungeonkoala.game.room.generator.SimpleRoomGenerator;
import org.haw.projekt.agenten.dungeonkoala.game.ui.GameUIController;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.SwingGameUI;
import org.haw.projekt.agenten.dungeonkoala.learning.QLearningBehaviour;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.features.AbstractDungeonKoalaFeature;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.features.DungeonKoalaFeatures;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policies;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTableListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.agent.AgentPanelController;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.feature.editor.FeatureEditorController;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.statistics.StatisticUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.swing.SwingQLearnerConfiguratorUI;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.QLearnerDebuggerController;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.logging.Logger;

public class QLearnerConfiguratorController implements QLearnerConfiguratorUIListener_I {
	private static final List<AbstractDungeonKoalaFeature> DEFAULT_ACTIVE_FEATURES = Collections.unmodifiableList(Arrays.asList(
			DungeonKoalaFeatures.SELF_LIFE_FEATURE,
			DungeonKoalaFeatures.ENEMY_LIFE_FEATURE,
			DungeonKoalaFeatures.SELF_ELEMENT_FEATURE,
			DungeonKoalaFeatures.ENEMY_ELEMENT_FEATURE,
			DungeonKoalaFeatures.SELF_CHARGE_LEVEL_FEATURE,
			DungeonKoalaFeatures.ENEMY_CHARGE_LEVEL_FEATURE,
			DungeonKoalaFeatures.ENEMY_BEHAVIOURS_FEATURE
	));
	
	private QLearnerDebuggerController _debugger;
	private GameUIController _gameUI;
	
	private AgentPanelController _agentPanelController;
	private FeatureEditorController _featureEditorController;

	private QLearnerConfiguratorUI_I _ui;
	private StatisticUI_I _statisticUI;
	
	private int _statisticWins, _statisticGames, _statisticsKillcount, _statisticsMaxkills, _statisticsMaxkillcounter, _statisticsActions;
	
	private List<Class<? extends Action>> _actionList;
	private QLearningBehaviour _behaviour;
	
	public QLearnerConfiguratorController() {
		_debugger = new QLearnerDebuggerController();
		
		_actionList = new ArrayList<>(Actions.getAllActions());
		
		_ui = new SwingQLearnerConfiguratorUI();
		_agentPanelController = new AgentPanelController(_ui.getAgentPanelUI(), Policies.getAllPolicies());
		_featureEditorController = new FeatureEditorController(_ui.getFeatureEditorUI(), new ArrayList<>(DEFAULT_ACTIVE_FEATURES), new ArrayList<>(DungeonKoalaFeatures.getAllFeatures()));
		_statisticUI = _ui.getStatisticUI();
		
		_ui.addQLearnerConfiguratorUIListener(this);
	}
	
	public void start() {
		_ui.show();
	}
	
	@Override
	public void onShowDebugger() {
		if(_behaviour == null) {
			newBehaviour();
		}
		
		_debugger.show();
	}
	
	@Override
	public void onLearn() {
		if(_behaviour == null) {
			newBehaviour();
		}
		
		// Neuen Thread starten, damit der Swing-Event-Thread nicht blockiert wird
		new Thread(() -> {
			bearbeitendeElementeSperren();
			_debugger.show();
			
			int progressFromLast = _ui.getProgress();
			int iterations = progressFromLast + _ui.getLearningUI().getIterations();
			_ui.initProgress(0, iterations);
			for(int i=progressFromLast; i<iterations; i++) {
				_ui.updateProgress();
				GameSession game = new GameSession(new SimpleRoomGenerator(new RandomPooledEnemyGenerator()));
				game.addGameListener(_behaviour);
				game.addGameListener(Logger.logger);
				game.start(_behaviour);
			}
			_ui.updateProgress(iterations);
			
			bearbeitendeElementeFreigeben();
		}).start();
	}
	
	@Override
	public void onStart() {
		if(_behaviour == null) {
			newBehaviour();
		}
		
		// Neuen Thread starten, damit der Swing-Event-Thread nicht blockiert wird
		new Thread(() -> {
			bearbeitendeElementeSperren();
			_debugger.show();
			
			// Altes Fenster schließen, falls es noch offen sein sollte
			if(_gameUI != null) {
				_gameUI.stop();
			}
			
			// Spiel starten
			_gameUI = new GameUIController(new SwingGameUI(), _behaviour);
			_gameUI.addGameListener(_behaviour);
			_gameUI.addGameListener(Logger.logger);
			
			// Debugger zum Pausieren bringen, bevor das echte Spiel startet
			_debugger.getUI().getDebugPanelUI().setPauseOnQValueChange(true);
			_debugger.getUI().getDebugPanelUI().setPauseOnActionChoosen(true);
			
			_gameUI.start();
			
			bearbeitendeElementeFreigeben();
		}).start();
	}
	
	private void bearbeitendeElementeSperren() {
		_ui.setEnabled(false);
		_agentPanelController.setEnabled(false);
		_featureEditorController.setEnabled(false);
		_ui.getLearningUI().setEnabled(false);
	}
	
	private void bearbeitendeElementeFreigeben() {
		_ui.setEnabled(true);
		_agentPanelController.setEnabled(true);
		_ui.getLearningUI().setEnabled(true);
		_featureEditorController.setEnabled(true);
	}
	
	@Override
	public void onPolicyChanged(Policy_I policy) {
		resetBehaviour();
	}
	
	@Override
	public void onLearningRateChanged(double learningRate) {
		resetBehaviour();
	}
	
	@Override
	public void onDiscountFactorChanged(double discountFactor) {
		resetBehaviour();
	}
	
	@Override
	public void onAddFeatures(List<AbstractDungeonKoalaFeature> features) {
		// Nur resetten, den Rest erledigt der FeatureEditorController
		resetBehaviour();
	}

	@Override
	public void onRemoveFeatures(List<AbstractDungeonKoalaFeature> features) {
		// Nur resetten, den Rest erledigt der FeatureEditorController
		resetBehaviour();
	}
	
	@Override
	public void onIterationsChanged(int iterations) {
		_ui.initProgress(0, _ui.getProgress() + iterations);
		_ui.updateProgress(_ui.getProgress());
	}
	
	private void resetBehaviour() {
		_ui.initProgress(0, _ui.getLearningUI().getIterations());
		_ui.updateProgress(0);
		
		newBehaviour();
	}
	
	private void newBehaviour() {
		if(_behaviour != null) {
			_behaviour.removeQLearningBehaviourListener(Logger.logger);
		}
		
		_behaviour = new QLearningBehaviour(
				_agentPanelController.getPolicy(),
				_featureEditorController.getActiveFeatures(),
				_actionList,
				0.0,
				_agentPanelController.getLearningRate(),
				_agentPanelController.getDiscountFactor()
		);
		_behaviour.addQLearningBehaviourListener(Logger.logger);
		
		_debugger.setAgent(_behaviour.getQLearningAgent(), _behaviour.getFeatureSet(), _actionList);
		_debugger.setEnvironment(_behaviour);
		_debugger.setPolicy(_behaviour.getQLearningAgent().getPolicy());
		_behaviour.getQLearningAgent().getQValues().addQValueTableListener(new QValueTableListener_I() {
			
			@Override
			public void onQValueChanged(int state, int action, double value) {
				//no interest
			}
			
			@Override
			public void onNewStateReached(int reachedStates,int maxStates) {
				_ui.getLearningUI().setReachedStates(reachedStates,maxStates);
			}
		});
		
	}

	@Override
	public void onDoStatistics() {
		_statisticWins = 0;
		_statisticGames = 0;
		_statisticsKillcount = 0;
		_statisticsMaxkills = 0;
		_statisticsActions = 0;
		if (_behaviour != null) {
			double oldLearnRate = _behaviour.getQLearningAgent().getLearningRate();
			_behaviour.getQLearningAgent().setLearningRate(0);
			new Thread(() -> {
				bearbeitendeElementeSperren();
				int iterations = _ui.getLearningUI().getIterations();
				for (int i = 0; i < iterations; i++) {
					GameSession game = new GameSession();
					game.addGameListener(_behaviour);
					_statisticsMaxkillcounter = 0;					
					game.addGameListener(new GameAdapter() {
						@Override
						public void onRoundStarted(){
							_statisticsActions++;
							if(_statisticsKillcount>0)
								_statisticUI.setAverageActions(_statisticsActions/_statisticsKillcount);
						}
						@Override
						public void onGameEnded(Player player, int rooms) {
							if (player.isAlive()) _statisticWins++;
							_statisticGames++;
							_statisticUI.setWinrate((double) _statisticWins / _statisticGames);
							_statisticUI.setGames(_statisticGames);
							_statisticUI.setWins(_statisticWins);
							if (_statisticsMaxkills<_statisticsMaxkillcounter){
								_statisticUI.setMaxKills(_statisticsMaxkillcounter);
								_statisticsMaxkills = _statisticsMaxkillcounter;
							}
						}
						@Override
						public void onRoomCleared(Room room){
							_statisticsKillcount++;
							_statisticsMaxkillcounter++;
							if(_statisticGames>0)
								_statisticUI.setAverageKillcount(_statisticsKillcount/_statisticGames);
							_statisticUI.setKills(_statisticsKillcount);
						}
					});
					game.start(_behaviour);
				}
				_behaviour.getQLearningAgent().setLearningRate(oldLearnRate);
				bearbeitendeElementeFreigeben();
			}).start();
		}
	}
}
