package com.game.rockpaperscissors.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GlobalStatsSimpleData {

	private final int totalRounds;
	private final int totalWinsFirstPlayer;
	private final int totalWinsSecondPlayer;
	private final int totalDraws;
	
}
