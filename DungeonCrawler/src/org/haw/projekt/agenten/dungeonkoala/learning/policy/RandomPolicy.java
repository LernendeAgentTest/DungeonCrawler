package org.haw.projekt.agenten.dungeonkoala.learning.policy;

import java.util.List;
import java.util.Random;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTable;

/**
 * Implements a random policy-decision making process. Always chooses the action randomly.
 */
public class RandomPolicy implements Policy_I {
	private static final String NAME = "RandomPolicy";
	
	private final Random _randomNumberGenerator = new Random();
	
	@Override
	public int chooseAction(int state, List<Integer> possibleActions, QValueTable qValues) {
		int chosenAction = possibleActions.get(_randomNumberGenerator.nextInt(possibleActions.size()));
		return chosenAction;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
