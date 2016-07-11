package org.haw.projekt.agenten.dungeonkoala.game.entity.listener;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;

public class EntityAdapter implements EntityListener_I {
	@Override
	public void onHealthChanged(Entity entity, int oldValue, int newValue) {}
	
	@Override
	public void onDied(Entity entity) {}
	
	@Override
	public void onEnergyChanged(Entity entity, int oldValue, int newValue) {}
	
	@Override
	public void onBlockLevelChanged(Entity entity, int oldValue, int newValue) {}
	
	@Override
	public void onAttackPowerChanged(Entity entity, int oldValue, int newValue) {}
	
	@Override
	public void onBeforeActionExecuted(Entity entity, Action action) {}
	
	@Override
	public void onAfterActionExecuted(Entity entity, Action action) {}
}
