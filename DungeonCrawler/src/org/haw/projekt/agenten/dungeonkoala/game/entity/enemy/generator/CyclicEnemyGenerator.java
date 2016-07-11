package org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.generator;

import java.util.LinkedList;
import java.util.Queue;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours.Behaviours;
import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;
import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.Enemy;

/**
 * Ein Enemy-Generator der Gegner mit Verhalten in einer festen Reihenfolge generiert, die immer wiederkehrt.
 * Die Verhalten werden aus {@link Behaviours} bezogen und hängen damit von der Reihenfolge dort ab.
 */
public class CyclicEnemyGenerator implements EnemyGenerator_I {
	private Queue<EntityBehaviour_I> _behaviourQueue;
	
	/**
	 * Erzeugt einen neuen CyclicEnemyGenerator. Jeder neue Generator fängt von Vorne an.
	 */
	public CyclicEnemyGenerator() {
		_behaviourQueue = new LinkedList<EntityBehaviour_I>(Behaviours.getBehaviours());
	}
	
	/**
	 * Gibt eine Singleton-Instanz von einem CyclicEnemyGenerator zurück.
	 * Die Instanz kann genutzt werden, wenn das zyklische Generieren z.B. über mehrere Spiele hinweg eingesetzt werden
	 * soll und nicht bei jedem Neustart mit einem neuen Generator wieder von Vorne anfangen anfangen soll.
	 * @return Die Instanz
	 */
	public static CyclicEnemyGenerator getSingletonInstance() {
		return Holder.INSTANCE;
	}
	
	@Override
	public Enemy generateEnemy() {
		// Das nächste Behaviour aus der Queue ziehen und es hinten wieder
		// raufpacken damit sich die Behaviours zyklisch wiederholen 
		EntityBehaviour_I behaviour = _behaviourQueue.poll();
		_behaviourQueue.offer(behaviour);
		
		return new Enemy(behaviour, 100, 10, Element.getRandomElement());
	}
	
	private static class Holder {
		private static final CyclicEnemyGenerator INSTANCE = new CyclicEnemyGenerator();
	}
}
