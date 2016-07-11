package org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class EntityMoveAnimation extends AbstractAnimation<Double> {

	private static final int OFFSET_START = 0;
	private static final int OFFSET_X = 50;
	private static final int OFFSET_Y = -50;

	private BufferedImage _image;
	private double  _offsetX, _offsetY, _incrementY;
	private boolean _isDead;
	private double _cutImage_offsetY, _scala, _scalaIncrement;

	public EntityMoveAnimation(int x, int y, BufferedImage entityImage, boolean isDead) {
		super(x, y);
		_image = entityImage;
		_isDead = isDead;		
	}
	
	@Override
	protected Double setupAnimation(int updates) {
		if(_isDead){
			_cutImage_offsetY = (double)_image.getHeight();
			_incrementY =  (OFFSET_Y - OFFSET_START)/(double)updates;
			return _cutImage_offsetY/ (double)updates;
		}else{
			_offsetX = OFFSET_X;
			_scalaIncrement = 1.0/(double)updates;
			return (OFFSET_X - OFFSET_START) / (double) updates;
		}
	}

	@Override
	protected void animateIteration(int iteration, int ofIterations, Double increment) {
		if(_isDead){
			_offsetY -= _incrementY;
			_cutImage_offsetY-= increment;
			if(_cutImage_offsetY < 1){
				_cutImage_offsetY = 1;
			}
		}else{
			_offsetX -= increment;
			_scala += _scalaIncrement;
		}
	}

	@Override
	public void paint(Graphics2D g) {
		if(_isDead){
			g.drawImage(_image, 
				(int) (getBaseX() - _image.getWidth() / 2),
				(int) (getBaseY() + _offsetY - _image.getHeight() / 2),
				_image.getWidth(),
				_image.getHeight(), 
				null);
			
			_image = _image.getSubimage(0, 0, _image.getWidth(),(int)_cutImage_offsetY);
			
		}else{
			g.drawImage(_image,
					(int) (getBaseX() + _offsetX - _image.getWidth() / 2),
					(int) (getBaseY() - _image.getHeight() / 2),
					(int)(_image.getWidth() * _scala),
					(int)(_image.getHeight() * _scala), 
					null);
			
		}
	}
}
