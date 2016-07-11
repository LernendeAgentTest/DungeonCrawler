package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.swing;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.AgentPanelUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.AgentPanelUI_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;
import org.haw.projekt.agenten.dungeonkoala.util.swing.SwingUtilities;

public class SwingAgentPanel implements AgentPanelUI_I {
	private EventDispatcher<AgentPanelUIListener_I> _dispatcher;
	
	private JPanel _content;
	private JComboBox<Policy_I> _policy;
	private JSpinner _learningRate;
	private JSpinner _discountFactor;
	
	public SwingAgentPanel() {
		initComponents();
		initLayout();
		initListeners();
	}
	
	private void initComponents() {
		_content = new JPanel();
		_policy = new JComboBox<Policy_I>();
		_learningRate = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.1));
		_discountFactor = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.1));
	}
	
	private void initLayout() {
		_policy.setRenderer(new PolicyListCellRenderer());
		
		_policy.setMaximumSize(new Dimension(Integer.MAX_VALUE, _policy.getMinimumSize().height));
		_learningRate.setMaximumSize(new Dimension(Integer.MAX_VALUE, _learningRate.getMinimumSize().height));
		_discountFactor.setMaximumSize(new Dimension(Integer.MAX_VALUE, _discountFactor.getMinimumSize().height));
		
		_content.setLayout(new BoxLayout(_content, BoxLayout.PAGE_AXIS));
		_content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		_content.add(SwingUtilities.labelComponent(_policy, "Policy:"));
		_content.add(Box.createVerticalStrut(5));
		_content.add(SwingUtilities.labelComponent(_learningRate, "Lernrate:"));
		_content.add(Box.createVerticalStrut(5));
		_content.add(SwingUtilities.labelComponent(_discountFactor, "Discount-Faktor:"));
	}
	
	private void initListeners() {
		_dispatcher = new EventDispatcher<AgentPanelUIListener_I>();
		_policy.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(ItemEvent.SELECTED == e.getStateChange()) {
					_dispatcher.fireEvent(l -> l.onPolicyChanged(getPolicy()));
				}
			}
		});
		_learningRate.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				_dispatcher.fireEvent(l -> l.onLearningRateChanged(getLearningRate()));
			}
		});
		_discountFactor.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				_dispatcher.fireEvent(l -> l.onDiscountFactorChanged(getDiscountFactor()));
			}
		});
	}
	
	@Override
	public void addAgentPanelListener(AgentPanelUIListener_I listener) {
		_dispatcher.addListener(listener);
	}
	
	@Override
	public void removeAgentPanelListener(AgentPanelUIListener_I listener) {
		_dispatcher.removeListener(listener);
	}
	
	@Override
	public void setPolicies(List<Policy_I> policies) {
		_policy.setModel(new DefaultComboBoxModel<Policy_I>(policies.toArray(new Policy_I[0])));
	}
	
	@Override
	public Policy_I getPolicy() {
		return (Policy_I)_policy.getSelectedItem();
	}
	
	@Override
	public void setPolicy(Policy_I policy) {
		_policy.setSelectedItem(policy);
	}
	
	@Override
	public double getLearningRate() {
		return (Double)_learningRate.getValue();
	}
	
	@Override
	public void setLearningRate(double learningRate) {
		_learningRate.setValue(learningRate);
	}
	
	@Override
	public double getDiscountFactor() {
		return (Double)_discountFactor.getValue();
	}
	
	@Override
	public void setDiscountFactor(double discountFactor) {
		_discountFactor.setValue(discountFactor);
	}
	
	@Override
	public boolean isEnabled() {
		return _policy.isEnabled() && _learningRate.isEnabled() && _discountFactor.isEnabled();
	}
	
	@Override
	public void setEnabled(boolean enable) {
		_policy.setEnabled(enable);
		_learningRate.setEnabled(enable);
		_discountFactor.setEnabled(enable);
	}
	
	public JComponent getComponent() {
		return _content;
	}
}
