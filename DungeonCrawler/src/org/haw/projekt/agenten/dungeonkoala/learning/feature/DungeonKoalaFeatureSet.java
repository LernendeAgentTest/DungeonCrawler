package org.haw.projekt.agenten.dungeonkoala.learning.feature;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.features.AbstractDungeonKoalaFeature;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

public class DungeonKoalaFeatureSet implements FeatureSet_I {
	/**
	 * The number of reserved states for special cases.
	 */
	private static final int NUMBER_OF_STATIC_STATES = 2;
	
	private EventDispatcher<FeatureSetListener_I> _eventDispatcher;
	
	private List<AbstractDungeonKoalaFeature> _features;
	
	public DungeonKoalaFeatureSet(Collection<? extends AbstractDungeonKoalaFeature> features) {
		_eventDispatcher = new EventDispatcher<FeatureSetListener_I>();
		_features = new LinkedList<AbstractDungeonKoalaFeature>(Objects.requireNonNull(features));
	}
	
	public void updateFeatures(Entity me, Entity opponent, Room room) {
		for(AbstractDungeonKoalaFeature feature : _features) {
			int oldState = feature.getCurrentState();
			feature.updateState(me, opponent, room);
			int newState = feature.getCurrentState();
			_eventDispatcher.fireEvent(l -> l.onFeatureStateChanged(feature, oldState, newState));
		}
	}
	
	@Override
	public void addFeatureSetListener(FeatureSetListener_I listener) {
		_eventDispatcher.addListener(listener);
	}
	
	@Override
	public void removeFeatureSetListener(FeatureSetListener_I listener) {
		_eventDispatcher.removeListener(listener);
	}
	
	@Override
	public int getCurrentState() {
		int state = NUMBER_OF_STATIC_STATES;
		int stateAllocationScope = 1;
		// Hier ist die Reihenfolge wichtig, damit es konsistent mit der Sortierung in #getDescription(int) bleibt
		for(int i=_features.size()-1; i>=0; i--) {
			AbstractDungeonKoalaFeature feature = _features.get(i);
			state += feature.getCurrentState() * stateAllocationScope;
			stateAllocationScope *= feature.getPossibleAllocations();
		}
		return state;
	}
	
	@Override
	public int getFeatureStateRange() {
		int possibleStates = 1;
		// Hier ist die Reihenfolge wichtig, damit es konsistent mit der Sortierung in #getDescription(int) bleibt
		for(int i=_features.size()-1; i>=0; i--) {
			AbstractDungeonKoalaFeature feature = _features.get(i);
			possibleStates *= feature.getPossibleAllocations();
		}
		return possibleStates + NUMBER_OF_STATIC_STATES;
	}
	
	@Override
	public String getDescription(int state) {
		String representation = "";
		if(state < NUMBER_OF_STATIC_STATES) {
			representation = state == 0 ? "PD" : "ED";
		} else {
			int stateWithoutStatics = state - NUMBER_OF_STATIC_STATES;
			int stateAllocationScope = 1;
			// Die umgedrehte Reihenfolge sorgt dafür, dass die Repräsentation der Features wie bei aussagenlogischen
			// Wahr-/Falsch-Tabellen aufgelistet wird (000, 001, 002, 010, 011, ...)
			for(int i=_features.size()-1; i>=0; i--) {
				AbstractDungeonKoalaFeature feature = _features.get(i);
				
				if(i != _features.size()) {
					representation = " " + representation;
				}
				representation = String.valueOf(stateWithoutStatics / stateAllocationScope % feature.getPossibleAllocations()) + representation;
				
				stateAllocationScope *= feature.getPossibleAllocations();
			}
		}
		return representation;
	}
	
	@Override
	public List<Feature_I> getFeatures() {
		return Collections.unmodifiableList(_features);
	}
}
