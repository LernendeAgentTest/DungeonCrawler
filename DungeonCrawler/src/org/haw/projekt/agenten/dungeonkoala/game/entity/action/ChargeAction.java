package org.haw.projekt.agenten.dungeonkoala.game.entity.action;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Erhöht das Energielevel der aktiven Entität um eigene Aktionen zu verstärken
 */
public class ChargeAction extends Action_A {

    public ChargeAction(Entity me, Room room) {
        super(me, room);
    }

    @Override
    public void execute() {
        _me.alterEnergy(1);
    }

    @Override
    public int getPriority() {
        //Das aufladen von Energie kann als letztes ausgeführt werden
        return Integer.MIN_VALUE;
    }

    @Override
    public String toString() {
        return "Charge";
    }
}
