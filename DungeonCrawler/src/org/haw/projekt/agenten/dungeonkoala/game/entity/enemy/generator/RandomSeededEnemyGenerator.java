package org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.generator;

import java.util.Random;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours.Behaviours;
import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;
import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.Enemy;

public class RandomSeededEnemyGenerator implements EnemyGenerator_I {
	private Random _random;
	
	public RandomSeededEnemyGenerator(long seed) {
		_random = new Random(seed);
	}
	
	@Override
	public Enemy generateEnemy() {
		int selectedBehaviour = _random.nextInt(Behaviours.getBehaviours().size());
		EntityBehaviour_I behaviour = Behaviours.getBehaviours().get(selectedBehaviour);
		
		return new Enemy(behaviour, 100, 10, Element.getRandomElement());
	}
}
