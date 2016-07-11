package org.haw.projekt.agenten.dungeonkoala.game.ui.swing.renderer.animation;

import java.util.concurrent.TimeUnit;

/**
 * Abstrakte Oberklasse für Animationen, die folgende typische Probleme abnimmt:
 * <ul>
 *   <li>Basis- / Ausgangskoordinaten von denen aus gezeichnet werden soll
 *   <li>Berechnung der benötigten Updates bei übergebenen FPS und Dauer (der Animation)
 *   <li>Aufrufen des {@link RepaintHook}s nach jeder Iteration
 *   <li>Schlafen im die gewünschten FPS und Dauer zu gewährleisten
 * </ul>
 * 
 * @param <T> Der Typ, der als Übergabe zwischen den Methoden {@link #setupAnimation(int)}
 *         und {@link #animateIteration(int, int, Object)} übergeben werden kann
 */
public abstract class AbstractAnimation<T> implements Animation_I {
	private int _baseX;
	private int _baseY;
	
	protected AbstractAnimation(int baseX, int baseY) {
		_baseX = baseX;
		_baseY = baseY;
	}
	
	@Override
	public synchronized final void animate(int fps, int durationMS, RepaintHook hook) {
		int updates = fps * durationMS / 1000;
		T values = setupAnimation(updates);
		for(int i=0; i<updates; i++) {
			animateIteration(i, updates, values);
			
			if(hook != null) {
				hook.repaint();
			}
			
			try {
				TimeUnit.MILLISECONDS.sleep((int)(1000.0/fps));
			} catch (InterruptedException e) {}
		}
	}
	
	/**
	 * Bereitet sich auf eine Animation vor. Hier können zum Beispiel Startwerte
	 * initialisiert werden. Außerdem bietet die Methode einen generischen
	 * Rückgabetypen, um eventuelle Parameter in die
	 * {@link #animateIteration(int, int, Object)}-Methode zu übergeben.
	 * @param updates Die Anzahl der berechneten Updates
	 * @return Ein Objekt mit Parametern die in {@link #animateIteration(int, int, Object)} benötigt werden
	 */
	protected abstract T setupAnimation(int updates);
	
	/**
	 * Berechnet den Zustand der animation für die aktuelle Iteration.
	 * @param iteration Die aktuelle Iteration
	 * @param ofIterations Die Anzahl der insgesamt zu berechnenden Iterationen
	 * @param values Die in {@link #setupAnimation(int)} vorbereiteten Übergabeparameter
	 */
	protected abstract void animateIteration(int iteration, int ofIterations, T values);
	
	/**
	 * Gibt die Ausgangskoordinate auf der X-Achse zurück.
	 * @return die Ausgangskoordinate auf der X-Achse
	 */
	protected int getBaseX() {
		return _baseX;
	}
	
	/**
	 * Gibt die Ausgangskoordinate auf der Y-Achse zurück.
	 * @return die Ausgangskoordinate auf der Y-Achse
	 */
	protected int getBaseY() {
		return _baseY;
	}
	
	@Override
	public boolean isBuffered() {
		return false;
	}
}
