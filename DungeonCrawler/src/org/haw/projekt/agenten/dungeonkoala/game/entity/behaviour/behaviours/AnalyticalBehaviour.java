package org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.AttackAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.BlockAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.ChargeAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.HealAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.SwitchToFireAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.SwitchToGrassAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.SwitchToWaterAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;
import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.Enemy;
import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Ein Verhalten, das auf Basis von Analyse die beste Aktion wählt.
 * 
 * <p>
 * Da man bisher nur angreifen und sich heilen kann, ist der Analyseprozess
 * ziemlich simpel. Die Überlegung ist, wenn der nächste Schlag vom Gegner mich
 * umbringen würde und ich mich mehr heilen kann, als der Gegner mir zufügt,
 * dann heile ich mich. Ansonsten greife ich an.
 */
public class AnalyticalBehaviour implements EntityBehaviour_I {
	@Override
	public Action makeNextMove(Entity me, Entity oppenent, List<Action> actions, Room room) {
		
		double factor = (me.getElement().versusElement(oppenent.getElement()));
		int me_attackDamage = (int) ((me.getAttackPower() + me.getEnergyBonus()) * factor);
		int healValue = (int) ((me.getAttackPower() + me.getEnergyBonus()));
		
		factor = (oppenent.getElement().versusElement(me.getElement()));
		int oppenent_attackDamage = (int) ((oppenent.getAttackPower() + oppenent.getEnergyBonus()) * factor);

		
		if (me_attackDamage >= oppenent.getHealth().getValue()) {
			return new AttackAction(me, oppenent, room);
		
		} else if ((me.getElement().versusElement(oppenent.getElement()) < Element.EFFECTIVE) && (me.getClass() == Player.class)) {
			if(oppenent.getElement() == Element.FIRE) {
				return new SwitchToWaterAction(me, room);
			} else if(oppenent.getElement() == Element.WATER) {
				return new SwitchToGrassAction(me, room);
			} else {
				return new SwitchToFireAction(me, room);
			}
			
		} else if ((me.getElement().versusElement(oppenent.getElement()) < Element.EFFECTIVE) && (me.getClass()== Enemy.class) && Math.random() > 0.5) {
			if(oppenent.getElement() == Element.FIRE) {
				return new SwitchToWaterAction(me, room);
			} else if(oppenent.getElement() == Element.WATER) {
				return new SwitchToGrassAction(me, room);
			} else {
				return new SwitchToFireAction(me, room);
			}
			
		} else if(oppenent_attackDamage >= me.getHealth().getMaximum()) {
			return new BlockAction(me, room);
			
		} else if(oppenent_attackDamage >= (me.getHealth().getValue()+ healValue)) {
			return new BlockAction(me, room);
			
		} else if (me.getHealth().getValue() < (me.getHealth().getMaximum()/2)) {
			return new HealAction(me, room);
			
		} else if (me.getEnergy() < 3) {
			return new ChargeAction(me, room);
				
		} else {
			return new AttackAction(me, oppenent, room);
		}
	}
	
	@Override
	public String getDisplayName() {
		return "analytisch";
	}
}
