package org.haw.projekt.agenten.dungeonkoala.game.ui;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.haw.projekt.agenten.dungeonkoala.game.GameSession;
import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.listener.EntityListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.entity.player.Player;
import org.haw.projekt.agenten.dungeonkoala.game.listener.GameListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

/**
 * Ein Controller der eine {@link GameSession} mit einer {@link GameUI_I Benutzeroberfläche}
 * und einem {@link EntityBehaviour_I PlayerBehaviour} kontrolliert.
 */
public class GameUIController implements GameListener_I, GameUIListener_I, GameFlowControlsListener_I {
	private Lock _lock;
	private Condition _continueCondition;
	
	private GameSession _game;
	private EntityBehaviour_I _playerBehaviour;
	
	private GameUI_I _ui;
	private Renderer_I _renderer;
	private GameFlowControls_I _flowControls;
	private EntityStatusUIUpdateListener _entityListener;
	
	public GameUIController(GameUI_I ui, EntityBehaviour_I playerBehaviour) {
		this(new GameSession(), ui, playerBehaviour);
	}
	
	public GameUIController(GameSession game, GameUI_I ui, EntityBehaviour_I playerBehaviour) {
		_lock = new ReentrantLock();
		_continueCondition = _lock.newCondition();
		_game = Objects.requireNonNull(game);
		
		_ui = Objects.requireNonNull(ui);
		_ui.addGameUIListener(this);
		_renderer = _ui.getRenderer();
		_flowControls = _ui.getGameFlowControls();
		_flowControls.addGameFlowControlsListener(this);
		_flowControls.setCurrentDuration(_renderer.getAnimationDuration());
		
		_playerBehaviour = Objects.requireNonNull(playerBehaviour);
		_entityListener = new EntityStatusUIUpdateListener();
	}
	
	public void start() {
		_ui.setVisible(true);
		_game.addGameListener(this);
		_game.addGameListener(_renderer.getGameListener());
		_game.start(_playerBehaviour);
	}
	
	public void stop() {
		_game.stop();
		_game.removeGameListener(this);
		_game.removeGameListener(_renderer.getGameListener());
		_ui.setVisible(false);
	}
	
	/**
	 * Ermöglicht das Registrieren eines {@link GameListener_I} an der GameSession.
	 * Der Listener wird über diverse Ereignisse im Spielablauf benachrichtigt
	 * @param listener Der zu registrierende Listener
	 */
	public void addGameListener(GameListener_I listener) {
		_game.addGameListener(listener);
	}
	
	/**
	 * Entfernt den übergebenen Listener von dieser GameSession.
	 * @param listener Der zu entfernende Listener
	 */
	public void removeGameListener(GameListener_I listener) {
		_game.removeGameListener(listener);
	}
	
	@Override
	public void onClose() {
		// Aus eventueller Blockierung holen, wenn der Benutzer den Pause-Button gedrückt hatte
		// TODO: Das funktioniert derzeit nicht, da eine ConcurrentModificationException geworfen wird.
		// Bisher ist leider nicht klar, was der Grund dafür ist.
//		onContinuePressed();
		
		stop();
	}
	
	@Override
	public void onDurationChanged(int durationMS) {
		_renderer.setAnimationDuration(durationMS);
	}
	
	@Override
	public void onPausePressed() {
		if(!_flowControls.isPausePressed()) {
			onContinuePressed();
		}
	}
	
	@Override
	public void onContinuePressed() {
		try {
			_lock.lock();
			_continueCondition.signal();
		} finally {
			_lock.unlock();
		}
	}
	
	@Override
	public void onGameStarted(Player player, int rooms) {
		player.addEntityListener(_entityListener);
		_renderer.setPlayer(player);
		_ui.setStatusText("Das Spiel hat begonnen!");
		_ui.setGameProgressMax(rooms);
		_ui.setGameProgressCur(0);
	}
	
	@Override
	public void onGameEnded(Player player, int rooms) {
		player.removeEntityListener(_entityListener);
		if(player.isAlive()) {
			_ui.setStatusText("Gewonnen!");
		} else {
			_ui.setStatusText("Game Over! Du hast es bis Raum " + rooms + " geschafft");
		}
	}
	
	@Override
	public void onRoomEntered(Room room) {
		room.getEnemy().addEntityListener(_entityListener);
		_renderer.setRoom(room);
	}
	
	@Override
	public void onRoomCleared(Room room) {
		_renderer.setRoom(null);
		room.getEnemy().removeEntityListener(_entityListener);
		_ui.setGameProgressCur(room.getNumber());
	}

	@Override
	public void onRoundStarted() {
		// Interessiert uns für die Benutzeroberfläche nicht
	}
	
	@Override
	public void onRoundEnded() {
		if(_flowControls.isPausePressed()) {
			try {
				_lock.lock();
				_flowControls.setContinueEnabled(true);
				_continueCondition.await();
			} catch(InterruptedException e) {
				
			} finally {
				_flowControls.setContinueEnabled(false);
				_lock.unlock();
			}
		}
	}
	
	private class EntityStatusUIUpdateListener implements EntityListener_I {
		@Override
		public void onHealthChanged(Entity entity, int oldValue, int newValue) {
			int difference = oldValue - newValue;
			_ui.setStatusText(entity.getName() + " hat " + (difference < 0 ? -difference : difference) + " Gesundheitspunkte " + (difference < 0 ? "erhalten" : "verloren"));
		}
		
		@Override
		public void onDied(Entity entity) {
			_ui.setStatusText(entity.getName() + " wurde besiegt!");
		}
		
		@Override
		public void onEnergyChanged(Entity entity, int oldValue, int newValue) {
			
		}
		
		@Override
		public void onBlockLevelChanged(Entity entity, int oldValue, int newValue) {
			
		}
		
		@Override
		public void onAttackPowerChanged(Entity entity, int oldValue, int newValue) {
			
		}
		
		@Override
		public void onBeforeActionExecuted(Entity entity, Action action) {
			_ui.setStatusText(entity.getName() + " führt " + action.toString() + " aus!");
		}
		
		@Override
		public void onAfterActionExecuted(Entity entity, Action action) {
			
		}
	}
}
