package org.haw.projekt.agenten.dungeonkoala.game.entity.action;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

public class SwitchToFireAction extends Action_A {

	
	public SwitchToFireAction(Entity me, Room room) {
		super(me, room);
	}

	@Override
	public void execute() {
		_me.setElement(Element.FIRE);
	}

	@Override
	public int getPriority() {
		return 0; 
	}

	@Override
	public String toString() {
		return "Fire";
	}
}
