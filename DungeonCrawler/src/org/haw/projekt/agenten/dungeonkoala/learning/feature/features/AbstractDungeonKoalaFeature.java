package org.haw.projekt.agenten.dungeonkoala.learning.feature.features;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.Feature_I;

/**
 * Abstrakte Oberklasse für {@link Feature_I Features} in der DungeonKoala-Domäne.
 */
public abstract class AbstractDungeonKoalaFeature implements Feature_I {
	private String _name;
	private AbstractFeatureState _currentState;
	private List<AbstractFeatureState> _featureStates;
	
	protected AbstractDungeonKoalaFeature(String name, AbstractFeatureState... possibleStates) {
		_name = Objects.requireNonNull(name);
		_featureStates = Arrays.asList(possibleStates);
		_currentState = _featureStates.get(0);
	}
	
	@Override
	public int getCurrentState() {
		return getCurrentFeatureState().getStateNumber();
	}
	
	@Override
	public int getPossibleAllocations() {
		return _featureStates.size();
	}
	
	@Override
	public FeatureState_I getCurrentFeatureState() {
		return _currentState;
	}
	
	@Override
	public List<? extends FeatureState_I> getFeatureStates() {
		return _featureStates;
	}
	
	@Override
	public String getName() {
		return _name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	/**
	 * Aktualisiert den Zustand dieses Features. Dabei wird geprüft, welches {@link AbstractFeatureState}
	 * für die gegebene Spielsituation gerade zutrifft.
	 * @param me Die Entität, die aus der Sicht des Features sich selbst repräsentiert
	 * @param opponent Die Entität, die aus der Sicht des Features den Gegner repräsentiert
	 * @param room Der Raum in dem sich die Teilnehmer befinden
	 */
	public void updateState(Entity me, Entity opponent, Room room) {
		for(AbstractFeatureState state : _featureStates) {
			if(state.appliesTo(me, opponent, room)) {
				_currentState = state;
				break;
			}
		}
	}
	
	protected static class AbstractFeatureState implements FeatureState_I {
		private int _number;
		private String _description;
		private StateChecker_I _checker;
		
		public AbstractFeatureState(int number, String description, StateChecker_I checker) {
			_number = number;
			_description = description;
			_checker = Objects.requireNonNull(checker);
		}
		
		@Override
		public int getStateNumber() {
			return _number;
		}
		
		@Override
		public String getDescription() {
			return _description;
		}
		
		protected boolean appliesTo(Entity me, Entity opponent, Room room) {
			return _checker.appliesTo(me, opponent, room);
		}
	}
	
	@FunctionalInterface
	protected static interface StateChecker_I {
		/**
		 * Prüft ob dieser Zustand für die gegebenen Parameter zutrifft.
		 * @param me Die Entität, die aus der Sicht des Features sich selbst repräsentiert
		 * @param opponent Die Entität, die aus der Sicht des Features den Gegner repräsentiert
		 * @param room Der Raum in dem sich die Teilnehmer befinden
		 * @return {@code true} wenn der Zustand zutrifft, ansonsten {@code false}
		 */
		boolean appliesTo(Entity me, Entity opponent, Room room);
	}
}
