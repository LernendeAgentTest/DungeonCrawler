package org.haw.projekt.agenten.dungeonkoala.learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.DungeonKoalaFeatureSet;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.features.AbstractDungeonKoalaFeature;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

public class QLearningBehaviour extends QLearningEnvironment<DungeonKoalaFeatureSet> implements EntityBehaviour_I, GameListener_I {
	private static final String REWARD_PER_ACTION = "PER_ACTION";
	private static final String REWARD_ENEMY_DEAD = "ENEMY_DEAD";
	private static final String REWARD_SELF_DEAD  = "SELF_DEAD";
	
	private static final int PLAYER_DEAD_STATE = 0;
	private static final int ENEMY_DEAD_STATE = 1;
	
	private EventDispatcher<QLearningBehaviourListener_I> _dispatcher;
	
	private List<Class<? extends Action>> _actions;
	private Map<Class<? extends Action>, Integer> _actionsMapping;
	
	private Entity myEntity;
	private Entity myOpponent;
	private Room myRoom;
	
	public QLearningBehaviour(Policy_I policy, List<AbstractDungeonKoalaFeature> features, List<Class<? extends Action>> actions) {
		this(policy, features, actions, QLearningAgent.DEFAULT_START_QVALUE, QLearningAgent.DEFAULT_LEARNING_RATE, QLearningAgent.DEFAULT_DISCOUNT_FACTOR);
	}
	
	public QLearningBehaviour(Policy_I policy, List<AbstractDungeonKoalaFeature> features, List<Class<? extends Action>> actions, double startQValue, double learningRate, double discountFactor) {
		this(policy, new DungeonKoalaFeatureSet(features), actions, startQValue, learningRate, discountFactor);
	}
	
	private QLearningBehaviour(Policy_I policy, DungeonKoalaFeatureSet featureSet, List<Class<? extends Action>> actions, double startQValue, double learningRate, double discountFactor) {
		super(new QLearningAgent(policy, featureSet.getFeatureStateRange(), actions.size(), startQValue, learningRate, discountFactor),featureSet);
		_dispatcher = new EventDispatcher<QLearningBehaviourListener_I>();
		
		_actions = Objects.requireNonNull(actions);
		_actionsMapping = new HashMap<>();
		for(int i=0; i<_actions.size(); i++) {
			_actionsMapping.put(_actions.get(i), i);
		}
		
		// Setup Rewards
		putReward(REWARD_PER_ACTION, -10);
		putReward(REWARD_ENEMY_DEAD, 100);
		putReward(REWARD_SELF_DEAD, -100);
	}
	
	@Override
	public Action makeNextMove(Entity me, Entity opponent, List<Action> actions, Room room) {
		List<Integer> agentActions = mapToAgentActions(actions);
		myRoom = room;
		int choosenAction = chooseAction(agentActions);
		Action action = mapToDungeonKoalaAction(choosenAction, actions);
		_dispatcher.fireEvent(l -> l.onActionChosen(action));
		return action;
	}
	
	/**
	 * Erzeugt aus der übergebenen Liste von DungeonKoala-Actions eine Liste
	 * von Aktionen für den Agenten (codiert als Integer).
	 * <p>Hierbei ist wichtig zu berücksichtigen, dass es theoretisch möglich ist,
	 * dass die Liste der DungeonKoala-Aktionen nicht in einer festen Reihenfolge
	 * definiert sind und nicht alle Aktionen enthalten muss, die das Spiel vorsieht.
	 * Deshalb wird ein Mapping der Actions auf die Integer-Kodierung im Konstruktor
	 * erzeugt und hier verwendet. Dadurch wird sichergestellt, dass zu jeder Aktion
	 * immer der Integer-Wert gefunden
	 * @param actions Eine Liste der DungeonKoala-Aktionen die in diesem Zug möglich sind
	 * @return Eine Liste aller möglichen Aktionen, codiert als Integer
	 */
	private List<Integer> mapToAgentActions(List<Action> actions) {
		List<Integer> possibleActions = new ArrayList<Integer>();
		for(Action action : actions) {
			Integer agentAction = _actionsMapping.get(action.getClass());
			if(agentAction != null) {
				possibleActions.add(agentAction);
			}
		}
		return possibleActions;
	}
	
	private Action mapToDungeonKoalaAction(int agentAction, List<Action> possibleActions) {
		Class<? extends Action> actionCls = _actions.get(agentAction);
		for(Action action : possibleActions) {
			if(actionCls.equals(action.getClass())) {
				return action;
			}
		}
		throw new IllegalStateException("Die vom Agenten ausgewählte Aktion konnte nicht zu einer DungeonKoala-Aktion gemappt werden!");
	}
	
	public void addQLearningBehaviourListener(QLearningBehaviourListener_I listener) {
		_dispatcher.addListener(listener);
	}
	
	public void removeQLearningBehaviourListener(QLearningBehaviourListener_I listener) {
		_dispatcher.removeListener(listener);
	}
	
	@Override
	public String getDisplayName() {
		return "Q-learning";
	}
	
	/**
	 * Berechnet die Belohnung für den Agenten für die übergebene Aktion die er im übergebenen Zustand
	 * ausgeführt hat.
	 * @param me Die Entität zu der dieses Verhalten gehört
	 * @param opponent Der Gegner
	 * @return Die Belohnung
	 */
	private double calculateReward(Entity me, Entity opponent) {
		double reward = getReward(REWARD_PER_ACTION);
		if(!me.isAlive()) {
			reward = getReward(REWARD_SELF_DEAD);
		} else if(!opponent.isAlive()) {
			reward = getReward(REWARD_ENEMY_DEAD);
		}
		return reward;
	}
	
	/**
	 * Sets the Q-state to the given state.
	 */
	private void updateCurrentState(int state) {
		_lastState = _currentState;
		_currentState = state;
		_dispatcher.fireEvent((l) -> l.onStateChanged(state));
	}
	
	@Override
	public void onGameStarted(Player player, int rooms) {
		myEntity = player;
	}
	
	@Override
	public void onGameEnded(Player player, int rooms) {
		updateCurrentState((player.getHealth().getValue() > 0) ? ENEMY_DEAD_STATE : PLAYER_DEAD_STATE);
		reward(calculateReward(myEntity, myOpponent));
	}
	
	@Override
	public void onRoomEntered(Room room) {
		myRoom = room;
		myOpponent = room.getEnemy();
		updateFeatures(featureSet -> featureSet.updateFeatures(myEntity, myOpponent, myRoom));
		_dispatcher.fireEvent((l) -> l.onStateChanged(getCurrentState()));
	}
	
	@Override
	public void onRoomCleared(Room room) {
		updateCurrentState(ENEMY_DEAD_STATE);
		reward(calculateReward(myEntity, myOpponent));
	}
	
	@Override
	public void onRoundStarted() {
	}
	
	@Override
	public void onRoundEnded() {
		updateFeatures(featureSet -> featureSet.updateFeatures(myEntity, myOpponent, myRoom));
		_dispatcher.fireEvent((l) -> l.onStateChanged(getCurrentState()));
		reward(calculateReward(myEntity, myOpponent));
	}

	public double getDefaultReward() {
		return getReward(REWARD_PER_ACTION);
	}

	public void setDefaultReward(double defaultReward) {
		setReward(REWARD_PER_ACTION, defaultReward);
	}

	public double getEnemyDeadReward() {
		return getReward(REWARD_ENEMY_DEAD);
	}

	public void setEnemyDeadReward(double enemyDeadReward) {
		setReward(REWARD_ENEMY_DEAD, enemyDeadReward);
	}

	public double getPlayerDeadReward() {
		return getReward(REWARD_SELF_DEAD);
	}

	public void setPlayerDeadReward(double playerDeadReward) {
		setReward(REWARD_SELF_DEAD, playerDeadReward);
	}
}
