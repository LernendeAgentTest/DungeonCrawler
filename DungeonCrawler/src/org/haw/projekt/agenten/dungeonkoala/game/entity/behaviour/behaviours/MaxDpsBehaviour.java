package org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.AttackAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.ChargeAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Ein Verhalten, das die Entität dazu veranlasst maximalen Schaden zu machen
 */
public class MaxDpsBehaviour implements EntityBehaviour_I {
    @Override
    public Action makeNextMove(Entity me, Entity opponent, List<Action> actions, Room room) {
        if(opponent.getHealth().getValue() <= me.getEnergyBonus()) {
            return new AttackAction(me, opponent, room);
        } else if(me.getEnergy() < 3) {
            return new ChargeAction(me, room);
        } else {
            return new AttackAction(me, opponent, room);
        }
    }
    
    @Override
    public String getDisplayName() {
        return "Max Dps Behaviour";
    }
}
