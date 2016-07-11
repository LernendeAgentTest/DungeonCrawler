package org.haw.projekt.agenten.dungeonkoala.game.ui;

import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

public interface Renderer_I {
	
	int getAnimationFPS();
	
	
	void setAnimationFPS(int fps);
	
	
	int getAnimationDuration();
	
	
	void setAnimationDuration(int durationMS);
	
	
	void setPlayer(Player player);
	
	
	void setRoom(Room room);
	
	
	GameListener_I getGameListener();
}
