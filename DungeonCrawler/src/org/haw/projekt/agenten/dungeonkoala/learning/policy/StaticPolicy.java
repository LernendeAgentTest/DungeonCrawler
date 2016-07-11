package org.haw.projekt.agenten.dungeonkoala.learning.policy;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTable;

/**
 * Implements a static policy-decision making process. Always chooses the action which in the past led to the best
 * results.
 */
public class StaticPolicy implements Policy_I {
	private static final String NAME = "StaticPolicy";
	
	@Override
	public int chooseAction(int state, List<Integer> possibleActions, QValueTable qValues) {
		double highestQValue = -Double.MAX_VALUE;
		int choosenAction = -1;
		for(Integer action : possibleActions) {
			double currentQValue = qValues.getQValue(state, action);
			if(currentQValue > highestQValue) {
				highestQValue = currentQValue;
				choosenAction = action;
			}
		}
		return choosenAction;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
