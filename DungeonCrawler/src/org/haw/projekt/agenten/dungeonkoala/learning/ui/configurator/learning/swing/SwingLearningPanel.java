package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.learning.swing;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.learning.LearningUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.learning.LearningUI_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;
import org.haw.projekt.agenten.dungeonkoala.util.swing.SwingUtilities;

public class SwingLearningPanel implements LearningUI_I {
	private EventDispatcher<LearningUIListener_I> _eventDispatcher;
	
	private JPanel _content;
	private JSpinner _iterations;
	private JLabel _reachedStates;
	
	public SwingLearningPanel() {
		initComponents();
		initLayout();
		initListeners();
	}
	
	private void initComponents() {
		_content = new JPanel();
		_iterations = new JSpinner(new SpinnerNumberModel(100, 0, Integer.MAX_VALUE, 100));
		_reachedStates = new JLabel("0");
	}
	
	private void initLayout() {
		_content.setLayout(new BoxLayout(_content, BoxLayout.PAGE_AXIS));
		_content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		_content.add(SwingUtilities.labelComponent(_iterations, "Iterationen:"));
		_content.add(SwingUtilities.labelComponent(_reachedStates, "Erreichte States:"));
	}
	
	private void initListeners() {
		_eventDispatcher = new EventDispatcher<>();
		_iterations.addChangeListener(e -> _eventDispatcher.fireEvent(l -> l.onIterationsChanged(getIterations())));
	}
	
	@Override
	public void addLearningUIListener(LearningUIListener_I listener) {
		_eventDispatcher.addListener(listener);
	}
	
	@Override
	public void removeLearningUIListener(LearningUIListener_I listener) {
		_eventDispatcher.removeListener(listener);
	}
	
	@Override
	public void setIterations(int iterations) {
		_iterations.setValue(iterations);
	}
	
	@Override
	public int getIterations() {
		return (Integer)_iterations.getValue();
	}
	
	@Override
	public boolean isEnabled() {
		return _iterations.isEnabled();
	}
	
	@Override
	public void setEnabled(boolean enable) {
		_iterations.setEnabled(enable);
	}
	
	public Component getComponent() {
		return _content;
	}

	@Override
	public void setReachedStates(int reachedStates,int maxStates) {
		double percentage = (double) reachedStates/maxStates*100;
		_reachedStates.setText(reachedStates+" of "+maxStates+" => "+(int)percentage+"%");
	}
}
