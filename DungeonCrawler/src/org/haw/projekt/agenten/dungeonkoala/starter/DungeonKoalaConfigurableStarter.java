package org.haw.projekt.agenten.dungeonkoala.starter;

import org.haw.projekt.agenten.dungeonkoala.starter.ui.StarterController;
import org.haw.projekt.agenten.dungeonkoala.starter.ui.StarterUI_I;
import org.haw.projekt.agenten.dungeonkoala.starter.ui.swing.SwingStarterUI;

public class DungeonKoalaConfigurableStarter {
	public static void main(String[] args) {
		StarterUI_I ui = new SwingStarterUI();
		
		StarterController starter = new StarterController(ui);
		starter.start();
	}
}
