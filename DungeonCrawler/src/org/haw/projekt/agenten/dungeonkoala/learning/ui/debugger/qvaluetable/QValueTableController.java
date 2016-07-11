package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.FeatureSet_I;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTable;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTableListener_I;

public class QValueTableController implements QValueTableUIListener_I, QValueTableListener_I {
	private QValueTableUI_I _ui;
	private QValueTable _qValues;
	
	public QValueTableController(QValueTableUI_I ui) {
		_ui = Objects.requireNonNull(ui);
		_ui.addQValueTableListener(this);
	}
	
	public void setQValues(QValueTable qValues, FeatureSet_I featureSet, List<Class<? extends Action>> actionList) {
		if(_qValues != null) {
			_qValues.removeQValueTableListener(this);
		}
		_qValues = qValues;
		if(_qValues != null) {
			_qValues.addQValueTableListener(this);
		}
		_ui.setQValues(_qValues, featureSet, actionList);
	}
	
	@Override
	public void onSaveQValues(File file) {
		// TODO Speichern
		
	}
	
	@Override
	public void onLoadQValues(File file) {
		// TODO Laden
		
	}
	
	@Override
	public void onQValueChanged(int state, int action, double value) {
		_ui.selectCell(state, action);
	}

	@Override
	public void onNewStateReached(int reachedStates, int maxStates) {
		// Interessiert hier nicht
	}
}
