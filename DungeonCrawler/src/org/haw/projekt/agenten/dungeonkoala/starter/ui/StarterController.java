package org.haw.projekt.agenten.dungeonkoala.starter.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.game.GameSession;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Actions;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours.Behaviours;
import org.haw.projekt.agenten.dungeonkoala.game.ui.GameUIController;
import org.haw.projekt.agenten.dungeonkoala.game.ui.GameUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.QLearningBehaviour;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.features.DungeonKoalaFeatures;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.StaticPolicy;

public class StarterController implements StarterUIListener_I {
	private StarterUI_I _ui;
	
	public StarterController(StarterUI_I ui) {
		_ui = Objects.requireNonNull(ui);
		_ui.setListener(this);
		_ui.setPlayerInterfaces(getSupportedPlayerInterfaces());
	}
	
	public void start() {
		_ui.show();
	}
	
	@Override
	public void onStart(GameUI_I ui, EntityBehaviour_I playerInterface) {
		_ui.hide();
		
		GameSession game = new GameSession();
		GameUIController controller = new GameUIController(game, ui, playerInterface);
		if(playerInterface instanceof QLearningBehaviour) {
			game.addGameListener((QLearningBehaviour)playerInterface);
		}
		controller.start();
	}
	
	private static List<EntityBehaviour_I> getSupportedPlayerInterfaces() {
		List<EntityBehaviour_I> playerInterfaces = new ArrayList<EntityBehaviour_I>();
		playerInterfaces.addAll(Behaviours.getBehaviours());
		playerInterfaces.add(new QLearningBehaviour(new StaticPolicy(), DungeonKoalaFeatures.getAllFeatures(), Actions.getAllActions()));
		return playerInterfaces;
	}
}
