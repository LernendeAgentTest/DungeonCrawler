package org.haw.projekt.agenten.dungeonkoala.learning.feature.features;

public class EnemyLifeFeature extends AbstractDungeonKoalaFeature{
	private static final int LIFE_SEGMENT = 5;
	
	protected EnemyLifeFeature() {
		super("Enemy Health (" + LIFE_SEGMENT + " segments)", createFeatureStates());
	}
	
	private static AbstractFeatureState[] createFeatureStates() {
		AbstractFeatureState[] featureStates = new AbstractFeatureState[LIFE_SEGMENT + 1];
		for(int i=LIFE_SEGMENT; i>=0; i--) {
			featureStates[i] = new EnemyLifeFeatureState(i, 1.0/LIFE_SEGMENT*i);
		}
		return featureStates;
	}
	
	private static class EnemyLifeFeatureState extends AbstractFeatureState {
		public EnemyLifeFeatureState(int number, double percentage) {
			super(number, "<= " + (int)(percentage*100) + "%", (me, opponent, room) -> opponent.getHealth().getPercentage() <= percentage);
		}
	}
}
