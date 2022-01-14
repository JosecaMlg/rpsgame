package com.game.rockpaperscissors.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lombok.NonNull;

public enum HandShapesEnum {
	ROCK {
		@Override
		public Result beats(@NonNull HandShapesEnum other) {
			if (other == this)
				return Result.DRAW;
			else if (other == SCISSORS)
				return Result.PLAYER1WINS;
			else
				return Result.PLAYER2WINS;
		}
	}, PAPER {
		@Override
		public Result beats(@NonNull HandShapesEnum other) {
			if (other == this)
				return Result.DRAW;
			else if (other == ROCK)
				return Result.PLAYER1WINS;
			else
				return Result.PLAYER2WINS;
		}
	}, SCISSORS {
		@Override
		public Result beats(@NonNull HandShapesEnum other) {
			if (other == this)
				return Result.DRAW;
			else if (other == PAPER)
				return Result.PLAYER1WINS;
			else
				return Result.PLAYER2WINS;
		}
	};

	private static final List<HandShapesEnum> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();


	public static HandShapesEnum getRandomShape() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

	public abstract Result beats(HandShapesEnum other);
}
