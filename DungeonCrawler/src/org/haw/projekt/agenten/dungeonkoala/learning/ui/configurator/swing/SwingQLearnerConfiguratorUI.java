package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.QLearnerConfiguratorUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.QLearnerConfiguratorUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.feature.editor.FeatureEditorUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.feature.editor.swing.SwingFeatureEditor;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.learning.LearningUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.learning.swing.SwingLearningPanel;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.statistics.StatisticUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.statistics.swing.SwingStatiscticsUI;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.AgentPanelUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.swing.SwingAgentPanel;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;
import org.haw.projekt.agenten.dungeonkoala.util.swing.components.separator.LabelledSeparator;
import org.haw.projekt.agenten.dungeonkoala.util.swing.components.separator.NonSpaceEatingSeparator;

public class SwingQLearnerConfiguratorUI implements QLearnerConfiguratorUI_I {
	private EventDispatcher<QLearnerConfiguratorUIListener_I> _eventDispatcher;
	
	private JFrame _frame;
	private SwingAgentPanel _agentPanel;
	private SwingFeatureEditor _featureEditor;
	private SwingLearningPanel _learningPanel;
	private SwingStatiscticsUI _statisticsUI;
	private JProgressBar _progressBar;
	private JButton _debuggerButton;
	private JButton _learnButton;
	private JButton _startButton;
	private JButton _statisticButton;
	
	public SwingQLearnerConfiguratorUI() {
		initComponents();
		initLayout();
		initListeners();
	}
	
	private void initComponents() {
		_frame = new JFrame("QLearner Configurator");
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		_agentPanel = new SwingAgentPanel();
		_featureEditor = new SwingFeatureEditor();
		_learningPanel = new SwingLearningPanel();
		_statisticsUI = new SwingStatiscticsUI();
		_progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
		_progressBar.setStringPainted(true);
		_progressBar.setString("Iteration 0 / " + _learningPanel.getIterations());
		_debuggerButton = new JButton("Debugger");
		_learnButton = new JButton("Lernen");
		_startButton = new JButton("Starten");
		_learnButton.setToolTipText("Lässt einen neuen Agenten mit den aktuellen Einstellungen lernen");
		_startButton.setToolTipText("Lässt den Agenten spielen, sodass man ihm zuschauen kann");
		_statisticButton = new JButton("Statistik");
		_statisticButton.setToolTipText("Berechnet eine Statistik über eine Anzahl von {Iterationen} an Spielen");
	}
	
	private void initLayout() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
		buttonPanel.add(_progressBar);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPanel.add(_debuggerButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPanel.add(_learnButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPanel.add(_statisticButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPanel.add(_startButton);
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		content.add(new LabelledSeparator("Agenten-Konfiguration"));
		content.add(_agentPanel.getComponent());
		content.add(Box.createVerticalStrut(5));
		content.add(new LabelledSeparator("Feature-Konfiguration"));
		content.add(_featureEditor.getComponent());
		content.add(Box.createVerticalStrut(5));
		content.add(new LabelledSeparator("Statistics"));
		content.add(_statisticsUI.getContent());
		content.add(Box.createVerticalStrut(5));
		content.add(new LabelledSeparator("Learning"));
		content.add(_learningPanel.getComponent());
		content.add(Box.createVerticalStrut(5));
		content.add(new NonSpaceEatingSeparator());
		content.add(buttonPanel);

		_frame.setContentPane(content);
		_frame.pack();
		_frame.setLocationRelativeTo(null);
	}
	
	private void initListeners() {
		_eventDispatcher = new EventDispatcher<QLearnerConfiguratorUIListener_I>();
		_debuggerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_eventDispatcher.fireEvent(l -> l.onShowDebugger());
			}
		});
		_learnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_eventDispatcher.fireEvent(l -> l.onLearn());
			}
		});
		_startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_eventDispatcher.fireEvent(l -> l.onStart());
			}
		});
		_statisticButton.addActionListener(e->_eventDispatcher.fireEvent(l->l.onDoStatistics()));
	}
	
	@Override
	public void addQLearnerConfiguratorUIListener(QLearnerConfiguratorUIListener_I listener) {
		_eventDispatcher.addListener(listener);
		_agentPanel.addAgentPanelListener(listener);
		_featureEditor.addFeatureEditorUIListener(listener);
		_learningPanel.addLearningUIListener(listener);
	}
	
	@Override
	public void removeQLearnerConfiguratorUIListener(QLearnerConfiguratorUIListener_I listener) {
		_eventDispatcher.removeListener(listener);
		_agentPanel.removeAgentPanelListener(listener);
		_featureEditor.removeFeatureEditorUIListener(listener);
		_learningPanel.removeLearningUIListener(listener);
	}
	
	@Override
	public AgentPanelUI_I getAgentPanelUI() {
		return _agentPanel;
	}
	
	@Override
	public FeatureEditorUI_I getFeatureEditorUI() {
		return _featureEditor;
	}
	
	@Override
	public LearningUI_I getLearningUI() {
		return _learningPanel;
	}
	
	@Override
	public void initProgress(int min, int max) {
		_progressBar.setMinimum(min);
		_progressBar.setMaximum(max);
	}
	
	@Override
	public int getProgress() {
		return _progressBar.getValue();
	}
	
	@Override
	public void updateProgress() {
		updateProgress(getProgress() + 1);
	}
	
	@Override
	public void updateProgress(int current) {
		_progressBar.setValue(current);
		_progressBar.setString("Iteration " + current + " / " + _progressBar.getMaximum());
	}
	
	@Override
	public boolean isEnabled() {
		return _learnButton.isEnabled() && _startButton.isEnabled();
	}
	
	@Override
	public void setEnabled(boolean enable) {
		_learnButton.setEnabled(enable);
		_startButton.setEnabled(enable);
		_statisticButton.setEnabled(enable);
	}
	
	@Override
	public void show() {
		_frame.pack();
		_frame.setVisible(true);
	}

	@Override
	public StatisticUI_I getStatisticUI() {
		return _statisticsUI;
	}

	@Override
	public void hide() {
		_frame.setVisible(false);
	}
}
