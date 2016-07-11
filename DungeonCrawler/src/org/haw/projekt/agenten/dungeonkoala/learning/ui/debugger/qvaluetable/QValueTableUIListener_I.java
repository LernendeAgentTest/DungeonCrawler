package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.qvaluetable;

import java.io.File;

/**
 * Listener für Events die auf dem {@link QValueTableUI_I QValueTable-UI-Element} auftreten können.
 */
public interface QValueTableUIListener_I {
	/**
	 * Wird aufgerufen, wenn der Speichern-Button gedrückt wurde.
	 * @param file Die Datei in die die QValues gespeichert werden sollen
	 */
	void onSaveQValues(File file);
	
	/**
	 * Wird aufgerufen, wenn der Laden-Button gedrückt wurde.
	 * @param file Die Datei aus der die QValues geladen werden sollen
	 */
	void onLoadQValues(File file);
}
