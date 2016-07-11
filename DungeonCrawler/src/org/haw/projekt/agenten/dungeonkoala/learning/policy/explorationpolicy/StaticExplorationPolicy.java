package org.haw.projekt.agenten.dungeonkoala.learning.policy.explorationpolicy;

import java.util.List;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.RandomPolicy;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.StaticPolicy;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTable;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

/**
 * Implements an exploring policy-decision making process.
 * On every call there exists a chance of chosing a non-optimal Action.
 *
 */
public class StaticExplorationPolicy implements Policy_I {
	private static final String NAME = "StaticExplorationPolicy";
	
	private static final double DEFAULT_EXPLORATION_RATE = 0.1;
	
	private double _explorationRate;
	private Policy_I _staticPolicy;
	private Policy_I _randomPolicy;
	
	private EventDispatcher<ExplorationPolicyListener_I> _eventDispatcher;
	
	public StaticExplorationPolicy() {
		this(DEFAULT_EXPLORATION_RATE);
	}
	
	public StaticExplorationPolicy(double explorationRate) {
		_eventDispatcher = new EventDispatcher<>();
		_explorationRate = explorationRate;
		_staticPolicy = new StaticPolicy();
		_randomPolicy = new RandomPolicy();
	}

	@Override
	public int chooseAction(int state, List<Integer> possibleActions, QValueTable qValues) {
		int chosenAction;
		if(Math.random() > _explorationRate) {
			chosenAction = _staticPolicy.chooseAction(state, possibleActions, qValues);
		} else {
			chosenAction = _randomPolicy.chooseAction(state, possibleActions, qValues);
			_eventDispatcher.fireEvent((l) -> l.onExplored());
		}
		return chosenAction;
	}

	public double getExplorationRate() {
		return _explorationRate;
	}

	public void setExplorationRate(double explorationRate) {
		_explorationRate = explorationRate;
	}
	
	public void addExplorationListener(ExplorationPolicyListener_I listener) {
		_eventDispatcher.addListener(listener);
	}
	
	public void removeExplorationListener(ExplorationPolicyListener_I listener) {
		_eventDispatcher.removeListener(listener);
	}

	@Override
	public String getName() {
		return NAME;
	}
}
