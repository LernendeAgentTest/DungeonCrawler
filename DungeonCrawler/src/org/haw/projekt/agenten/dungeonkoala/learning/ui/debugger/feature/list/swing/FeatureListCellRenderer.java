package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.haw.projekt.agenten.dungeonkoala.learning.feature.Feature_I;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.Feature_I.FeatureState_I;

@SuppressWarnings("serial")
public class FeatureListCellRenderer extends JPanel implements ListCellRenderer<Feature_I> {
	private static final Color LIGHT_GRAY = new Color(240, 240, 240);
	
	private JLabel _featureName;
	private JLabel _featureStateDescription;
	private JLabel _featureState;
	
	public FeatureListCellRenderer() {
		_featureName = new JLabel();
		_featureStateDescription = new JLabel();
		_featureState = new JLabel();
		
		_featureState.setFont(_featureState.getFont().deriveFont(Font.BOLD));
		
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 2));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(_featureName);
		add(Box.createHorizontalGlue());
		add(_featureStateDescription);
		add(Box.createHorizontalStrut(5));
		add(_featureState);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Feature_I> list, Feature_I feature, int index, boolean isSelected, boolean cellHasFocus) {
		_featureName.setText(feature.getName());
		_featureStateDescription.setText(feature.getCurrentFeatureState().getDescription());
		_featureState.setText("[" + feature.getCurrentState() + " / " + (feature.getPossibleAllocations()-1) + "]");
		
		String tooltip = "<html>States:";
		for(FeatureState_I state : feature.getFeatureStates()) {
			tooltip += "<br />- " + state.getStateNumber() + ": " + state.getDescription().replace("<", "&lt;");
		}
		tooltip += "</html>";
		setToolTipText(tooltip);
		
		_featureName.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
		_featureStateDescription.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
		_featureState.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
		setBackground(isSelected ? list.getSelectionBackground() : index%2==0 ? list.getBackground() : LIGHT_GRAY);
		
		return this;
	}
	
}
