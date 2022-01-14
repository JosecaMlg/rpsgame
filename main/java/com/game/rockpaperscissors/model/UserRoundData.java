package com.game.rockpaperscissors.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UserRoundData {
	
	private final int numberOfRounds;
	private final String userInfo;
	
}
