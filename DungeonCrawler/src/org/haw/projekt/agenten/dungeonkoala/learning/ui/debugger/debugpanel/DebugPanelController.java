package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.debugpanel;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.haw.projekt.agenten.dungeonkoala.learning.QLearningAgent;
import org.haw.projekt.agenten.dungeonkoala.learning.QLearningAgentListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;

public class DebugPanelController implements DebugPanelUIListener_I, QLearningAgentListener_I {
	private DebugPanelUI _ui;
	private QLearningAgent _agent;
	
	// Condition-Variablen für das Signalisieren
	private Lock _lock;
	private Condition _qValueCondition;
	private Condition _actionCondition;
	
	public DebugPanelController(DebugPanelUI ui) {
		_ui = Objects.requireNonNull(ui);
		_ui.addDebugPanelListener(this);
		
		_lock = new ReentrantLock();
		_qValueCondition = _lock.newCondition();
		_actionCondition = _lock.newCondition();
	}
	
	/**
	 * Setzt den Agenten auf dem debuggt werden soll.
	 * @param agent Der Agent
	 */
	public void setAgent(QLearningAgent agent) {
		if(_agent != null) {
			_agent.removeQLearningAgentListener(this);
		}
		_agent = agent;
		if(_agent != null) {
			_agent.addQLearningAgentListener(this);
		}
	}
	
	@Override
	public void onResumePressed() {
		try {
			_lock.lock();
			_qValueCondition.signal();
			_actionCondition.signal();
		} finally {
			_lock.unlock();
		}
	}
	
	@Override
	public void onQValueChanged(int state, int action, double value) {
		if(_ui.isPauseOnQValueChange()) {
			try {
				_lock.lock();
				_ui.setResumeButtonEnabled(true);
				_qValueCondition.await();
			} catch(InterruptedException e) {
				// Do nothing here
			} finally {
				_ui.setResumeButtonEnabled(false);
				_lock.unlock();
			}
		}
	}
	
	@Override
	public void onActionChoosen(int state, int choosenAction) {
		if(_ui.isPauseOnActionChoosen()) {
			try {
				_lock.lock();
				_ui.setResumeButtonEnabled(true);
				_actionCondition.await();
			} catch(InterruptedException e) {
				// Do nothing here
			} finally {
				_ui.setResumeButtonEnabled(false);
				_lock.unlock();
			}
		}
	}
	
	@Override
	public void onPolicyChanged(Policy_I policy) {
		// Interessiert hier nicht
	}
	
	@Override
	public void onLearningRateChanged(double learningRate) {
		// Interessiert hier nicht
	}
	
	@Override
	public void onDiscountFactorChanged(double discountFactor) {
		// Interessiert hier nicht
	}

	@Override
	public void onNewStateReached(int reachedStates, int maxStates) {
		// Interessiert hier nicht
	}
}
