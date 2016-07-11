
package org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.AttackAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.BlockAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.ChargeAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.HealAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.SwitchToFireAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.SwitchToGrassAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.SwitchToWaterAction;
import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.Enemy;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.EntityListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameAdapter;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;
import org.haw.projekt.agenten.dungeonkoala.game.ui.Renderer_I;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation.Animation_I;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation.AttackActionAnimation;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation.BlockActionAnimation;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation.ChargeActionAnimation;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation.EntityMoveAnimation;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation.FireActionAnimation;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation.GrassActionAnimation;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation.HealActionAnimation;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation.HealthChangeAnimation;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation.WaterActionAnimation;

/**
 * Der Renderer zeichnet die Darstellung des aktuellen Spielzustands.
 */
@SuppressWarnings("serial")
public class SwingGameRenderer extends JPanel implements Renderer_I {
	private static final String ICON_PLAYER = "img/entities/dungeon_koala.png";
	private static final String ICON_ENEMY  = "img/entities/enemy.png";
	private static final String ICON_STATUS_BLOCK = "img/entities/status/block.png";
	private static final String ICON_STATUS_CHARGE = "img/entities/status/charge.png";
	
	private static final int DEFAULT_FPS = 60;
	private static final int DEFAULT_DURATION_MS = 300;
	
	public static final int CLEAR_ENTITY_ICON = 1;
	public static final int CLEAR_ENTITY_STATUS_HEALTH = 2;
	public static final int NO_CLEAR = 0;
	
	private BufferedImage _playerImage;
	private BufferedImage _enemyImage;
	private ImageIcon _statusBlockImage;
	private ImageIcon _statusChargeImage;
	
	private Map<Class<? extends Action>, Animation_I> _playerActionAnimationMapping;
	private Map<Class<? extends Action>, Animation_I> _enemyActionAnimationMapping;
	private List<Animation_I> _animations;
	private int _animationFPS;
	private int _animationDuration;
	
	private Room _room;
	private Player _player;
	
	private PlayerListener _playerListener;
	private EnemyListener _enemyListener;
	private GameListener _gameListener;
	
	private AttackActionAnimation _playersAttackAction;
	private BlockActionAnimation _playersBlockAction;
	
	private AttackActionAnimation _enemysAttackAction;
	private BlockActionAnimation _enemysBlockAction;
	
	private int _playerX, _playerY, _enemyX, _enemyY;
	private int _clearPlayerIcon, _clearEnemyIcon;
	
	public SwingGameRenderer() {
		setPreferredSize(new Dimension(800, 500));
		
		try {
			_playerImage = ImageIO.read(getClass().getClassLoader().getResource(ICON_PLAYER));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			_enemyImage = ImageIO.read(getClass().getClassLoader().getResource(ICON_ENEMY));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		_statusBlockImage = new ImageIcon(getClass().getClassLoader().getResource(ICON_STATUS_BLOCK));
		_statusChargeImage = new ImageIcon(getClass().getClassLoader().getResource(ICON_STATUS_CHARGE));
		
		_playerX = 50 + EntityCardRenderer.WIDTH/6;
		_playerY = getPreferredSize().height - EntityCardRenderer.HEIGHT + EntityCardRenderer.HEIGHT/2 - 50;
		_enemyX = getPreferredSize().width - EntityCardRenderer.WIDTH + EntityCardRenderer.WIDTH/6 - 50;
		_enemyY = 50 + EntityCardRenderer.HEIGHT/2;
		
		_playersAttackAction = new AttackActionAnimation(_enemyX, _enemyY, false);
		_playersBlockAction = new BlockActionAnimation(_playerX, _playerY);
		
		_enemysAttackAction = new AttackActionAnimation(_playerX, _playerY, true);
		_enemysBlockAction = new BlockActionAnimation(_enemyX, _enemyY);
		
		_playerActionAnimationMapping = new HashMap<>();
		_playerActionAnimationMapping.put(AttackAction.class, _playersAttackAction);
		_playerActionAnimationMapping.put(HealAction.class, new HealActionAnimation(_playerX, _playerY));
		_playerActionAnimationMapping.put(BlockAction.class, _playersBlockAction);
		_playerActionAnimationMapping.put(ChargeAction.class, new ChargeActionAnimation(_playerX, _playerY));
		_playerActionAnimationMapping.put(SwitchToFireAction.class, new FireActionAnimation(_playerX, _playerY));
		_playerActionAnimationMapping.put(SwitchToWaterAction.class, new WaterActionAnimation(_playerX, _playerY));
		_playerActionAnimationMapping.put(SwitchToGrassAction.class, new GrassActionAnimation(_playerX, _playerY));
		
		_enemyActionAnimationMapping = new HashMap<>();
		_enemyActionAnimationMapping.put(AttackAction.class, _enemysAttackAction);
		_enemyActionAnimationMapping.put(HealAction.class, new HealActionAnimation(_enemyX, _enemyY));
		_enemyActionAnimationMapping.put(BlockAction.class, _enemysBlockAction);
		_enemyActionAnimationMapping.put(ChargeAction.class, new ChargeActionAnimation(_enemyX, _enemyY));
		_enemyActionAnimationMapping.put(SwitchToFireAction.class, new FireActionAnimation(_enemyX, _enemyY));
		_enemyActionAnimationMapping.put(SwitchToWaterAction.class, new WaterActionAnimation(_enemyX, _enemyY));
		_enemyActionAnimationMapping.put(SwitchToGrassAction.class, new GrassActionAnimation(_enemyX, _enemyY));
		
		_animations = new LinkedList<Animation_I>();
		_animationFPS = DEFAULT_FPS;
		_animationDuration = DEFAULT_DURATION_MS;
		
		_playerListener = new PlayerListener();
		_enemyListener = new EnemyListener();
		
		_gameListener = new GameListener();
	}
	
	@Override
	public int getAnimationFPS() {
		return _animationFPS;
	}
	
	@Override
	public void setAnimationFPS(int fps) {
		if(fps < 0) {
			throw new IllegalArgumentException("Die Frames per Second für Animationen dürfen nicht negativ sein!");
		}
		_animationFPS = fps;
	}
	
	@Override
	public int getAnimationDuration() {
		return _animationDuration;
	}
	
	@Override
	public void setAnimationDuration(int durationMS) {
		if(durationMS < 0) {
			throw new IllegalArgumentException("Die Anzeigedauer für Animationen kann nicht negativ sein!");
		}
		_animationDuration = durationMS;
	}
	
	@Override
	public void setRoom(Room room) {
		if(_room != null) {
			_room.getEnemy().removeEntityListener(_enemyListener);
		}
		_room = room;
		if(_room != null) {
			enemyJoin_Animation();
			_room.getEnemy().addEntityListener(_enemyListener);
		}
		repaint();
	}
	
	@Override
	public void setPlayer(Player player) {
		if(_player != null) {
			_player.removeEntityListener(_playerListener);
		}
		_player = player;
		if(_player != null) {
			_player.addEntityListener(_playerListener);
		}
		repaint();
	}
	
	@Override
	public GameListener_I getGameListener() {
		return _gameListener;
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics;
		
		paintBackground(g);
		
		if(_room != null) {
			paintRoom(g, _room);
		}
		
		if(_player != null) {
			paintPlayer(g, _player);
		}
		
		
		for(Animation_I animation : _animations) {
			animation.paint(g);
		}
	}
	
	/**
	 * Zeichnet den Hintergrund.
	 * @param g Ein Graphics-Objekt zum Zeichnen
	 */
	private void paintBackground(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	/**
	 * Zeichnet den übergebenen Raum.
	 * @param g Ein Graphics-Objekt zum Zeichnen
	 * @param room Der Raum
	 */
	private void paintRoom(Graphics2D g, Room room) {
		try {
			Image img = ImageIO.read(new File(room.getRoomImage()));
			g.drawImage(img, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		paintEnemy(g, room.getEnemy());
	}
	
	/**
	 * Zeichnet den übergebenen Spieler.
	 * @param g Ein Graphics-Objekt zum Zeichnen
	 * @param player Der Spieler
	 */
	private void paintPlayer(Graphics2D g, Player player) {
		int padding = 50;
		EntityCardRenderer cardRenderer = new EntityCardRenderer(this, player, _playerImage, _statusBlockImage, _statusChargeImage, player.getElement().getElementIcon());
		cardRenderer.paint(g, padding, getHeight()-cardRenderer.getHeight()-padding, player.getElement(), _clearPlayerIcon);
		
	}
	
	/**
	 * Zeichnet den übergebenen Gegner.
	 * @param g Ein Graphics-Objekt zum Zeichnen
	 * @param enemy Der Gegner
	 */
	private void paintEnemy(Graphics2D g, Enemy enemy) {
		int padding = 50;
		EntityCardRenderer cardRenderer = new EntityCardRenderer(this, enemy, _enemyImage, _statusBlockImage, _statusChargeImage, enemy.getElement().getElementIcon());
		cardRenderer.paint(g, getWidth()-cardRenderer.getWidth()-padding, padding, enemy.getElement(), _clearEnemyIcon);
	
	
	}
	
	private void animate(Animation_I animation) {
		if(animation != null) {
			_animations.add(animation);
			animation.animate(_animationFPS, _animationDuration, () -> {
				try {
					SwingUtilities.invokeAndWait(() -> {
						repaint();
					});
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
				}
			});
			if(!animation.isBuffered()){
				_animations.remove(animation);
			}
		}
	}
	
	private void enemyJoin_Animation(){
		boolean isDead = false;
		_clearEnemyIcon = CLEAR_ENTITY_STATUS_HEALTH;
		Animation_I enemyUp = new EntityMoveAnimation(_enemyX, _enemyY, _enemyImage, isDead);
		animate(enemyUp);
		_clearEnemyIcon = NO_CLEAR;
	}
	
	private class GameListener extends GameAdapter {
		@Override
		public void onRoundEnded() {
			_animations.clear();
		}
	}
	
	private class PlayerListener implements EntityListener_I {
		
		@Override
		public void onHealthChanged(Entity entity, int oldValue, int newValue) {
			int playerX = 50 + EntityCardRenderer.WIDTH/6;
			int playerY = getPreferredSize().height - EntityCardRenderer.HEIGHT + EntityCardRenderer.HEIGHT/2 - 50;
			Animation_I animation = new HealthChangeAnimation(playerX, playerY, oldValue, newValue);
			animate(animation);
		}

		@Override
		public void onDied(Entity entity) {
			boolean isDead = true;
			Animation_I playerDown = new EntityMoveAnimation(_playerX, _playerY, _playerImage, isDead);
			_clearPlayerIcon = CLEAR_ENTITY_ICON;
			animate(playerDown);
//			repaint();
		}

		@Override
		public void onEnergyChanged(Entity entity, int oldValue, int newValue) {
			repaint();
		}

		@Override
		public void onBlockLevelChanged(Entity entity, int oldValue, int newValue) {
			repaint();
		}

		@Override
		public void onAttackPowerChanged(Entity entity, int oldValue, int newValue) {
			repaint();
		}

		@Override
		public void onBeforeActionExecuted(Entity entity, Action action) {
			Animation_I animation = _playerActionAnimationMapping.get(action.getClass());
			_playersAttackAction.setElementAttackActionImage(entity.getElement(), entity.getEnergy());
			_playersBlockAction.setElementAttackActionImage(entity.getElement(), entity.getEnergy());
			animate(animation);
		}

		@Override
		public void onAfterActionExecuted(Entity entity, Action action) {
			repaint();
		}
	}
	
	private class EnemyListener implements EntityListener_I {
		
		@Override
		public void onHealthChanged(Entity entity, int oldValue, int newValue) {
			int enemyX = getPreferredSize().width - EntityCardRenderer.WIDTH + EntityCardRenderer.WIDTH/6 - 50;
			int enemyY = 50 + EntityCardRenderer.HEIGHT/2;
			Animation_I animation = new HealthChangeAnimation(enemyX, enemyY, oldValue, newValue);
			animate(animation);
		}

		@Override
		public void onDied(Entity entity) {
			boolean isDead = true;
			Animation_I enemyDown = new EntityMoveAnimation(_enemyX, _enemyY, _enemyImage, isDead);
			_clearEnemyIcon = CLEAR_ENTITY_ICON;
			animate(enemyDown);
			isDead = false;
//			repaint();
		}

		@Override
		public void onEnergyChanged(Entity entity, int oldValue, int newValue) {
			repaint();
		}

		@Override
		public void onBlockLevelChanged(Entity entity, int oldValue, int newValue) {
			repaint();
		}

		@Override
		public void onAttackPowerChanged(Entity entity, int oldValue, int newValue) {
			repaint();
		}

		@Override
		public void onBeforeActionExecuted(Entity entity, Action action) {
			Animation_I animation = _enemyActionAnimationMapping.get(action.getClass());
			_enemysAttackAction.setElementAttackActionImage(entity.getElement(), entity.getEnergy());
			_enemysBlockAction.setElementAttackActionImage(entity.getElement(), entity.getEnergy());
			animate(animation);
		}

		@Override
		public void onAfterActionExecuted(Entity entity, Action action) {
			repaint();
		}
	}
}

