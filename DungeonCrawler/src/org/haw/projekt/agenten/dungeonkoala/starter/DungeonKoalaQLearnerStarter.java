package org.haw.projekt.agenten.dungeonkoala.starter;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.QLearnerConfiguratorController;

public class DungeonKoalaQLearnerStarter {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
		
		new QLearnerConfiguratorController().start();
	}
}
