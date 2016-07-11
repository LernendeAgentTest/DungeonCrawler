package org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation;

import java.awt.Graphics2D;

/**
 * Eine simple Schnittstelle für Animationen.
 */
public interface Animation_I {
	/**
	 * Führt diese Animation einmal aus.
	 * <p>Dieser Aufruf ist synchron gedacht, das heißt der aufrufende Thread blockiert bis
	 * die Methode (und damit die Animation) abgeschlossen ist.
	 * @param fps Die Anzahl der Frames pro Sekunde in der die Animation abgespielt werden soll
	 * @param durationMS Die Anzahl an Millisekunden, wielange die Animation dauern soll
	 * @param hook Ein Callback, das nach jedem Schritt der Animation aufgerufen wird um die Veränderung zu rendern 
	 */
	void animate(int fps, int durationMS, RepaintHook hook);
	
	/**
	 * Zeichnet die Animation in ihrem aktuellen Zustand.
	 * @param g Ein Graphics-Objekt zum Zeichnen
	 */
	void paint(Graphics2D g);
	
	@FunctionalInterface
	interface RepaintHook {
		void repaint();
	}
	
	boolean isBuffered();
}
