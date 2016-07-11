package org.haw.projekt.agenten.dungeonkoala.learning.feature.features;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Eine Sammlung aller {@link AbstractDungeonKoalaFeature Features} für einen
 * QLearningAgent.
 */
public final class DungeonKoalaFeatures {
	public static final SelfAlmostDeadFeature   SELF_ALMOST_DEAD_FEATURE = new SelfAlmostDeadFeature();
	public static final SelfElementFeature      SELF_ELEMENT_FEATURE = new SelfElementFeature();
	public static final SelfChargeLevelFeature  SELF_CHARGE_LEVEL_FEATURE = new SelfChargeLevelFeature();
	public static final SelfLifeFeature 		SELF_LIFE_FEATURE = new SelfLifeFeature();
	
	public static final EnemyAlmostDeadFeature  ENEMY_ALMOST_DEAD_FEATURE = new EnemyAlmostDeadFeature();
	public static final EnemyElementFeature     ENEMY_ELEMENT_FEATURE = new EnemyElementFeature();
	public static final EnemyChargeLevelFeature ENEMY_CHARGE_LEVEL_FEATURE = new EnemyChargeLevelFeature();
	public static final EnemyBehaviourFeature   ENEMY_BEHAVIOURS_FEATURE = new EnemyBehaviourFeature();
	public static final EnemyLifeFeature 		ENEMY_LIFE_FEATURE = new EnemyLifeFeature();

	private static final List<AbstractDungeonKoalaFeature> ALL_FEATURES;
	
	static {
		ALL_FEATURES = Arrays.asList(
				SELF_ALMOST_DEAD_FEATURE,
				SELF_LIFE_FEATURE,
				SELF_ELEMENT_FEATURE,
				SELF_CHARGE_LEVEL_FEATURE,
				ENEMY_ALMOST_DEAD_FEATURE,
				ENEMY_LIFE_FEATURE,
				ENEMY_ELEMENT_FEATURE,
				ENEMY_CHARGE_LEVEL_FEATURE,
				ENEMY_BEHAVIOURS_FEATURE
		);
	}
	
	private DungeonKoalaFeatures() {}
	
	/**
	 * Gibt eine unveränderliche Liste aller Features zurück.
	 * 
	 * @return Eine unveränderliche Liste aller Features
	 */
	public static List<AbstractDungeonKoalaFeature> getAllFeatures() {
		return Collections.unmodifiableList(ALL_FEATURES);
	}
}
