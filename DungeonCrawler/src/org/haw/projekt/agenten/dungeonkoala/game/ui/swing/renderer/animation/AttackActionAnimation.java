package org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;

public class AttackActionAnimation extends AbstractAnimation<Double> {
//	private static final String ATTACK_ICON = "img/actions/attack.png";

	private static final int OFFSET_START = -400;
	private static final int OFFSET_END   = 0;
	
	private BufferedImage _image;
	private double _offsetX;
	private boolean _flipHorizontally;
	
	public AttackActionAnimation(int x, int y, boolean reverse) {
		super(x, y);
//		try {
//			_image = ImageIO.read(getClass().getClassLoader().getResource(ATTACK_ICON));
//		} catch (IOException e) {
//			throw new RuntimeException("Das Bild für die AttackActionAnimation konnte nicht geladen werden!", e);
//		}
		_flipHorizontally = reverse;
	}
	
	public void setElementAttackActionImage(Element element, int energy) {
		String image_directory = element.getElementAttackImage(energy);
		try {
			_image = ImageIO.read(getClass().getClassLoader().getResource(image_directory));
		} catch (IOException e) {
			throw new RuntimeException("Das Bild für die ElementAttackActionAnimation konnte nicht geladen werden!", e);
		}
	}
	
	@Override
	protected Double setupAnimation(int updates) {
		_offsetX = OFFSET_START;
		return (OFFSET_END - OFFSET_START) / (double)updates;
	}
	
	@Override
	protected void animateIteration(int iteration, int ofIterations, Double increment) {
		_offsetX += increment;
	}
	
	@Override
	public void paint(Graphics2D g) {
		g = (Graphics2D)g.create();
		g.translate(getBaseX(), getBaseY());
		if(_flipHorizontally) {
			g.scale(-1, 1);
		}else{
			g.scale(1, -1);
		}
		g.rotate(0.5);
		g.drawImage(
			_image,
			(int)(_offsetX - _image.getWidth()/2),
			(int)(         - _image.getHeight()/2),
			_image.getWidth(),
			_image.getHeight(),
			null
		);
	}
}
