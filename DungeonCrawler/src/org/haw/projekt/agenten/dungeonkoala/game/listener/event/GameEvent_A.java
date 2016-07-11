package org.haw.projekt.agenten.dungeonkoala.game.listener.event;

import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.Event;

/**
 * Abstraktes Game-Event. Wird im Moment nur dafür gebraucht, dass {@link GameListener_I}-Events
 * statt von {@code Event_A<GameListener_I>} zu erben nur von {@code GameEvent_A} erben müssen.
 */
public abstract class GameEvent_A implements Event<GameListener_I> {
	
}
