package org.haw.projekt.agenten.dungeonkoala.util.listener;

@FunctionalInterface
public interface Event<L> {
	
	void fireEvent(L l);
}
