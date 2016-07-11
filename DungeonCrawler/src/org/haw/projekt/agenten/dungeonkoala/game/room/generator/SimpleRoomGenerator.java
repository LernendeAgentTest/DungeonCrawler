package org.haw.projekt.agenten.dungeonkoala.game.room.generator;

import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.Enemy;
import org.haw.projekt.agenten.dungeonkoala.game.entity.enemy.generator.EnemyGenerator_I;
import org.haw.projekt.agenten.dungeonkoala.game.room.Room;

public class SimpleRoomGenerator implements RoomGenerator_I {
	private EnemyGenerator_I _enemyGenerator;
	private int _roomCounter;

	public SimpleRoomGenerator(EnemyGenerator_I enemyGenerator) {
		_enemyGenerator = Objects.requireNonNull(enemyGenerator);
	}

	@Override
	public Room generateRoom() {
		Enemy enemy = _enemyGenerator.generateEnemy();
		++_roomCounter;
		return new Room(_roomCounter, enemy);
	}

	@Override
	public int getGeneratedRoomsCount() {
		return _roomCounter;
	}
}
