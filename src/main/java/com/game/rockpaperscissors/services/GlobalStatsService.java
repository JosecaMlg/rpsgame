package com.game.rockpaperscissors.services;

import java.util.List;

import com.game.rockpaperscissors.model.GlobalStatsSimpleData;
import com.game.rockpaperscissors.model.MatchResult;

public interface GlobalStatsService {

	List<MatchResult> allGlobalMatchPlayed();

	GlobalStatsSimpleData getGlobalStats();
}
