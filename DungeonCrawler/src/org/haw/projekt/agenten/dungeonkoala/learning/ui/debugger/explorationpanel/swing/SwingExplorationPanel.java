package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.explorationpanel.swing;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.explorationpanel.ExplorationUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.explorationpanel.ExplorationUI_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;
import org.haw.projekt.agenten.dungeonkoala.util.swing.SwingUtilities;

public class SwingExplorationPanel implements ExplorationUI_I{

	private static final double STANDARD_EXPLORATION_RATE = 0.1;
	private double _explorationCounterValue = 0.0;

	private JPanel _content;
	private JSpinner _explorationRateSpinner;
	private JTextField _explorationCounter;
	private EventDispatcher<ExplorationUIListener_I> _dispatcher = new EventDispatcher<>();

	public SwingExplorationPanel() {
		initComponents();
		initLayout();
	}

	private void initLayout() {
		_content = new JPanel();
		_content.setLayout(new BoxLayout(_content, BoxLayout.PAGE_AXIS));
		_content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		_content.add(SwingUtilities.labelComponent(_explorationRateSpinner, "Exploration Rate:"));
		_content.add(Box.createVerticalStrut(5));
		_content.add(SwingUtilities.labelComponent(_explorationCounter, "Exploration Counter:"));
	}

	private void initComponents() {
		_explorationRateSpinner = new JSpinner(
				new SpinnerNumberModel(STANDARD_EXPLORATION_RATE, 0.0, 1.0, 0.1));
		_explorationRateSpinner
				.setMaximumSize(new Dimension(Integer.MAX_VALUE, _explorationRateSpinner.getMinimumSize().height));
		_explorationRateSpinner.addChangeListener((e) -> {
			_dispatcher.fireEvent((l) -> l.onExplorationRateChanged(getExplorationRate()));
		});
		_explorationRateSpinner.setEnabled(false);
		
		_explorationCounter = new JTextField("0");
		_explorationCounter.setEditable(false);
		_explorationCounter.setHorizontalAlignment(JTextField.RIGHT);
		_explorationCounter.setMaximumSize(new Dimension(Integer.MAX_VALUE, _explorationCounter.getMinimumSize().height));
	}

	@Override
	public JPanel getComponent() {
		return _content;
	}

	public double getExplorationRate() {
		return (Double) _explorationRateSpinner.getValue();
	}

	public double getExplorationCounter() {
		return _explorationCounterValue;
	}

	public void setExplorationRate(double reward) {
		_explorationRateSpinner.setValue(reward);
	}

	public void setExplorationCounter(double counter) {
		_explorationCounterValue = counter;
		_explorationCounter.setText(String.format("%.0f", _explorationCounterValue));
	}
	
	public void setExplorationRateEnabled(boolean enabled) {
		_explorationRateSpinner.setEnabled(enabled);
	}

	public void addExplorationListener(ExplorationUIListener_I listener) {
		_dispatcher.addListener(listener);
	}

	public void removeExplorationListener(ExplorationUIListener_I listener) {
		_dispatcher.removeListener(listener);
	}
}
