package org.haw.projekt.agenten.dungeonkoala.game.entity.action;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;

public interface Action {
	/**
	 * Die Aktion wird ausgeführt.
	 */
	void execute();
	
	/**
	 * Gibt die Entität zurück, die die Action ausführt.
	 * @return Die Entität, die die Action ausführt
	 */
	Entity getPerformer();
	
	/**
	 * Gibt die Priorität der Aktion zurück.
	 * @return die Priorität. Niedrigste Priorität {@code Integer.MIN_VALUE}. Höchste Priorität {@code Integer.MAX_VALUE}.
	 */
	int getPriority();
}
