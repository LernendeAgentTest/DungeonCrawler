package org.haw.projekt.agenten.dungeonkoala.game.ui.swing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.haw.projekt.agenten.dungeonkoala.game.entity.Entity;
import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;
import org.haw.projekt.agenten.dungeonkoala.game.ui.GameFlowControls_I;
import org.haw.projekt.agenten.dungeonkoala.game.ui.GameUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.ui.GameUI_I;
import org.haw.projekt.agenten.dungeonkoala.game.ui.Renderer_I;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.SwingGameRenderer;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;
import org.haw.projekt.agenten.dungeonkoala.util.swing.components.separator.NonSpaceEatingSeparator;

/**
 * Die Benutzeroberfläche für das Spiel.
 */
public class SwingGameUI implements GameUI_I, EntityBehaviour_I {
	private EventDispatcher<GameUIListener_I> _dispatcher;
	
	private JFrame _frame;
	private JProgressBar _gameProgress;
	private SwingGameRenderer _renderer;
	private JPanel _actionsPanel;
	private JLabel _statusLabel;
	private SwingFlowControls _flowControls;
	
	public SwingGameUI() {
		initComponents();
		initLayout();
		initListeners();
	}
	
	/**
	 * Erzeugt die GUI-Komponenten, die von der Benutzeroberfläche genutzt
	 * werden.
	 */
	private void initComponents() {
		_frame = new JFrame("Dungeon Koala");
		_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_gameProgress = new JProgressBar();
		_gameProgress.setStringPainted(true);
		_renderer = new SwingGameRenderer();
		_statusLabel = new JLabel();
		_flowControls = new SwingFlowControls();
		_actionsPanel = new JPanel();
	}
	
	/**
	 * Kümmert sich um das Layout der Benutzeroberfläche.
	 */
	private void initLayout() {
		_actionsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		_actionsPanel.setPreferredSize(new Dimension(_renderer.getPreferredSize().width, 100));
		
		JPanel labels = new JPanel();
		labels.setLayout(new BoxLayout(labels, BoxLayout.LINE_AXIS));
		labels.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		labels.add(_statusLabel);
		labels.add(Box.createHorizontalGlue());
		labels.add(_flowControls.getDurationLabel());
		labels.add(Box.createRigidArea(new Dimension(5, 0)));
		labels.add(_flowControls.getPauseButton());
		labels.add(Box.createRigidArea(new Dimension(5, 0)));
		labels.add(_flowControls.getContinueButton());
		
		JPanel flowControlsPanel = new JPanel();
		flowControlsPanel.setLayout(new BoxLayout(flowControlsPanel, BoxLayout.PAGE_AXIS));
		flowControlsPanel.add(labels);
		flowControlsPanel.add(_flowControls.getSleepSlider());

		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		content.add(_gameProgress);
		content.add(_renderer);
		content.add(_actionsPanel);
		content.add(new NonSpaceEatingSeparator());
		content.add(flowControlsPanel);
		
		_frame.setContentPane(content);
		_frame.pack();
		_frame.setLocationRelativeTo(null);
	}
	
	private void initListeners() {
		_dispatcher = new EventDispatcher<>();
		_frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				_dispatcher.fireEvent(l -> l.onClose());
				
				// Auswahl-Blockierung beenden
				synchronized(SwingGameUI.this) {
					SwingGameUI.this.notify();
				}
			}
		});
	}
	
	@Override
	public void addGameUIListener(GameUIListener_I listener) {
		_dispatcher.addListener(listener);
	}
	
	@Override
	public void removeGameUIListener(GameUIListener_I listener) {
		_dispatcher.addListener(listener);
	}
	
	@Override
	public Renderer_I getRenderer() {
		return _renderer;
	}
	
	@Override
	public GameFlowControls_I getGameFlowControls() {
		return _flowControls;
	}
	
	@Override
	public String getStatusText() {
		return _statusLabel.getText();
	}
	
	@Override
	public void setStatusText(String text) {
		_statusLabel.setText(text);
	}
	
	@Override
	public boolean isVisible() {
		return _frame.isVisible();
	}
	
	@Override
	public void setVisible(boolean visible) {
		_frame.setVisible(visible);
	}
	
	@Override
	public void setGameProgressMax(int max) {
		_gameProgress.setMaximum(max);
	}
	
	@Override
	public void setGameProgressCur(int cur) {
		_gameProgress.setValue(cur);
		_gameProgress.setString("Raum " + cur + " / " + _gameProgress.getMaximum());
	}
	
	private Action _selectedAction;

	@Override
	public Action makeNextMove(Entity me, Entity opponent, List<Action> actions, Room room) {
		_statusLabel.setText("Erwarte Benutzeraktion");
		
		synchronized(this) {
			// Buttons für die möglichen Benutzeraktionen bauen
			for(Action action : actions) {
				JButton actionButton = new JButton(action.toString());
				actionButton.setAction(new ActionSelectionAction(action));
				_actionsPanel.add(actionButton);
			}
			
			// Buttons anzeigen
			_actionsPanel.validate();
			
			// Darauf warten, dass die gewünschte Benutzeraktion ausgewählt wurde
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		
		// Buttons für die möglichen Benutzeraktionen entfernen, sodass sie
		// nicht gedrückt werden können, wenn garkeine Aktion erwünscht ist
		_actionsPanel.removeAll();
		_actionsPanel.repaint();
		
		// Ausgewählte Benutzeraktion zurückgeben
		return _selectedAction;
	}
	
	@Override
	public String getDisplayName() {
		return "Swing Game UI";
	}

	@SuppressWarnings("serial")
	private class ActionSelectionAction extends AbstractAction {
		private Action _action;

		public ActionSelectionAction(Action action) {
			super(action.toString());
			_action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// Die (Swing-)Action wird vom Swing Event-Thread aufgerufen, wenn
			// ihr zugehöriger Button gedrückt wird. Das ist primär der Grund,
			// warum hier Thread-Synchronisation eingesetzt wird (wait / notify).
			// Während das Spiel (Hauptthread) stoppt und wartet, reaktiviert der
			// Swing Event-Thread durch das Auswählen der Benutzer-Aktion den
			// Hauptthread per Notify.
			synchronized (SwingGameUI.this) {
				_selectedAction = _action;
				SwingGameUI.this.notify();
			}
		}
	}
}
