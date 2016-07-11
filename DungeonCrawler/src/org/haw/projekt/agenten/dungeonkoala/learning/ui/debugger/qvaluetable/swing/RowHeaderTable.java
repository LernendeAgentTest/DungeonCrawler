package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable.swing;

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * Use a JTable as a renderer for row numbers of a given main table.
 * This table must be added to the row header of the scrollpane that
 * contains the main table.
 * 
 * @author  Rob Camick, https://tips4java.wordpress.com/2008/11/18/row-number-table/
 */
@SuppressWarnings("serial")
public class RowHeaderTable extends JTable implements ChangeListener, PropertyChangeListener, TableModelListener {
	private JTable main;
	
	public RowHeaderTable(JTable table) {
		main = table;
		main.addPropertyChangeListener(this);
		main.getModel().addTableModelListener(this);
		
		setFocusable(false);
		setAutoCreateColumnsFromModel(false);
		setSelectionModel(main.getSelectionModel());
		
		TableColumn stateColumn = new TableColumn();
		stateColumn.setHeaderValue("State");
		stateColumn.setCellRenderer(new RowNumberRenderer());
		stateColumn.setPreferredWidth(40);
		addColumn(stateColumn);
		
		TableColumn featuresColumn = new TableColumn();
		featuresColumn.setHeaderValue("Features");
		featuresColumn.setCellRenderer(new RowNumberRenderer());
		featuresColumn.setPreferredWidth(60);
		addColumn(featuresColumn);
		
		setPreferredScrollableViewportSize(getPreferredSize());
	}

	@Override
	public void addNotify() {
		super.addNotify();

		//  Keep scrolling of the row table in sync with the main table.
		Component c = getParent();
		if (c instanceof JViewport) {
			JViewport viewport = (JViewport)c;
			viewport.addChangeListener(this);
		}
	}

	/**
	 * Delegate method to main table
	 */
	@Override
	public int getRowCount() {
		return main.getRowCount();
	}

	@Override
	public int getRowHeight(int row) {
		int rowHeight = main.getRowHeight(row);

		if (rowHeight != super.getRowHeight(row)) {
			super.setRowHeight(row, rowHeight);
		}

		return rowHeight;
	}

	/**
	 * No model is being used for this table so just use the row number
	 * as the value of the cell.
	 */
	@Override
	public Object getValueAt(int row, int column) {
		if(column == 1) {
			TableModel model = main.getModel();
			if(model instanceof QValueTableModel) {
				QValueTableModel qmodel = (QValueTableModel)model;
				return qmodel.getRowName(row);
			}
		}
		return Integer.toString(row);
	}

	/**
	 * Don't edit data in the main TableModel by mistake
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	/**
	 * Do nothing since the table ignores the model
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {}

	/**
	 * Implement {@link ChangeListener}.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		//  Keep the scrolling of the row table in sync with main table
		JViewport viewport = (JViewport)e.getSource();
		JScrollPane scrollPane = (JScrollPane)viewport.getParent();
		scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
	}
	
	/**
	 * Implement {@link PropertyChangeListener}
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		//  Keep the row table in sync with the main table

		if ("selectionModel".equals(e.getPropertyName())) {
			setSelectionModel( main.getSelectionModel() );
		}

		if ("rowHeight".equals(e.getPropertyName())) {
			repaint();
		}

		if ("model".equals(e.getPropertyName())) {
			main.getModel().addTableModelListener( this );
			revalidate();
		}
	}

	/**
	 * Implement {@link TableModelListener}
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		revalidate();
	}

	/**
	 * Attempt to mimic the table header renderer
	 */
	private static class RowNumberRenderer extends DefaultTableCellRenderer {
		public RowNumberRenderer() {
			setHorizontalAlignment(JLabel.CENTER);
		}
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if(table != null) {
				JTableHeader header = table.getTableHeader();
				
				if(header != null) {
					setForeground(header.getForeground());
					setBackground(header.getBackground());
					setFont(header.getFont());
				}
			}
			
			if(isSelected) {
				setFont(getFont().deriveFont(Font.BOLD));
			}
			
			setText((value == null) ? "" : value.toString());
			setToolTipText((value == null) ? "" : value.toString());
			
			return this;
		}
	}
}
