package org.haw.projekt.agenten.dungeonkoala.learning.feature.features;

public class EnemyChargeLevelFeature extends AbstractDungeonKoalaFeature {
	public EnemyChargeLevelFeature() {
		super(
				"Enemy Charge",
				new EnemyChargeLevelFeatureState(0),
				new EnemyChargeLevelFeatureState(1),
				new EnemyChargeLevelFeatureState(2),
				new EnemyChargeLevelFeatureState(3)
		);
	}
	
	private static class EnemyChargeLevelFeatureState extends AbstractFeatureState {
		public EnemyChargeLevelFeatureState(int chargeLevel) {
			super(chargeLevel, String.valueOf(chargeLevel), (me, opponent, room) -> opponent.getEnergy()==chargeLevel);
		}
	}
}
