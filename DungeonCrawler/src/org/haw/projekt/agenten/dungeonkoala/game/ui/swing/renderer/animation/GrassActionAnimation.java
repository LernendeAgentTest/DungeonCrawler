package org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GrassActionAnimation extends AbstractAnimation<Double> {
	private static final String SWITCH_TO_GRASS_ICON = "img/actions/SwitchToGrass3.png";
	
	private static final double SCALE_START = 1;
	private static final double SCALE_END   = 3;
	
	private BufferedImage _image;
	private double _scale;
	
	public GrassActionAnimation(int x, int y) {
		super(x, y);
		try {
			_image = ImageIO.read(getClass().getClassLoader().getResource(SWITCH_TO_GRASS_ICON));
		} catch (IOException e) {
			throw new RuntimeException("Das Bild für die FireActionAnimation konnte nicht geladen werden!", e);
		}
	}
	
	@Override
	protected Double setupAnimation(int updates) {
		_scale = SCALE_START;
		return (SCALE_END - SCALE_START) / updates;
	}
	
	@Override
	protected void animateIteration(int iteration, int ofIterations, Double increment) {
		_scale += increment;
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.drawImage(
				_image,
				(int)(getBaseX() - _image.getWidth()*_scale/2),
				(int)(getBaseY() - _image.getHeight()*_scale/2),
				(int)(_image.getWidth()*_scale),
				(int)(_image.getHeight()*_scale),
				null
		);
	}

}
