package com.game.rockpaperscissors.services.storage;

import java.util.List;

import com.game.rockpaperscissors.model.MatchResult;

public interface MatchStorageService {

	public void addMatch(MatchResult players);

	List<MatchResult> allMatchesPlayed();

	void counterReset();
	
}
