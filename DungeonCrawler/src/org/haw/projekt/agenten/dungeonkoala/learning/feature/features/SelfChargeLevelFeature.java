package org.haw.projekt.agenten.dungeonkoala.learning.feature.features;

public class SelfChargeLevelFeature extends AbstractDungeonKoalaFeature {
	public SelfChargeLevelFeature() {
		super(
				"Self Charge",
				new SelfChargeLevelFeatureState(0),
				new SelfChargeLevelFeatureState(1),
				new SelfChargeLevelFeatureState(2),
				new SelfChargeLevelFeatureState(3)
		);
	}
	
	private static class SelfChargeLevelFeatureState extends AbstractFeatureState {
		public SelfChargeLevelFeatureState(int chargeLevel) {
			super(chargeLevel, String.valueOf(chargeLevel), (me, opponent, room) -> me.getEnergy()==chargeLevel);
		}
	}
}
