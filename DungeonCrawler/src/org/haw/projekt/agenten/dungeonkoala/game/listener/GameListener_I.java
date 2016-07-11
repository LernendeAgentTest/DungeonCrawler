package org.haw.projekt.agenten.dungeonkoala.game.listener;

import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Schnittstelle für die Ausgabe auf der Benutzeroberfläche.
 */
public interface GameListener_I {
	/**
	 * Wird vom Spiel aufgerufen, wenn das Spiel gestartet wird.
	 * @param player Der Spieler
	 * @param rooms Die Anzahl der zu bestehenden Räume
	 */
	void onGameStarted(Player player, int rooms);
	
	/**
	 * Wird vom Spiel aufgerufen, wenn das Spiel zuende ist.
	 * @param player Der Spieler, repräsentiert den Stand zum Ende des Spiels
	 * @param rooms Wieviele Räume der Spieler geschafft hat
	 */
	void onGameEnded(Player player, int rooms);
	
	/**
	 * Wird vom Spiel immer dann aufgerufen, wenn ein neuer Raum betreten wird.
	 * @param room Der betretene Raum
	 */
	void onRoomEntered(Room room);
	
	/**
	 * Wird vom Spiel immer dann aufgerufen, wenn ein Raum abgeschlossen wurde.
	 * @param room Der abgeschlossene Raum
	 */
	void onRoomCleared(Room room);
	
	/**
	 * Wird vom Spiel immer dann aufgerufen, wenn eine Runde beginnt.
	 * Das heißt noch bevor die Spieler ihre Aktionen wählen.
	 */
	void onRoundStarted();
	
	/**
	 * Wird vom Spiel immer dann aufgerufen, wenn eine Runde abgeschlossen ist.
	 * Das heißt nachdem die Aktionen aller Spieler ausgeführt worden sind.
	 */
	void onRoundEnded();
}
