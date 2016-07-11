package org.haw.projekt.agenten.dungeonkoala.starter.ui.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.haw.projekt.agenten.dungeonkoala.game.entity.behaviour.EntityBehaviour_I;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.SwingGameUI;
import org.haw.projekt.agenten.dungeonkoala.starter.ui.StarterUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.starter.ui.StarterUI_I;

public class SwingStarterUI implements StarterUI_I {
	private StarterUIListener_I _listener;
	private SwingGameUI _gameUI;
	
	private JFrame _frame;
	private JComboBox<EntityBehaviourWrapper> _controller;
	private JButton _startButton;
	
	public SwingStarterUI() {
		_gameUI = new SwingGameUI();
		initComponents();
		initLayout();
		initListeners();
	}
	
	private void initComponents() {
		_frame = new JFrame("Dungeon Koala");
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_controller = new JComboBox<EntityBehaviourWrapper>();
		_startButton = new JButton("Start");
	}
	
	private void initLayout() {
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(_startButton);
		
		JPanel centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.PAGE_AXIS));
		centerPane.add(labelComponent(_controller, "Player-Interface:"));
		
		JPanel content = new JPanel();
		content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		content.setLayout(new BorderLayout());
		content.add(buttonPane, BorderLayout.PAGE_END);
		content.add(centerPane, BorderLayout.CENTER);
		
		_frame.setContentPane(content);
		_frame.setSize(800, 600);
		_frame.setLocationRelativeTo(null);
	}
	
	private void initListeners() {
		_startButton.addActionListener((event) -> {
			if(_listener != null) {
				// Der Listener muss hier in einem neuen Thread benachrichtigt werden, da wir uns
				// sonst im Event-Dispatch-Thread von Swing befinden.
				new Thread() {
					@Override
					public void run() {
						_listener.onStart(_gameUI, ((EntityBehaviourWrapper)_controller.getSelectedItem())._behaviour);
					}
				}.start();
			}
		});
	}
	
	private static JPanel labelComponent(Component component, String label) {
		JLabel jlabel = new JLabel(label);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(jlabel);
		panel.add(Box.createHorizontalStrut(5));
		panel.add(component);
		return panel;
	}
	
	@Override
	public void setPlayerInterfaces(List<EntityBehaviour_I> playerInterfaces) {
		DefaultComboBoxModel<EntityBehaviourWrapper> model = new DefaultComboBoxModel<EntityBehaviourWrapper>();
		model.addElement(new EntityBehaviourWrapper(_gameUI));
		for(EntityBehaviour_I playerInterface : playerInterfaces) {
			model.addElement(new EntityBehaviourWrapper(playerInterface));
		}
		_controller.setModel(model);
	}
	
	@Override
	public void show() {
		_frame.setVisible(true);
	}
	
	@Override
	public void hide() {
		_frame.setVisible(false);
	}
	
	@Override
	public void setListener(StarterUIListener_I l) {
		_listener = l;
	}

	@Override
	public StarterUIListener_I getListener() {
		return _listener;
	}
	
	/**
	 * Ein Wrapper, damit die Namen der {@link EntityBehaviour_I} in der ComboBox angezeigt werden.
	 */
	private class EntityBehaviourWrapper {
		private EntityBehaviour_I _behaviour;
		
		public EntityBehaviourWrapper(EntityBehaviour_I behaviour) {
			_behaviour = behaviour;
		}
		
		@Override
		public String toString() {
			return _behaviour != null ? _behaviour.getDisplayName() : "<null>";
		}
	}
}
