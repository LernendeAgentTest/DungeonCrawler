package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.agentpanel.swing;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.haw.projekt.agenten.dungeonkoala.learning.policy.Policy_I;

@SuppressWarnings("serial")
public class PolicyListCellRenderer extends JLabel implements ListCellRenderer<Policy_I> {
	@Override
	public Component getListCellRendererComponent(JList<? extends Policy_I> list, Policy_I value, int index, boolean isSelected, boolean cellHasFocus) {
		setText(value.getClass().getSimpleName());
		
		setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
		setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
		
		// -1 entspricht in einer Combo-Box dem gerade angezeigten Element
		setOpaque(index != -1);
		
		return this;
	}
}
