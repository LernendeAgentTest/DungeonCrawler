package org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;

public class BlockActionAnimation extends AbstractAnimation<Double> implements Animation_I {
//	private static final String BLOCK_ICON = "img/actions/block.png";

	private static final double SCALE_START = 1;
	private static double SCALE_END = 1.5;

	private BufferedImage _image;
	private double _scale;

	public BlockActionAnimation(int x, int y) {
		super(x, y);
		// try {
		// _image =
		// ImageIO.read(getClass().getClassLoader().getResource(BLOCK_ICON));
		// } catch (IOException e) {
		// throw new RuntimeException("Das Bild für die BlockActionAnimation
		// konnte nicht geladen werden!", e);
		// }
	}

	public void setElementAttackActionImage(Element element, int energy) {
		switch(energy){
			case 1: SCALE_END = 2.0; break;
			case 2: SCALE_END = 2.5; break;
			case 3: SCALE_END = 3.0; break;
			default: SCALE_END = 1.5;
		}
		
		String image_directory = element.getElementBlockImage();
		
		try {
			_image = ImageIO.read(getClass().getClassLoader().getResource(image_directory));
		} catch (IOException e) {
			throw new RuntimeException("Das Bild für die BlockActionAnimation konnte nicht geladen werden!", e);
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
		g.drawImage(_image, (int) (getBaseX() - _image.getWidth() * _scale / 2),
				(int) (getBaseY() - _image.getHeight() * _scale / 2), (int) (_image.getWidth() * _scale),
				(int) (_image.getHeight() * _scale), null);
	}

	@Override
	public boolean isBuffered() {
		return true;
	}
}
