package org.haw.projekt.agenten.dungeonkoala.starter;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.haw.projekt.agenten.dungeonkoala.game.ui.GameUIController;
import org.haw.projekt.agenten.dungeonkoala.game.ui.swing.SwingGameUI;

public class DungeonKoalaHumanStarter {
	/**
	 * Startet direkt ein Spiel, sodass ein Mensch es spielen kann.
	 */
	public static void main(String... args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
		
		SwingGameUI ui = new SwingGameUI();
		GameUIController gameController = new GameUIController(ui, ui);
		gameController.start();
	}
}
