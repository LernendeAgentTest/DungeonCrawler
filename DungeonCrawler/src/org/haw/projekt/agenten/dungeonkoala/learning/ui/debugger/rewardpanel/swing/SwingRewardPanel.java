package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.rewardpanel.swing;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.rewardpanel.ExplorationUIListener_I;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.rewardpanel.RewardUI_I;
import org.haw.projekt.agenten.dungeonkoala.util.listener.EventDispatcher;
import org.haw.projekt.agenten.dungeonkoala.util.swing.SwingUtilities;

public class SwingRewardPanel implements RewardUI_I {

	private static final double STANDARD_REWARD = -10.0;
	private static final double PLAYER_DEAD_REWARD = -100.0;
	private static final double ENEMY_DEAD_REWARD = 100.0;

	private JPanel _content;
	private JSpinner _defaultRewardSpinner;
	private JSpinner _playerDeadReward;
	private JSpinner _enemyDeadReward;
	private JCheckBox _givePlayerDeadReward;
	private EventDispatcher<ExplorationUIListener_I> _dispatcher = new EventDispatcher<>();

	public SwingRewardPanel() {
		initComponents();
		initLayout();
	}

	private void initLayout() {
		_content = new JPanel();
		_content.setLayout(new BoxLayout(_content, BoxLayout.PAGE_AXIS));
		_content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		_content.add(SwingUtilities.labelComponent(_defaultRewardSpinner, "Default Reward:"));
		_content.add(Box.createVerticalStrut(5));
		_content.add(SwingUtilities.labelComponent(_enemyDeadReward, "Enemy Dead Reward:"));
		_content.add(Box.createVerticalStrut(5));
		_content.add(SwingUtilities.labelComponent(_playerDeadReward, "Player Dead Reward:"));
	}

	private void initComponents() {
		_defaultRewardSpinner = new JSpinner(
				new SpinnerNumberModel(STANDARD_REWARD, Integer.MIN_VALUE, Integer.MAX_VALUE, 1.0));
		_defaultRewardSpinner
				.setMaximumSize(new Dimension(Integer.MAX_VALUE, _defaultRewardSpinner.getMinimumSize().height));
		_defaultRewardSpinner.addChangeListener((e) -> {
			_dispatcher.fireEvent((l) -> l.onDefaultRewardChanged(getDefaultReward()));
		});

		_playerDeadReward = new JSpinner(
				new SpinnerNumberModel(PLAYER_DEAD_REWARD, Integer.MIN_VALUE, Integer.MAX_VALUE, 1.0));
		_playerDeadReward.setMaximumSize(new Dimension(Integer.MAX_VALUE, _playerDeadReward.getMinimumSize().height));
		_playerDeadReward.addChangeListener((e) -> {
			_dispatcher.fireEvent((l) -> l.onPlayerDeadRewardChanged(getPlayerDeadReward()));
		});

		_enemyDeadReward = new JSpinner(
				new SpinnerNumberModel(ENEMY_DEAD_REWARD, Integer.MIN_VALUE, Integer.MAX_VALUE, 1.0));
		_enemyDeadReward.setMaximumSize(new Dimension(Integer.MAX_VALUE, _enemyDeadReward.getMinimumSize().height));
		_enemyDeadReward.addChangeListener((e) -> {
			_dispatcher.fireEvent((l) -> l.onEnemyDeadRewardChanged(getEnemyDeadReward()));
		});

		_givePlayerDeadReward = new JCheckBox();
		_givePlayerDeadReward.addActionListener((e) -> {
			_dispatcher.fireEvent((l) -> l.onGivePlayerDeadRewardChanged(isPlayerDeadRewardEnabled()));
		});
	}

	@Override
	public JPanel getComponent() {
		return _content;
	}

	@Override
	public double getDefaultReward() {
		return (Double) _defaultRewardSpinner.getValue();
	}

	@Override
	public double getPlayerDeadReward() {
		return _givePlayerDeadReward.isSelected() ? (Double) _playerDeadReward.getValue()
				: (Double) _defaultRewardSpinner.getValue();
	}

	@Override
	public double getEnemyDeadReward() {
		return (Double) _enemyDeadReward.getValue();
	}

	@Override
	public void setDeafaultReward(double reward) {
		_defaultRewardSpinner.setValue(reward);
	}

	@Override
	public void setPlayerDeadReward(double reward) {
		_playerDeadReward.setValue(reward);
	}

	@Override
	public void setEnemyDeadReward(double reward) {
		_enemyDeadReward.setValue(reward);
	}

	@Override
	public boolean isPlayerDeadRewardEnabled() {
		return _givePlayerDeadReward.isSelected();
	}

	@Override
	public void setPlayerDeadRewardEnabled(boolean enabled) {
		_givePlayerDeadReward.setSelected(enabled);
	}

	@Override
	public void addRewardListener(ExplorationUIListener_I listener) {
		_dispatcher.addListener(listener);
	}

	@Override
	public void removeRewardListener(ExplorationUIListener_I listener) {
		_dispatcher.removeListener(listener);
	}
}
