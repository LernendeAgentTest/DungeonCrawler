package org.haw.projekt.agenten.dungeonkoala.learning;

import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;

public interface QLearningBehaviourListener_I {
	public void onActionChosen(Action action);
	public void onStateChanged(int newState);
}
