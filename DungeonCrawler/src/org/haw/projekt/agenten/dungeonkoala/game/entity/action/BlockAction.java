package org.haw.projekt.agenten.dungeonkoala.game.entity.action;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Die aktive Entität blockt in dieser Runde einen Angriff
 */
public class BlockAction extends Action_A {

    public BlockAction(Entity me, Room room) {
        super(me, room);
    }

    @Override
    public void execute() {
        _me.setBlockLevel(_me.getEnergy());
        _me.resetEnergy();
    }

    @Override
    public int getPriority() {
        //Die Block Aktion muss als erstes ausgeführt werden
        return Integer.MAX_VALUE;
    }

    @Override
    public String toString() {
        return "Block";
    }
}
