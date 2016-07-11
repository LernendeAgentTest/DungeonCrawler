package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable.swing;

import java.util.List;
import java.util.Objects;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.FeatureSet_I;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTable;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTableListener_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;

public class QValueTableModel implements TableModel, QValueTableListener_I {
	private EventDispatcher<TableModelListener> _listeners;
	private QValueTable _qValues;
	private FeatureSet_I _featureSet;
	private List<Class<? extends Action>> _actionList;
	
	public QValueTableModel(QValueTable qValues, FeatureSet_I featureSet, List<Class<? extends Action>> actionList) {
		_listeners = new EventDispatcher<TableModelListener>();
		_qValues = Objects.requireNonNull(qValues);
		_qValues.addQValueTableListener(this);
		_featureSet = Objects.requireNonNull(featureSet);
		_actionList = Objects.requireNonNull(actionList);
	}
	
	@Override
	public int getRowCount() {
		return _qValues.getStateCount();
	}
	
	@Override
	public int getColumnCount() {
		return _qValues.getActionCount();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return _qValues.getQValue(rowIndex, columnIndex);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Double value = Double.valueOf((String)aValue); // TODO could throw custom exception here
		_qValues.setQValue(rowIndex, columnIndex, value);
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return _actionList.get(columnIndex).getSimpleName();
	}
	
	public String getRowName(int rowIndex) {
		return _featureSet.getDescription(rowIndex);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Double.class;
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		_listeners.addListener(l);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		_listeners.removeListener(l);
	}

	@Override
	public void onQValueChanged(int state, int action, double value) {
		_listeners.fireEvent(l -> l.tableChanged(new TableModelEvent(this, state, state, action, TableModelEvent.UPDATE)));
	}

	@Override
	public void onNewStateReached(int reachedStates, int maxStates) {
		// Interessiert hier nicht
	}
}
