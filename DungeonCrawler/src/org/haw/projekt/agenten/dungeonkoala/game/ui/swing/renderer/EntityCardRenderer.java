package org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.element.Element;

public class EntityCardRenderer {
	public static final int WIDTH  = 280;
	public static final int HEIGHT = 90;
	public static final int PADDING = 5;
	
//	private Color _background;
//	private Color _outline;
	
	private Component _component;
	private Entity _entity;
	private BufferedImage _entityIcon;
	private ImageIcon _blockIcon;
	private ImageIcon _chargeIcon;
	private ImageIcon _elementIcon;
	
	public EntityCardRenderer(Component component, Entity entity, BufferedImage entityIcon, ImageIcon blockIcon, ImageIcon chargeIcon, ImageIcon elementIcon) {
//		_background = new Color(220, 220, 255);
//		_outline = new Color(180, 180, 220);
		
		_component = component;
		_entity = entity;
		_entityIcon = entityIcon;
		_blockIcon = blockIcon;
		_chargeIcon = chargeIcon;
		_elementIcon = elementIcon;
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public void paint(Graphics2D g, int x, int y, Element element, int clearIcon_Number) {
		Color oldColor = g.getColor();
		Font oldFont = g.getFont();
		
		int xoffset = x+HEIGHT;
		int yoffset = y;
		
		if(clearIcon_Number == SwingGameRenderer.CLEAR_ENTITY_ICON){
			paintCard(g, x, y, element);
			yoffset = paintName(g, xoffset, yoffset);
			yoffset = paintStatusIcons(g, xoffset, yoffset);
			paintHealthBar(g, xoffset, y+HEIGHT);
			
		}else if(clearIcon_Number == SwingGameRenderer.CLEAR_ENTITY_STATUS_HEALTH){
			yoffset = paintName(g, xoffset, yoffset);
			
		}else{
			paintCard(g, x, y, element);
			paintIcon(g, x, y);
			yoffset = paintName(g, xoffset, yoffset);
			yoffset = paintStatusIcons(g, xoffset, yoffset);
			paintHealthBar(g, xoffset, y+HEIGHT);
			
		}
		
		g.setColor(oldColor);
		g.setFont(oldFont);
	}
	
	private void paintCard(Graphics2D g, int x, int y, Element element) {
		try {
			Image img = ImageIO.read(new File(element.getElementButtonImage()));
			Image scaledImage = img.getScaledInstance(WIDTH, HEIGHT,Image.SCALE_SMOOTH);

			g.drawImage(scaledImage, x, y, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		int arc = 2;
//		g.setColor(_background);
//		g.fillRoundRect(x, y, WIDTH, HEIGHT, arc, arc);
//		g.setColor(_outline);
//		g.drawRoundRect(x, y, WIDTH, HEIGHT, arc, arc);
		
	}
	
	private void paintIcon(Graphics2D g, int x, int y) {
		g.drawImage(_entityIcon, x+(HEIGHT-_entityIcon.getWidth())/2, y+(HEIGHT-_entityIcon.getHeight())/2, null);
	}
	
	private int paintName(Graphics2D g, int x, int y) {
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		
		int lineHeight = g.getFontMetrics().getHeight();
		g.drawString(_entity.getName(), x+PADDING, y+lineHeight+PADDING);
		
		g.drawString("(" + _entity.getBehaviour().getDisplayName() + ")", x+PADDING, y+lineHeight*2+PADDING);
		
		return y+lineHeight*2+PADDING*2;
	}
	
	private int paintStatusIcons(Graphics2D g, int x, int y) {
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
		
		_elementIcon.paintIcon(_component, g, x, y);

		_chargeIcon.paintIcon(_component, g, x+_elementIcon.getIconWidth(), y);
		g.drawString("+" + _entity.getEnergy(), x+_elementIcon.getIconWidth()+_chargeIcon.getIconWidth(), y+_chargeIcon.getIconHeight());
		
		if(_entity.isBlocking()) {
			_blockIcon.paintIcon(_component, g, x+_elementIcon.getIconWidth()+_chargeIcon.getIconWidth()*2, y);
			g.drawString("+" + _entity.getBlockLevel(), x+_elementIcon.getIconWidth()+_chargeIcon.getIconWidth()*2+_blockIcon.getIconWidth(), y+_blockIcon.getIconHeight());
		}
		
		return y+_chargeIcon.getIconHeight();
	}
	
	private void paintHealthBar(Graphics2D g, int x, int y) {
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		
		int textHeight = g.getFontMetrics().getHeight();
		
		double percentage = _entity.getHealth().getPercentage();
		g.setColor(pickHealthBarColor(percentage));
		g.fillRect(x+PADDING, y-PADDING-textHeight, (int)((HEIGHT*2-PADDING*2)*percentage), textHeight);
		
		g.setColor(Color.BLACK);
		g.drawString("HP: " + _entity.getHealth().getValue(), x+PADDING, y-g.getFontMetrics().getDescent()-PADDING);
	}
	
	private Color pickHealthBarColor(double percentage) {
		if(percentage < 0.33) {
			return new Color(255, 200, 200);
		} else if(percentage > 0.66) {
			return new Color(200, 255, 200);
		} else {
			return new Color(255, 255, 200);
		}
	}
}
