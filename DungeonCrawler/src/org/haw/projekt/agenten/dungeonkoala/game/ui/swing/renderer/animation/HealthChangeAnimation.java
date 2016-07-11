package org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class HealthChangeAnimation extends AbstractAnimation<Double> {
	private static final int OFFSET_Y_START = 0;
	private static final int OFFSET_Y_END   = -50;
	private static final int OFFSET_X       = 20;
	
	private int _healthDifference;
	private double _offsetX;
	private double _offsetY;
	
	public HealthChangeAnimation(int baseX, int baseY, int oldHealth, int newHealth) {
		super(baseX, baseY);
		_healthDifference = newHealth - oldHealth;
	}

	@Override
	protected Double setupAnimation(int updates) {
		_offsetY = OFFSET_Y_START;
		_offsetX = OFFSET_X;
		return (OFFSET_Y_END - OFFSET_Y_START) / (double)updates;
	}
	
	@Override
	protected void animateIteration(int iteration, int ofIterations, Double increment) {
		_offsetY += increment;
	}
	
	@Override
	public void paint(Graphics2D g) {
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setFont(g2.getFont().deriveFont(Font.BOLD));
		g2.setColor(_healthDifference > 0 ? Color.GREEN : Color.RED);
		g2.drawString(
				(_healthDifference > 0 ? "+" : "") + String.valueOf(_healthDifference),
				(int)(getBaseX() + _offsetX),
				(int)(getBaseY() + _offsetY)
		);
	}
}
