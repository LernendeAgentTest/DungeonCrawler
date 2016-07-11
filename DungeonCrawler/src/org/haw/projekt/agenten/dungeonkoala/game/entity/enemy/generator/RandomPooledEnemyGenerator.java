package org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.generator;

import java.util.LinkedList;
import java.util.List;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours.Behaviours;
import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;
import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.Enemy;

/**
 * Ein Generator, der Gegner mit Verhalten aus einem Pool generiert.
 * Dabei wird jedes Verhalten das benutzt wird aus dem Pool als "benutzt" markiert,
 * bis jedes Behavior aus dem Pool einmal dran war. Danach fängt der Pool wieder von vorne an.
 */
public class RandomPooledEnemyGenerator implements EnemyGenerator_I {
	private List<EntityBehaviour_I> _unused;
	private List<EntityBehaviour_I> _used;
	
	public RandomPooledEnemyGenerator() {
		_unused = new LinkedList<>(Behaviours.getBehaviours());
		_used = new LinkedList<>();
	}
	
	@Override
	public Enemy generateEnemy() {
		// Pools swappen, wenn der Unused-Pool leer ist.
		// Dadurch wird der Unused-Pool wieder gefüllt und der Used-Pool wieder geleert.
		if(_unused.isEmpty()) {
			List<EntityBehaviour_I> temp = _unused;
			_unused = _used;
			_used = temp;
		}
		
		// Behaviour aus dem Unused-Pool ziehen und dem Used-Pool hinzufügen,
		// um es aus der Auswahl für den nächsten Aufruf auszuschließen
		EntityBehaviour_I behaviour = _unused.remove((int)(Math.random()*_unused.size()));
		_used.add(behaviour);
		
		return new Enemy(behaviour, 100, 10, Element.getRandomElement());
	}
}
