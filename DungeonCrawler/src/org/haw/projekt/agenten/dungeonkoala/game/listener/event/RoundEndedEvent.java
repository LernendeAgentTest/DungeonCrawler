package org.haw.projekt.agenten.dungeonkoala.game.listener.event;

import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;

/**
 * Ein Event das über den Abschluss einer Runde informiert.
 */
public class RoundEndedEvent extends GameEvent_A {
	@Override
	public void fireEvent(GameListener_I l) {
		l.onRoundEnded();
	}
}
