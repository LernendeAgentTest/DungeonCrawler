package org.haw.projekt.agenten.dungeonkoala.util.swing.components.separator;

import java.awt.Dimension;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class NonSpaceEatingSeparator extends JSeparator {
	public NonSpaceEatingSeparator() {
		this(SwingConstants.HORIZONTAL);
	}
	
	public NonSpaceEatingSeparator(int orientation) {
		applyMaxSize(orientation);
	}
	
	@Override
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(d);
		applyMaxSize(getOrientation());
	}
	
	private void applyMaxSize(int orientation) {
		if(orientation == SwingConstants.HORIZONTAL) {
			setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
		} else if(orientation == SwingConstants.VERTICAL) {
			setMaximumSize(new Dimension(getPreferredSize().width, Integer.MAX_VALUE));
		}
	}
}
