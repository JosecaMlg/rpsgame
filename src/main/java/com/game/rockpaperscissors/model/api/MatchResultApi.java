package com.game.rockpaperscissors.model.api;

import com.game.rockpaperscissors.model.Result;

import lombok.Data;

@Data
public class MatchResultApi {

	private PlayerMatchApi player1;
	private PlayerMatchApi player2;
	private Result result;

}
