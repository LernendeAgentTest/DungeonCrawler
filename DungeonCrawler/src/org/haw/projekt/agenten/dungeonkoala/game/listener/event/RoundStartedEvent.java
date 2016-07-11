package org.haw.projekt.agenten.dungeonkoala.game.listener.event;

import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;

/**
 * Ein Event das über den Start einer Runde informiert.
 */
public class RoundStartedEvent extends GameEvent_A {
	@Override
	public void fireEvent(GameListener_I l) {
		l.onRoundStarted();
	}
}
