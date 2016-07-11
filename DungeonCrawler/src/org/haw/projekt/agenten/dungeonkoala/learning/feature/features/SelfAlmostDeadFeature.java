package org.haw.projekt.agenten.dungeonkoala.learning.feature.features;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

public class SelfAlmostDeadFeature extends AbstractDungeonKoalaFeature {
	public SelfAlmostDeadFeature() {
		super(
				"Self Almost Dead",
				new AbstractFeatureState(0, "Fine", (me, opponent, room) -> me.getHealth().getValue() > calculateDamage(me, opponent, room)),
				new AbstractFeatureState(1, "Almost Dead", (me, opponent, room) -> me.getHealth().getValue() <= calculateDamage(me, opponent, room))
		);
	}
	
	private static int calculateDamage(Entity me, Entity opponent, Room room) {
		double elementBonus = me.getElement().versusElement(opponent.getElement());
		return (int)((opponent.getAttackPower() + opponent.getEnergyBonus())*elementBonus);
	}
}
