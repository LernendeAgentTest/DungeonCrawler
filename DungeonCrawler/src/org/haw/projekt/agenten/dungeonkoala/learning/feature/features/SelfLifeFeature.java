package org.haw.projekt.agenten.dungeonkoala.learning.feature.features;

public class SelfLifeFeature extends AbstractDungeonKoalaFeature {
	private static final int LIFE_SEGMENT = 5;
	
	protected SelfLifeFeature() {
		super("Self Health (" + LIFE_SEGMENT + " segments)", createFeatureStates());
	}
	
	private static AbstractFeatureState[] createFeatureStates() {
		AbstractFeatureState[] featureStates = new AbstractFeatureState[LIFE_SEGMENT + 1];
		for(int i=LIFE_SEGMENT; i>=0; i--) {
			featureStates[i] = new SelfLifeFeatureState(i, 1.0/LIFE_SEGMENT*i);
		}
		return featureStates;
	}
	
	private static class SelfLifeFeatureState extends AbstractFeatureState {
		public SelfLifeFeatureState(int number, double percentage) {
			super(number, "<= " + (int)(percentage*100) + "%", (me, opponent, room) -> me.getHealth().getPercentage() <= percentage);
		}
	}
}
