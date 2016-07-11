package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable;

import org.haw.projekt.agenten.dungeonkoala.game.entity.action.Action;
import org.haw.projekt.agenten.dungeonkoala.learning.feature.FeatureSet_I;
import org.haw.projekt.agenten.dungeonkoala.learning.qvaluetable.QValueTable;

import java.util.List;

/**
 * Schnittstelle für das QValueTable-UI-Element im QLearner Debugger.
 */
public interface QValueTableUI_I {
	/**
	 * Fügt den übergebenen Listener als Listener an dem UI-Element hinzu.
	 * @param listener Der hinzuzufügende Listener
	 */
	void addQValueTableListener(QValueTableUIListener_I listener);
	
	/**
	 * Entfernt den übergebenen Listener als Listener an dem UI-Element.
	 * @param listener Der zu entfernende Listener
	 */
	void removeQValueTableListener(QValueTableUIListener_I listener);
	
	/**
	 * Setzt die übergebenen QValues.
	 * @param qvalues Die anzuzeigenden QValues
	 */
	void setQValues(QValueTable qvalues, FeatureSet_I featureSet, List<Class<? extends Action>> actionList);
	
	/**
	 * Wählt die Zelle, die sich an dem Schnittpunkt der übergebenen Zeile und Spalte befindet.
	 * @param row Die Zeile
	 * @param column Die Spalte
	 */
	void selectCell(int row, int column);
	
	/**
	 * Legt fest, ob das UI-Element für den Benutzer bearbeitbar ist.
	 * @param editable Ob das UI-Element bearbeitbar sein soll
	 */
	void setEditable(boolean editable);
	
	/**
	 * Gibt zurück, ob das UI-Element für den Benutzer bearbeitbar ist.
	 * @return {@code true} wenn das Element bearbeitbar ist, ansonsten {@code false}
	 */
	boolean isEditable();
}
