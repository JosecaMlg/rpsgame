package com.game.rockpaperscissors.model.api;

import lombok.Data;

@Data
public class GlobalStatsSimpleDataApi {

	private int totalRounds;
	private int totalWinsFirstPlayer;
	private int totalWinsSecondPlayer;
	private int totalDraws;
	
}
