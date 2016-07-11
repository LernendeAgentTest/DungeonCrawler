package org.haw.projekt.agenten.dungeonkoala.learning.feature.features;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

public class EnemyAlmostDeadFeature extends AbstractDungeonKoalaFeature {
	
	public EnemyAlmostDeadFeature() {
		super(
				"Enemy Almost Dead",
				new AbstractFeatureState(0, "Fine", (me, opponent, room) -> opponent.getHealth().getValue() > calculateDamage(me, opponent, room)),
				new AbstractFeatureState(1, "Almost Dead", (me, opponent, room) -> opponent.getHealth().getValue() <= calculateDamage(me, opponent, room))
		);
	}
	
	private static int calculateDamage(Entity me, Entity opponent, Room room) {
		double elementBonus = opponent.getElement().versusElement(me.getElement());
		return (int)((me.getAttackPower() + me.getEnergyBonus())*elementBonus);
	}
}
