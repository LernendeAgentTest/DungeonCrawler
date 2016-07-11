package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.debugpanel.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.debugpanel.DebugPanelUI;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.debugpanel.DebugPanelUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

public class SwingDebugPanel implements DebugPanelUI {
	private EventDispatcher<DebugPanelUIListener_I> _dispatcher;
	
	private JPanel _content;
	private JCheckBox _pauseOnQValueChange;
	private JCheckBox _pauseOnActionChoosen;
	private JButton _resumeButton;
	
	public SwingDebugPanel() {
		initComponents();
		initLayout();
		initListeners();
	}
	
	private void initComponents() {
		_content = new JPanel();
		_pauseOnQValueChange = new JCheckBox("Pausieren bei QValue-Änderung");
		_pauseOnActionChoosen = new JCheckBox("Pausieren bei Aktion wählen");
		_resumeButton = new JButton(new ImageIcon(getClass().getClassLoader().getResource("img/ui/execute.gif")));
		_resumeButton.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("img/ui/execute_rollover.gif")));
		_resumeButton.setPressedIcon(new ImageIcon(getClass().getClassLoader().getResource("img/ui/execute_pressed.gif")));
		_resumeButton.setToolTipText("Fortsetzen");
		_resumeButton.setEnabled(false);
	}
	
	private void initLayout() {
		_resumeButton.setBorder(null);
		_resumeButton.setContentAreaFilled(false);
		
		JPanel buttonPanel = new  JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(_resumeButton);
		buttonPanel.add(Box.createHorizontalGlue());
		
		_pauseOnQValueChange.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		_pauseOnActionChoosen.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		buttonPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		
		_content.setLayout(new BoxLayout(_content, BoxLayout.PAGE_AXIS));
		_content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		_content.add(_pauseOnQValueChange);
		_content.add(_pauseOnActionChoosen);
		_content.add(Box.createVerticalStrut(5));
		_content.add(buttonPanel);
	}
	
	private void initListeners() {
		_dispatcher = new EventDispatcher<DebugPanelUIListener_I>();
		_resumeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_dispatcher.fireEvent(l -> l.onResumePressed());
			}
		});
	}
	
	@Override
	public void addDebugPanelListener(DebugPanelUIListener_I listener) {
		_dispatcher.addListener(listener);
	}
	
	@Override
	public void removeDebugPanelListener(DebugPanelUIListener_I listener) {
		_dispatcher.removeListener(listener);
	}
	
	@Override
	public boolean isPauseOnQValueChange() {
		return _pauseOnQValueChange.isSelected();
	}
	
	@Override
	public void setPauseOnQValueChange(boolean pause) {
		_pauseOnQValueChange.setSelected(pause);
	}
	
	@Override
	public boolean isPauseOnActionChoosen() {
		return _pauseOnActionChoosen.isSelected();
	}
	
	@Override
	public void setPauseOnActionChoosen(boolean pause) {
		_pauseOnActionChoosen.setSelected(pause);
	}
	
	@Override
	public boolean isResumeButtonEnabled() {
		return _resumeButton.isEnabled();
	}
	
	@Override
	public void setResumeButtonEnabled(boolean enable) {
		_resumeButton.setEnabled(enable);
	}
	
	public JComponent getComponent() {
		return _content;
	}
}
