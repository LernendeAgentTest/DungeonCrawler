package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.statistics.swing;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.statistics.StatisticUI_I;
import org.haw.projekt.agenten.dungeonkoala.util.swing.SwingUtilities;

public class SwingStatiscticsUI implements StatisticUI_I{
	
	private JLabel winrate;
	private JLabel killcount;
	private JLabel maxKills;
	private JLabel wins;
	private JLabel games;
	private JLabel kills;
	private JLabel actionRate;
	
	private JPanel content;
	
	public SwingStatiscticsUI() {
		this.content = createContent();
	}
	
	private JPanel createContent(){
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content,BoxLayout.PAGE_AXIS));
	
		JPanel statisticsPanel = new JPanel();
		statisticsPanel.setLayout(new BoxLayout(statisticsPanel,BoxLayout.PAGE_AXIS));
		statisticsPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		wins = new JLabel("0");

		statisticsPanel.add(SwingUtilities.labelComponent(wins, "Total Wins:"));

		games = new JLabel("0");

		statisticsPanel.add(SwingUtilities.labelComponent(games, "Total Games:"));

		kills = new JLabel("0");

		statisticsPanel.add(SwingUtilities.labelComponent(kills, "Total Kills:"));

		winrate = new JLabel("0.0");

		statisticsPanel.add(SwingUtilities.labelComponent(winrate, "Average Winrate:"));
		
		actionRate = new JLabel("0.0");
		
		statisticsPanel.add(SwingUtilities.labelComponent(actionRate, "Average Actions:"));

		killcount = new JLabel("0.0");

		statisticsPanel.add(SwingUtilities.labelComponent(killcount, "Average Kill per Game:"));

		maxKills = new JLabel("0");

		statisticsPanel.add(SwingUtilities.labelComponent(maxKills, "Max Kills:"));
		
		content.add(statisticsPanel);
		
		return content;
	}

	@Override
	public JPanel getContent() {
		return content;
	}
	
	@Override
	public void setWinrate(double winrate){
		this.winrate.setText(winrate+"");
	}

	@Override
	public void setAverageKillcount(double killcount) {
		this.killcount.setText(killcount+"");		
	}

	@Override
	public void setMaxKills(int maxKills) {
		this.maxKills.setText(maxKills+"");
	}

	@Override
	public void setGames(int games) {
		this.games.setText(games+"");
	}

	@Override
	public void setWins(int wins) {
		this.wins.setText(wins+"");
	}

	@Override
	public void setKills(int kills) {
		this.kills.setText(kills+"");
	}

	@Override
	public void setAverageActions(double actions) {
		this.actionRate.setText(actions+"");
	}
}
