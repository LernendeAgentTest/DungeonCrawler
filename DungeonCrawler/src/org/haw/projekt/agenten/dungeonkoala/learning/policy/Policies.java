package org.haw.projekt.agenten.dungeonkoala.learning.policy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.explorationpolicy.StaticExplorationPolicy;

/**
 * Eine Sammlung aller {@link Policy_I Policies}, die ein Agent zum Treffen seiner Entscheidungen benutzen könnte.
 */
public final class Policies {
	public static final StaticPolicy STATIC_POLICY = new StaticPolicy();
	public static final RandomPolicy RANDOM_POLICY = new RandomPolicy();
	public static final StaticExplorationPolicy STATIC_EXPLORATION_POLICY = new StaticExplorationPolicy();
	public static final DynamicExplorationPolicy DYNAMIC_EXPLORATION_POLICY = new DynamicExplorationPolicy();
	
	private static final List<Policy_I> ALL_POLICIES = new ArrayList<Policy_I>();
	
	static {
		ALL_POLICIES.add(STATIC_POLICY);
		ALL_POLICIES.add(RANDOM_POLICY);
		ALL_POLICIES.add(STATIC_EXPLORATION_POLICY);
		ALL_POLICIES.add(DYNAMIC_EXPLORATION_POLICY);
	}
	
	private Policies() {}
	
	/**
	 * Gibt eine unveränderliche Liste aller {@link Policy_I Policies} zurück.
	 * @return Eine unveränderliche Liste aller Policies
	 */
	public static List<Policy_I> getAllPolicies() {
		return Collections.unmodifiableList(ALL_POLICIES);
	}
}
