package org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.generator;

import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.Enemy;

/**
 * Schnittstelle für Gegner-Generatoren.
 * 
 */
public interface EnemyGenerator_I {
	/**
	 * Generiert einen neuen Gegner.
	 * @return Der generierte Gegner
	 */
	Enemy generateEnemy();
}
