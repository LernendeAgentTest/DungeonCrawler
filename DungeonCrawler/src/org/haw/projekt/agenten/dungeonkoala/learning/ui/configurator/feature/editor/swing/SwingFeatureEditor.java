package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.feature.editor.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.haw.projekt.agenten.dungeonkoala.learning.feature.features.AbstractDungeonKoalaFeature;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.feature.editor.FeatureEditorUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.feature.editor.FeatureEditorUI_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

public class SwingFeatureEditor implements FeatureEditorUI_I {
	private static final String ICON_ADD = "img/ui/plus.png";
	private static final String ICON_REMOVE = "img/ui/minus.png";
	
	private ImageIcon _iconAdd;
	private ImageIcon _iconRemove;
	
	private EventDispatcher<FeatureEditorUIListener_I> _dispatcher = new EventDispatcher<>();
	
	private JSplitPane _content;
	private JLabel _activeFeaturesStateCount;
	private JList<AbstractDungeonKoalaFeature> _activeFeaturesList;
	private JList<AbstractDungeonKoalaFeature> _inactiveFeaturesList;
	private JButton _addButton;
	private JButton _removeButton;
	
	public SwingFeatureEditor() {
		initComponents();
		initLayout();
		initListeners();
	}
	
	private void initComponents() {
		_iconAdd = new ImageIcon(getClass().getClassLoader().getResource(ICON_ADD));
		_iconRemove = new ImageIcon(getClass().getClassLoader().getResource(ICON_REMOVE));
		
		_content = new JSplitPane();
		_activeFeaturesStateCount = new JLabel();
		_activeFeaturesList = new JList<AbstractDungeonKoalaFeature>();
		_activeFeaturesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		_activeFeaturesList.setCellRenderer(new FeatureListCellRenderer());
		_inactiveFeaturesList = new JList<AbstractDungeonKoalaFeature>();
		_inactiveFeaturesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		_inactiveFeaturesList.setCellRenderer(new FeatureListCellRenderer());
		
		_addButton = new JButton(_iconAdd);
		_addButton.setToolTipText("Add Feature...");
		_addButton.setEnabled(false);
		_removeButton = new JButton(_iconRemove);
		_removeButton.setToolTipText("Remove Feature");
		_removeButton.setEnabled(false);
	}
	
	private void initLayout() {
		_addButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		_removeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		_activeFeaturesStateCount.setForeground(Color.GRAY);
		
		JPanel leftTableHeader = new JPanel();
		leftTableHeader.setLayout(new BoxLayout(leftTableHeader, BoxLayout.LINE_AXIS));
		leftTableHeader.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
		leftTableHeader.add(new JLabel("Aktiv"));
		leftTableHeader.add(Box.createHorizontalStrut(5));
		leftTableHeader.add(_activeFeaturesStateCount);
		leftTableHeader.add(Box.createHorizontalGlue());
		leftTableHeader.add(_removeButton);
		
		JPanel leftTablePanel = new JPanel();
		leftTablePanel.setLayout(new BoxLayout(leftTablePanel, BoxLayout.PAGE_AXIS));
		leftTablePanel.add(leftTableHeader);
		leftTablePanel.add(new JScrollPane(_activeFeaturesList));
		
		JPanel rightTableHeader = new JPanel();
		rightTableHeader.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
		rightTableHeader.setLayout(new BoxLayout(rightTableHeader, BoxLayout.LINE_AXIS));
		rightTableHeader.add(new JLabel("Inaktiv"));
		rightTableHeader.add(Box.createHorizontalGlue());
		rightTableHeader.add(_addButton);
		
		JPanel rightTablePanel = new JPanel();
		rightTablePanel.setLayout(new BoxLayout(rightTablePanel, BoxLayout.PAGE_AXIS));
		rightTablePanel.add(rightTableHeader);
		rightTablePanel.add(new JScrollPane(_inactiveFeaturesList));
		
		_content.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		_content.setContinuousLayout(true);
		_content.setDividerLocation(0.5);
		_content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		_content.setLeftComponent(leftTablePanel);
		_content.setRightComponent(rightTablePanel);
		
		_content.setAlignmentX(JComponent.CENTER_ALIGNMENT);
	}
	
	private void initListeners() {
		_dispatcher = new EventDispatcher<FeatureEditorUIListener_I>();
		_activeFeaturesList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				_removeButton.setEnabled(!getSelectedActiveFeatures().isEmpty());
			}
		});
		_inactiveFeaturesList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				_addButton.setEnabled(!getSelectedInactiveFeatures().isEmpty());
			}
		});
		_activeFeaturesList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(KeyEvent.VK_DELETE == e.getKeyCode()) {
					onRemove();
				}
			}
		});
		_removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onRemove();
			}
		});
		_inactiveFeaturesList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(KeyEvent.VK_ENTER == e.getKeyCode()) {
					onAdd();
				}
			}
		});
		_addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAdd();
			}
		});
	}
	
	@Override
	public void addFeatureEditorUIListener(FeatureEditorUIListener_I l) {
		_dispatcher.addListener(l);
	}

	@Override
	public void removeFeatureEditorUIListener(FeatureEditorUIListener_I l) {
		_dispatcher.removeListener(l);
	}
	
	@Override
	public void setActiveFeatures(List<AbstractDungeonKoalaFeature> features) {
		DefaultListModel<AbstractDungeonKoalaFeature> model = new DefaultListModel<AbstractDungeonKoalaFeature>();
		int possibleStates = 1;
		for(AbstractDungeonKoalaFeature feature : features) {
			model.addElement(feature);
			possibleStates *= feature.getPossibleAllocations();
		}
		_activeFeaturesList.setModel(model);
		_activeFeaturesStateCount.setText("(" + String.valueOf(possibleStates) + " States)");
	}
	
	@Override
	public List<AbstractDungeonKoalaFeature> getSelectedActiveFeatures() {
		return _activeFeaturesList.getSelectedValuesList();
	}
	
	@Override
	public void setInactiveFeatures(List<AbstractDungeonKoalaFeature> features) {
		DefaultListModel<AbstractDungeonKoalaFeature> model = new DefaultListModel<AbstractDungeonKoalaFeature>();
		for(AbstractDungeonKoalaFeature feature : features) {
			model.addElement(feature);
		}
		_inactiveFeaturesList.setModel(model);
	}
	
	@Override
	public List<AbstractDungeonKoalaFeature> getSelectedInactiveFeatures() {
		return _inactiveFeaturesList.getSelectedValuesList();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		_addButton.setEnabled(enabled);
		_removeButton.setEnabled(enabled);
	}
	
	public Component getComponent() {
		return _content;
	}
	
	private void onAdd() {
		_dispatcher.fireEvent(l -> l.onAddFeatures(getSelectedInactiveFeatures()));
	}
	
	private void onRemove() {
		_dispatcher.fireEvent(l -> l.onRemoveFeatures(getSelectedActiveFeatures()));
	}
}
