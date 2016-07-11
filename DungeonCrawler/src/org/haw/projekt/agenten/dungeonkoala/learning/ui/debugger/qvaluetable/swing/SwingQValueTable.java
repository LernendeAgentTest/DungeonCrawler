package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.FeatureSet_I;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTable;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable.QValueTableUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable.QValueTableUI_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

public class SwingQValueTable implements QValueTableUI_I {
	private EventDispatcher<QValueTableUIListener_I> _eventDispatcher;
	
	private JPanel _content;
	private JTable _qValueTable;
	private JScrollPane _qValueScrollPane;
	private RowHeaderTable _qValueTableRowHeader;
	
	private JPopupMenu _popup;
	private JMenuItem _saveButton;
	private JMenuItem _loadButton;
	
	private JFileChooser _fileChooser;
	
	public SwingQValueTable() {
		initComponents();
		initLayout();
		initListeners();
	}
	
	private void initComponents() {
		_content = new JPanel();
		_qValueTable = new JTable();
		_qValueTableRowHeader = new RowHeaderTable(_qValueTable);
		
		_popup = new JPopupMenu();
		_saveButton = new JMenuItem("Q-Werte speichern...", new ImageIcon(getClass().getClassLoader().getResource("img/ui/file_save.gif")));
		_loadButton = new JMenuItem("Q-Werte laden...", new ImageIcon(getClass().getClassLoader().getResource("img/ui/file_open.gif")));
		
		_fileChooser = new JFileChooser();
	}
	
	private void initLayout() {
//		_qValueTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		_qValueTable.setCellSelectionEnabled(true);
		
		_qValueScrollPane = new JScrollPane(_qValueTable);
		_qValueScrollPane.setRowHeaderView(_qValueTableRowHeader);
		_qValueScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, _qValueTableRowHeader.getTableHeader());
		
		_content.setLayout(new BoxLayout(_content, BoxLayout.PAGE_AXIS));
		_content.add(_qValueScrollPane);
		
		_popup.add(_saveButton);
		_popup.add(_loadButton);
	}
	
	private void initListeners() {
		_eventDispatcher = new EventDispatcher<QValueTableUIListener_I>();
		_qValueTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					_popup.show(_qValueTable, e.getX(), e.getY());
				}
			}
		});
		_saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onSave();
			}
		});
		_loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onLoad();
			}
		});
	}
	
	@Override
	public void addQValueTableListener(QValueTableUIListener_I listener) {
		_eventDispatcher.addListener(listener);
	}
	
	@Override
	public void removeQValueTableListener(QValueTableUIListener_I listener) {
		_eventDispatcher.removeListener(listener);
	}
	
	@Override
	public void setQValues(QValueTable qValues, FeatureSet_I featureSet, List<Class<? extends Action>> actionList) {
		_qValueTable.setModel(new QValueTableModel(qValues, featureSet, actionList));
		_qValueTable.repaint();
	}
	
	@Override
	public void selectCell(int row, int column) {
		_qValueTable.setRowSelectionInterval(row, row);
		_qValueTable.setColumnSelectionInterval(column, column);
	}

	@Override
	public void setEditable(boolean editable) {
		_saveButton.setEnabled(editable);
		_loadButton.setEnabled(editable);
	}
	
	@Override
	public boolean isEditable() {
		return _saveButton.isEnabled() && _loadButton.isEnabled();
	}
	
	public Component getComponent() {
		return _content;
	}
	
	private void onSave() {
		_fileChooser.showSaveDialog(_qValueTable);
		File file = _fileChooser.getSelectedFile();
		_eventDispatcher.fireEvent(l -> l.onSaveQValues(file));
	}
	
	private void onLoad() {
		_fileChooser.showOpenDialog(_qValueTable);
		File file = _fileChooser.getSelectedFile();
		_eventDispatcher.fireEvent(l -> l.onLoadQValues(file));
	}
}
