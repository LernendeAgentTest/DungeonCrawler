package org.haw.projekt.agenten.dungeonkoala.util.swing.components.separator;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class LabelledSeparator extends JPanel {
	private JLabel myLabel;
	private JSeparator mySeparator;
	
	public LabelledSeparator() {
		this("");
	}
	
	public LabelledSeparator(String label) {
		initComponents(label);
		initLayout();
	}
	
	private void initComponents(String label) {
		myLabel = new JLabel(label);
		mySeparator = new NonSpaceEatingSeparator();
	}
	
	private void initLayout() {
		myLabel.setAlignmentY(CENTER_ALIGNMENT);
		mySeparator.setAlignmentY(CENTER_ALIGNMENT);
		
		int height = (myLabel.getPreferredSize().height > mySeparator.getPreferredSize().height ? myLabel.getPreferredSize().height : mySeparator.getPreferredSize().height);
		setMinimumSize(new Dimension(0, height));
		setPreferredSize(new Dimension(myLabel.getPreferredSize().width + mySeparator.getPreferredSize().width, height));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(Box.createHorizontalStrut(5));
		add(myLabel);
		add(Box.createHorizontalStrut(5));
		add(mySeparator);
	}
	
	public String getLabel() {
		return myLabel.getText();
	}
	
	public void setLabel(String label) {
		myLabel.setText(label);
	}
	
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if(mySeparator != null) {
			mySeparator.setBackground(bg.brighter());
		}
	}
	
	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if(myLabel != null) {
			myLabel.setForeground(fg);
		}
		if(mySeparator != null) {
			mySeparator.setForeground(fg);
		}
	}
}
