package org.haw.projekt.agenten.dungeonkoala.learning.feature.features;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.behaviours.Behaviours;

/**
 * Ein Agenten-Feature, das die unterschiedlichen {@link EntityBehaviour_I Gegnerverhalten} beschreibt.
 * Für den Fall, dass der Gegner ein Verhalten an den Tag legt, das nicht bekannt ist (siehe {@link Behaviours}),
 * ist der Statusraum um eins erweitert. Somit werden alle unbekannten Verhalten in einem Status zusammengefasst.
 */
public class EnemyBehaviourFeature extends AbstractDungeonKoalaFeature {
	public EnemyBehaviourFeature() {
		super("Enemy Behaviours", createFeatureStates());
	}
	
	private static AbstractFeatureState[] createFeatureStates() {
		List<EntityBehaviour_I> builtInBehaviours = Behaviours.getBehaviours();
		AbstractFeatureState[] featureStates = new AbstractFeatureState[builtInBehaviours.size() + 1];
		for(int i=0; i<builtInBehaviours.size(); i++) {
			featureStates[i] = new EnemyBehaviourFeatureState(builtInBehaviours.get(i));
		}
		featureStates[builtInBehaviours.size()] = new EnemyBehaviourFeatureState();
		return featureStates;
	}
	
	private static class EnemyBehaviourFeatureState extends AbstractFeatureState {
		public EnemyBehaviourFeatureState(EntityBehaviour_I behaviour) {
			super(
					Behaviours.getBehaviours().indexOf(behaviour),
					behaviour.getDisplayName(),
					(me, opponent, room) -> opponent.getBehaviour().equals(behaviour)
			);
		}
		
		public EnemyBehaviourFeatureState() {
			super(Behaviours.getBehaviours().size(), "<unknown>", (me, opponent, room) -> true);
		}
	}
}
