package org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HealActionAnimation extends AbstractAnimation<Double> {
	private static final String HEAL_ICON = "img/actions/heal.png";
	
	private static final int OFFSET_START = 50;
	private static final int OFFSET_END   = -50;
	
	private BufferedImage _image;
	private double _offsetY;
	
	public HealActionAnimation(int x, int y) {
		super(x, y);
		try {
			_image = ImageIO.read(getClass().getClassLoader().getResource(HEAL_ICON));
		} catch (IOException e) {
			throw new RuntimeException("Das Bild für die HealActionAnimation konnte nicht geladen werden!", e);
		}
	}
	
	@Override
	protected Double setupAnimation(int updates) {
		_offsetY = OFFSET_START;
		return (OFFSET_END - OFFSET_START) / (double)updates;
	}
	
	@Override
	protected void animateIteration(int iteration, int ofIterations, Double increment) {
		_offsetY += increment;
	}
	
	@Override
	public void paint(Graphics2D g) {
		double scale = 2;
		g.drawImage(
				_image,
				(int)(getBaseX()            - _image.getWidth()*scale/2),
				(int)(getBaseY() + _offsetY - _image.getHeight()*scale/2),
				(int)(_image.getWidth()*scale),
				(int)(_image.getHeight()*scale),
				null
		);
	}
}
