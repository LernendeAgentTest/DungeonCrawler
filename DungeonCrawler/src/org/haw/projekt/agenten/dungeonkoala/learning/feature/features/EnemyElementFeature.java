package org.haw.projekt.agenten.dungeonkoala.learning.feature.features;

import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;

public class EnemyElementFeature extends AbstractDungeonKoalaFeature {
	public EnemyElementFeature() {
		super("Enemy Element", createFeatureStates());
	}
	
	private static AbstractFeatureState[] createFeatureStates() {
		Element[] elements = Element.values();
		AbstractFeatureState[] featureStates = new AbstractFeatureState[elements.length];
		for(int i=0; i<elements.length; i++) {
			featureStates[i] = new EnemyElementFeatureState(elements[i]);
		}
		return featureStates;
	}
	
	private static class EnemyElementFeatureState extends AbstractFeatureState {
		public EnemyElementFeatureState(Element element) {
			super(element.ordinal(), element.name(), (me, opponent, room) -> opponent.getElement()==element);
		}
	}
}
