package org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.generator;

import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours.Behaviours;
import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.Enemy;
import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;

public class SimpleEnemyGenerator implements EnemyGenerator_I {
	@Override
	public Enemy generateEnemy() {

		Enemy enemy = new Enemy(Behaviours.randomBehaviour(), 100, 10, Element.getRandomElement());
		return enemy;
	}
}
