package org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.*;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Ein Verhalten, das die Entität dazu veranlasst möglichst lange zu überleben
 * FIRST DEFENSE THEN OFFENSE
 */
public class FirstDefenseBehaviour implements EntityBehaviour_I {
    @Override
    public Action makeNextMove(Entity me, Entity opponent, List<Action> actions, Room room) {
        if(opponent.getEnergy() == 3) {
            return new BlockAction(me, room);
        } else if((me.getHealth().getMaximum()-me.getHealth().getValue())>= me.getEnergyBonus()) {
            return new HealAction(me, room);
        } else if(me.getEnergy() < 2) {
            return new ChargeAction(me, room);
        } else {
            return new AttackAction(me,opponent, room);
        }
    }
    
    @Override
    public String getDisplayName() {
        return "First Defense Behaviour";
    }
}
