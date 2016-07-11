package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.swing;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.haw.projekt.agenten.dungeonkoala.learning.feature.FeatureSetListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.FeatureSet_I;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.Feature_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.FeatureListUI_I;

public class SwingFeatureList implements FeatureListUI_I, FeatureSetListener_I {
	private FeatureSet_I _featureSet;
	private DefaultListModel<Feature_I> _listModel = new DefaultListModel<>();
	
	private JPanel _content;
	private JLabel _stateLabel;
	private JList<Feature_I> _featureList;
	
	public SwingFeatureList() {
		initComponents();
		initLayout();
	}
	
	private void initComponents() {
		_content = new JPanel(new BorderLayout());
		_stateLabel = new JLabel();
		_featureList = new JList<>(_listModel);
		_featureList.setCellRenderer(new FeatureListCellRenderer());
	}
	
	private void initLayout() {
		JPanel statesPanel = new JPanel();
		statesPanel.setLayout(new BoxLayout(statesPanel, BoxLayout.LINE_AXIS));
		statesPanel.add(new JLabel("Gesamtzustand: "));
		statesPanel.add(_stateLabel);
		statesPanel.add(Box.createHorizontalGlue());
		
		_content.setLayout(new BoxLayout(_content, BoxLayout.PAGE_AXIS));
		_content.add(statesPanel);
		_content.add(new JScrollPane(_featureList));
	}
	
	@Override
	public void setFeatureSet(FeatureSet_I featureSet) {
		if(_featureSet != null) {
			_featureSet.removeFeatureSetListener(this);
		}
		_featureSet = featureSet;
		_featureSet.addFeatureSetListener(this);
		
		_listModel.clear();
		for(Feature_I feature : featureSet.getFeatures()) {
			_listModel.addElement(feature);
		}
	}
	
	public JComponent getComponent() {
		return _content;
	}
	
	@Override
	public void onFeatureStateChanged(Feature_I feature, int oldState, int newState) {
		_stateLabel.setText(_featureSet.getDescription(_featureSet.getCurrentState()));
		_featureList.repaint();
	}
}
