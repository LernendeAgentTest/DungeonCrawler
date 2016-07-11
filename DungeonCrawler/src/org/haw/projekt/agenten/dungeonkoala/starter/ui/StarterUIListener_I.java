package org.haw.projekt.agenten.dungeonkoala.starter.ui;

import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.ui.GameUI_I;

public interface StarterUIListener_I {
	void onStart(GameUI_I ui, EntityBehaviour_I playerInterface);
}
