package org.haw.projekt.agenten.dungeonkoala.game.room.generator;

import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Schnittstelle für Raum-Generatoren.
 * <p>Jeder Generator sollte zählen, wieviele Räume er bereits generiert hat.
 */
public interface RoomGenerator_I {
	/**
	 * Generiert einen neuen Raum.
	 * <p>Jedes Mal, wenn diese Methode aufgerufen wird, wird ein Zähler über die
	 * bisher generierten Räume hochgezählt.
	 * @return Der generierte Raum
	 * @see #getGeneratedRoomsCount()
	 */
	Room generateRoom();
	
	/**
	 * Gibt die Anzahl der bisher von diesem Generator generierten Räume zurück.
	 * @return Die Anzahl der bisher generierten Räume
	 */
	int getGeneratedRoomsCount();
}
