package org.haw.projekt.agenten.dungeonkoala.learning.feature.features;

import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;

public class SelfElementFeature extends AbstractDungeonKoalaFeature {
	public SelfElementFeature() {
		super("Self Element", createFeatureStates());
	}
	
	private static AbstractFeatureState[] createFeatureStates() {
		Element[] elements = Element.values();
		AbstractFeatureState[] featureStates = new AbstractFeatureState[elements.length];
		for(int i=0; i<elements.length; i++) {
			featureStates[i] = new SelfElementFeatureState(elements[i]);
		}
		return featureStates;
	}
	
	private static class SelfElementFeatureState extends AbstractFeatureState {
		public SelfElementFeatureState(Element element) {
			super(element.ordinal(), element.name(), (me, opponent, room) -> me.getElement()==element);
		}
	}
}
