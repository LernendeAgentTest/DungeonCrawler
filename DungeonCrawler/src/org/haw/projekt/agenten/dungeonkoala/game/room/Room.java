package org.haw.projekt.agenten.dungeonkoala.game.room;

import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.Enemy;

/**
 * Repräsentiert einen Raum im Rahmen des Spiels.
 */
public class Room {

	private static final String ROOM_IMAGE_1 = "res/img/background/default_landscape.png";
	private static int _number;
	private static Enemy _enemy;

	/**
	 * Initialisiert den Raum mit einem Gegner.
	 * 
	 * @param number Die Nummer des Raums
	 * @param enemy Der Gegner für diesen Raum, darf nicht {@code null} sein
	 */
	public Room(int number, Enemy enemy) {
		_number = number;
		_enemy = Objects.requireNonNull(enemy);
	}

	/**
	 * Gibt die Nummer des Raums zurück.
	 * 
	 * @return Die Raumnummer
	 */
	public int getNumber() {
		return _number;
	}

	/**
	 * Gibt den Gegner dieses Raums zurück.
	 * 
	 * @return Der Gegner in diesem Raum
	 */
	public Enemy getEnemy() {
		return _enemy;
	}

	/**
	 * Prüft, ob der Raum abgeschlossen ist. Ein Raum ist erst erledigt, wenn
	 * alle Gegner im Raum tot (nicht am Leben) sind.
	 * 
	 * @return {@code true} wenn der Raum abgeschlossen ist, ansonsten
	 *         {@code false}
	 * @see Enemy#isAlive()
	 */
	public boolean isCleared() {
		return !_enemy.isAlive();
	}

	public String getRoomImage() {
		return ROOM_IMAGE_1;
	}
}
