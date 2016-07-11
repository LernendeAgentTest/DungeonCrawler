package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.debugpanel;

public interface DebugPanelUI {
	/**
	 * Registriert einen Listener an dem Panel.
	 * @param listener Der zu registrierende Listener
	 */
	void addDebugPanelListener(DebugPanelUIListener_I listener);
	
	/**
	 * Entfernt den übergebenen Listener von dem Panel.
	 * @param listener Der zu entfernende Listener
	 */
	void removeDebugPanelListener(DebugPanelUIListener_I listener);
	
	/**
	 * Gibt zurück, ob die UI-Komponente für die Pause bei QWert-Änderung gedrückt ist.
	 * @return {@code true} wenn die Komponente gedrückt ist, ansonsten {@code false}
	 */
	boolean isPauseOnQValueChange();
	
	/**
	 * Legt fest, ob die UI-Komponente für die Pause bei QWert-Änderung gedrück ist.
	 * @param pause {@code true} wenn die Komponente gedrückt sein soll, ansonsten {@code false}
	 */
	void setPauseOnQValueChange(boolean pause);
	
	/**
	 * Gibt zurück, ob die UI-Komponente für die Pause bei Auswählen einer Aktion gedrückt ist.
	 * @return {@code true} wenn die Komponente gedrückt ist, ansonsten {@code false}
	 */
	boolean isPauseOnActionChoosen();
	
	/**
	 * Legt fest, ob die UI-Komponente für die Pause bei Auswählen einer Aktion gedrück ist.
	 * @param pause {@code true} wenn die Komponente gedrückt sein soll, ansonsten {@code false}
	 */
	void setPauseOnActionChoosen(boolean pause);
	
	/**
	 * Gibt zurück, ob der Button für das Fortfahren aktiviert (und damit anklickbar) ist.
	 * @return {@code true} wenn der Button aktiviert ist, ansonsten {@code false}
	 */
	boolean isResumeButtonEnabled();
	
	/**
	 * Legt fest, ob der Button für das Fortfahren aktiviert ist.
	 * @param enable {@code true} wenn der Button aktiviert sein soll, ansonsten {@code false}
	 */
	void setResumeButtonEnabled(boolean enable);
}
