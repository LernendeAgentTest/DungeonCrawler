package org.haw.projekt.agenten.dungeonkoala.game.ui;

public interface GameUI_I {
	/**
	 * Registriert den übergebenen Listener an der Game-UI.
	 * @param listener Der zu registrierende Listener
	 */
	void addGameUIListener(GameUIListener_I listener);
	
	/**
	 * Entfernt den übergebenen Listener von der Game-UI.
	 * @param listener Der zu entfernende Listener
	 */
	void removeGameUIListener(GameUIListener_I listener);
	
	/**
	 * Gibt den Renderer der Game-UI zurück.
	 * @return Der Renderer
	 */
	Renderer_I getRenderer();
	
	/**
	 * Gibt die Kontrollelemente für den Spielablauf zurück.
	 * @return Die Kontrollelemente für den Spielablauf
	 */
	GameFlowControls_I getGameFlowControls();
	
	/**
	 * Sorgt dafür, dass angezeigt wird, wieviele Räume der Spieler insgesamt
	 * bestehen muss.
	 * @param max Die Anzahl der zu bestehenden Räume
	 */
	void setGameProgressMax(int max);
	
	/**
	 * Sorgt dafür, dass angezeigt wird, wie weit der Spieler im Spiel
	 * vorangeschritten ist (wieviele Räume er bereits abgeschlossen hat).
	 * @param cur Die Anzahl der abgeschlossenen Räume
	 */
	void setGameProgressCur(int cur);	
	
	/**
	 * Gibt den aktuell angezeigten Statustext auf der Game-UI zurück.
	 * @return Der aktuell angezeigte Statustext
	 */
	String getStatusText();
	
	/**
	 * Legt den anzuzeigenden Statustext auf der Game-UI fest.
	 * @param text Der anzuzeigende Statustext
	 */
	void setStatusText(String text);
	
	/**
	 * Prüft, ob die Benutzeroberfläche sichtbar ist oder nicht.
	 * @return {@code true} wenn die Benutzeroberfläche sichtbar ist, ansonsten {@code false}
	 */
	boolean isVisible();
	
	/**
	 * Legt fest, ob die Benutzeroberfläche sichtbar sein soll oder nicht.
	 * @param visible {@code true} wenn die Benutzeroberfläche sichtbar sein soll, ansonsten {@code false}
	 */
	void setVisible(boolean visible);
}
