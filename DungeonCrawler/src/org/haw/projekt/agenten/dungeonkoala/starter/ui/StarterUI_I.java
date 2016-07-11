package org.haw.projekt.agenten.dungeonkoala.starter.ui;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;

public interface StarterUI_I {
	
	void show();
	
	
	void hide();
	
	
	void setListener(StarterUIListener_I l);
	
	
	StarterUIListener_I getListener();
	
	
	void setPlayerInterfaces(List<EntityBehaviour_I> playerInterfaces);
}
