package org.haw.projekt.agenten.dungeonkoala.learning.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTable;

/**
 * Implements an exploring policy-decision making process.
 */
public class DynamicExplorationPolicy implements Policy_I {
	private static final String NAME = "DynamicExplorationPolicy";
	
	private final Random _randomNumberGenerator = new Random();

	@Override
	public int chooseAction(int state, List<Integer> possibleActions, QValueTable qValues) {
		double sum = Double.MIN_VALUE;
		double lowestQValue = Double.MAX_VALUE;
		Map<Integer, Double> actionPercentageMapping = new HashMap<>();
		for(Integer action : possibleActions) {
			double qValue = qValues.getQValue(state, action);
			lowestQValue = (lowestQValue > qValue) ? qValue : lowestQValue;
		}
		
		for(Integer action : possibleActions) {
			double qValue = qValues.getQValue(state, action);
			sum += Math.abs(qValue - lowestQValue);
		}
		
		for(Integer action : possibleActions) {
			double qValue = qValues.getQValue(state, action);
			Double percentage;
			if(sum == Double.MIN_VALUE) {
				percentage = 0.25;
			} else {
				percentage = Math.abs(qValue - lowestQValue) / sum;
			}
			actionPercentageMapping.put(action, percentage);
		}
		double randomNumber = _randomNumberGenerator.nextDouble();
		int chosenAction = -1;
		double subSum = 1.0;
		
		for(Integer action : possibleActions) {
			subSum -= actionPercentageMapping.get(action);
			if(chosenAction == -1 && subSum <= randomNumber) {
				chosenAction = action;
			}
		}
		
		if(chosenAction == -1) {
			chosenAction = 1;
		}
		return chosenAction;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
