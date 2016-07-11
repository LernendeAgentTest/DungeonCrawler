package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.statistics;

import javax.swing.JPanel;

public interface StatisticUI_I {
	void setWinrate(double winrate);
	void setAverageActions(double actions);
	void setAverageKillcount(double killcount);
	void setMaxKills(int maxKills);
	void setGames(int games);
	void setWins(int wins);
	void setKills(int kills);
	JPanel getContent();
}
