package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.swing;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.QLearnerDebuggerUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.QLearnerDebuggerUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.AgentPanelUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.swing.SwingAgentPanel;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.debugpanel.DebugPanelUI;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.debugpanel.swing.SwingDebugPanel;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.explorationpanel.ExplorationUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.explorationpanel.swing.SwingExplorationPanel;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.FeatureListUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.logging.Logger;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.logging.swing.Log;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.swing.SwingFeatureList;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable.QValueTableUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable.swing.SwingQValueTable;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.rewardpanel.RewardUI_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.rewardpanel.swing.SwingRewardPanel;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

public class SwingQLearnerDebuggerUI implements QLearnerDebuggerUI_I {
	private EventDispatcher<QLearnerDebuggerUIListener_I> _eventDispatcher;
	
	private JFrame _frame;
	private SwingAgentPanel _agentPanel;
	private SwingFeatureList _featureList;
	private SwingQValueTable _qValueTable;
	private SwingDebugPanel _debugPanel;
	private SwingRewardPanel _rewardPanel;
	private SwingExplorationPanel _explorationPanel;
	
	public SwingQLearnerDebuggerUI() {
		initComponents();
		initLayout();
		initListeners();
	}
	
	private void initComponents() {
		_frame = new JFrame("QLearner Debugger");
		_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		_agentPanel = new SwingAgentPanel();
		_featureList = new SwingFeatureList();
		_qValueTable = new SwingQValueTable();
		_debugPanel = new SwingDebugPanel();
		_rewardPanel = new SwingRewardPanel();
		_explorationPanel = new SwingExplorationPanel();
	}
	
	private void initLayout() {
		_agentPanel.getComponent().setAlignmentX(JComponent.LEFT_ALIGNMENT);
		_debugPanel.getComponent().setAlignmentX(JComponent.LEFT_ALIGNMENT);
		_rewardPanel.getComponent().setAlignmentX(JComponent.LEFT_ALIGNMENT);
		_explorationPanel.getComponent().setAlignmentX(JComponent.LEFT_ALIGNMENT);

		JTabbedPane tabs = new JTabbedPane();
		tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabs.add("Debug", _debugPanel.getComponent());
		tabs.add("Agent", _agentPanel.getComponent());
		tabs.add("Reward", _rewardPanel.getComponent());
		tabs.add("Exploration", _explorationPanel.getComponent());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(tabs);
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, panel, _featureList.getComponent());
		split.setBorder(null);
		split.setOneTouchExpandable(true);
		
		JSplitPane split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, split, _qValueTable.getComponent());
		split2.setBorder(null);
		split2.setOneTouchExpandable(true);
		
		split2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		JPanel contenPanel = new JPanel(new BorderLayout());
		contenPanel.add(split2,BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		contenPanel.add(menuBar,BorderLayout.NORTH);
		
		JMenu viewMenue = new JMenu("Views");
		menuBar.add(viewMenue);
		
		JMenuItem featureLogItem = new JMenuItem("Feature Log");
		featureLogItem.addActionListener(l -> {
			new Log(Logger.logger).show();
		});
		viewMenue.add(featureLogItem);
		
		_frame.setContentPane(contenPanel);
		_frame.pack();
		_frame.setLocationRelativeTo(null);
	}
	
	private void initListeners() {
		_eventDispatcher = new EventDispatcher<QLearnerDebuggerUIListener_I>();
	}
	
	@Override
	public void addQLearnerUIListener(QLearnerDebuggerUIListener_I listener) {
		_eventDispatcher.addListener(listener);
	}
	
	@Override
	public void removeQLearnerUIListener(QLearnerDebuggerUIListener_I listener) {
		_eventDispatcher.removeListener(listener);
	}
	
	@Override
	public AgentPanelUI_I getAgentPanelUI() {
		return _agentPanel;
	}
	
	@Override
	public FeatureListUI_I getFeatureListUI() {
		return _featureList;
	}
	
	@Override
	public QValueTableUI_I getQValueTableUI() {
		return _qValueTable;
	}
	
	@Override
	public DebugPanelUI getDebugPanelUI() {
		return _debugPanel;
	}

	@Override
	public RewardUI_I getRewardUI() {
		return _rewardPanel;
	}

	@Override
	public ExplorationUI_I getExplorationUI() {
		return _explorationPanel;
	}
	
	@Override
	public void show() {
		_frame.setVisible(true);
	}
	
	@Override
	public void hide() {
		_frame.setVisible(false);
	}
}
