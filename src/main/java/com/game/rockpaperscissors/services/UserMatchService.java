package com.game.rockpaperscissors.services;

import java.util.List;

import com.game.rockpaperscissors.model.MatchResult;
import com.game.rockpaperscissors.model.player.PlayerMatch;

public interface UserMatchService {

	MatchResult playAMatch(PlayerMatch player1, PlayerMatch player2);

	List<MatchResult> userMatchPlayed();

	void resetUserMatches();

}
