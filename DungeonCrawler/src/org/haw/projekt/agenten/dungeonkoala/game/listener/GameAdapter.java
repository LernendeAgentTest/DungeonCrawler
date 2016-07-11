package org.haw.projekt.agenten.dungeonkoala.game.listener;

import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Adapter für den {@link GameListener_I}, sodass man nur die Methoden überschreiben muss,
 * die einen interessieren.
 */
public class GameAdapter implements GameListener_I {
	@Override
	public void onGameStarted(Player player, int rooms) {}
	
	@Override
	public void onGameEnded(Player player, int rooms) {}
	
	@Override
	public void onRoomEntered(Room room) {}
	
	@Override
	public void onRoomCleared(Room room) {}
	
	@Override
	public void onRoundStarted() {}
	
	@Override
	public void onRoundEnded() {}
}
