package org.haw.projekt.agenten.dungeonkoala.game.ui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.haw.projekt.agenten.dungeonkoala.game.ui.GameFlowControlsListener_I;
import org.haw.projekt.agenten.dungeonkoala.game.ui.GameFlowControls_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

public class SwingFlowControls implements GameFlowControls_I {
	private static final int DEFAULT_DURATION_MILLIS = 100;
	
	private EventDispatcher<GameFlowControlsListener_I> _dispatcher;
	
	private JLabel _durationLabel;
	private JSlider _durationSlider;
	private JToggleButton _pauseButton;
	private JButton _continueButton;
	
	public SwingFlowControls() {
		initComponents();
		initLayout();
		initListeners();
	}
	
	private void initComponents() {
		_durationLabel = new JLabel(DEFAULT_DURATION_MILLIS + "ms");
		_durationSlider = new JSlider(JSlider.HORIZONTAL, 0, 2000, DEFAULT_DURATION_MILLIS);
		
		_pauseButton = new JToggleButton(new ImageIcon(getClass().getClassLoader().getResource("img/ui/pause.gif")));
		_pauseButton.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("img/ui/pause_rollover.gif")));
		_pauseButton.setSelectedIcon(new ImageIcon(getClass().getClassLoader().getResource("img/ui/pause_pressed.gif")));
		_pauseButton.setToolTipText("Nach jeder Runde pausieren (an/aus)");
		
		_continueButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("img/ui/execute.gif")));
		_continueButton.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("img/ui/execute_rollover.gif")));
		_continueButton.setPressedIcon(new ImageIcon(getClass().getClassLoader().getResource("img/ui/execute_pressed.gif")));
		_continueButton.setToolTipText("Fortsetzen");
		_continueButton.setEnabled(false);
	}
	
	private void initLayout() {
		_durationSlider.setMajorTickSpacing(100);
		_durationSlider.setPaintTicks(true);
		_durationSlider.setSnapToTicks(true);
		
		_pauseButton.setBorder(null);
		_pauseButton.setContentAreaFilled(false);
		
		_continueButton.setBorder(null);
		_continueButton.setContentAreaFilled(false);
	}
	
	@SuppressWarnings("serial")
	private void initListeners() {
		_dispatcher = new EventDispatcher<>();
		_durationSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int durationMS = getCurrentDuration();
				_durationLabel.setText(durationMS + "ms");
				if(!_durationSlider.getValueIsAdjusting()) {
					_dispatcher.fireEvent(l -> l.onDurationChanged(durationMS));
				}
			}
		});
		_pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_dispatcher.fireEvent(l -> l.onPausePressed());
			}
		});
		_continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_dispatcher.fireEvent(l -> l.onContinuePressed());
			}
		});
		setShortcutAction(_durationSlider, JComponent.WHEN_IN_FOCUSED_WINDOW, KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.CTRL_DOWN_MASK), "Scroll-Left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_durationSlider.setValue(_durationSlider.getValue() - _durationSlider.getMajorTickSpacing());
			}
		});
		setShortcutAction(_durationSlider, JComponent.WHEN_IN_FOCUSED_WINDOW, KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.CTRL_DOWN_MASK), "Scroll-Right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_durationSlider.setValue(_durationSlider.getValue() + _durationSlider.getMajorTickSpacing());
			}
		});
		setShortcutAction(_pauseButton, JComponent.WHEN_IN_FOCUSED_WINDOW, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.CTRL_DOWN_MASK), "Pause", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_pauseButton.setSelected(!_pauseButton.isSelected());
				_dispatcher.fireEvent(l -> l.onPausePressed());
			}
		});
		setShortcutAction(_continueButton, JComponent.WHEN_IN_FOCUSED_WINDOW, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK), "Continue", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_dispatcher.fireEvent(l -> l.onContinuePressed());
			}
		});
	}
	
	private static void setShortcutAction(JComponent component, int condition, KeyStroke keyStroke, String actionKey, Action action) {
		component.getInputMap(condition).put(keyStroke, actionKey);
		component.getActionMap().put(actionKey, action);
	}
	
	@Override
	public void addGameFlowControlsListener(GameFlowControlsListener_I listener) {
		_dispatcher.addListener(listener);
	}
	
	@Override
	public void removeGameFlowControlsListener(GameFlowControlsListener_I listener) {
		_dispatcher.removeListener(listener);
	}
	
	@Override
	public int getCurrentDuration() {
		return _durationSlider.getValue();
	}
	
	@Override
	public void setCurrentDuration(int durationMS) {
		_durationSlider.setValue(durationMS);
	}
	
	@Override
	public boolean isPausePressed() {
		return _pauseButton.isSelected();
	}
	
	@Override
	public void setPausePressed(boolean pause) {
		_pauseButton.setSelected(pause);
	}
	
	@Override
	public boolean isDurationSliderEnabled() {
		return _durationSlider.isEnabled();
	}
	
	@Override
	public void setDurationSliderEnabled(boolean enable) {
		_durationSlider.setEnabled(enable);
	}
	
	@Override
	public boolean isPauseEnabled() {
		return _pauseButton.isEnabled();
	}
	
	@Override
	public void setPauseEnabled(boolean enable) {
		_pauseButton.setEnabled(enable);
	}
	
	@Override
	public boolean isContinueEnabled() {
		return _continueButton.isEnabled();
	}
	
	@Override
	public void setContinueEnabled(boolean enable) {
		_continueButton.setEnabled(enable);
	}
	
	public JLabel getDurationLabel() {
		return _durationLabel;
	}
	
	public JSlider getSleepSlider() {
		return _durationSlider;
	}
	
	public JToggleButton getPauseButton() {
		return _pauseButton;
	}
	
	public JButton getContinueButton() {
		return _continueButton;
	}
}
