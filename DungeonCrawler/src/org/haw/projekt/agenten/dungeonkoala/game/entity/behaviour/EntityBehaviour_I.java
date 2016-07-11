package org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Schnittstelle für das Entitäts-Verhalten. Ein Verhalten wird der {@link Entity Entität} per
 * Konstruktor übergeben und wird vom Spiel genutzt, um eine Aktion für die Entität zu ermitteln.
 * 
 * <p>Es muss für die Zukunft berücksichtigt werden, dass wenn der Spieler auch Aktionen ausführen
 * können soll, die sich nicht im Kampf abspielen (z.B. soetwas wie einen nächsten Raum wählen),
 * dann bräuchten wir ein gesondertes PlayerBehaviour_I, das u.U. von EntityBehaviour_I erbt und
 * dann diese Funktionen zur Verfügung stellt.
 */
public interface EntityBehaviour_I {
	/**
	 * Wird vom Spiel immer dann aufgerufen, wenn es eine Aktion von einer {@link Entity Entität}
	 * benötigt.
	 * @param me Die Entität, die eine Aktion ausführt, darf nicht {@code null} sein
	 * @param opponent Eine eventuell an der Aktion beteiligte Entität, sollte auch nicht {@code null} sein
	 * @param actions Eine Liste der Aktionen, die dieses Behaviour in dem Zug ausführen kann
	 * @return Die von der Entität gewählte Aktion, darf nicht {@code null} sein
	 */
	Action makeNextMove(Entity me, Entity opponent, List<Action> actions, Room room);
	
	/**
	 * Gibt den Anzeigenamen des Verhaltens zurück.
	 * @return Der Anzeigename
	 */
	String getDisplayName();
}