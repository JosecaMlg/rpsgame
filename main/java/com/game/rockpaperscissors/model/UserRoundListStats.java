package com.game.rockpaperscissors.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class UserRoundListStats {
	private final List<MatchResult> matchResults;
	private final int numberOfRounds;
	
}
