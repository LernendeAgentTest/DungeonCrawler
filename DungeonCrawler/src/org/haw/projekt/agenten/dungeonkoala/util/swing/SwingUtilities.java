package org.haw.projekt.agenten.dungeonkoala.util.swing;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwingUtilities {
	public static JPanel labelComponent(Component component, String label) {
		JLabel jlabel = new JLabel(label);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, component.getMinimumSize().height));
		panel.add(jlabel);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(component);
		return panel;
	}
}
