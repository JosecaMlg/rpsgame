package com.game.rockpaperscissors.services.storage.impl;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.game.rockpaperscissors.model.MatchResult;
import com.game.rockpaperscissors.services.storage.MatchStorageService;

import lombok.NonNull;

@Service("globalUserStorage")
public class MatchMemoryStorageImpl implements MatchStorageService {

	private LinkedBlockingQueue<MatchResult> matches = new LinkedBlockingQueue<MatchResult>();
	
	@Override
	public void addMatch(@NonNull MatchResult match) {
		matches.add(match);
	}
	
	@Override
	public void counterReset() {
		matches.clear();
	}

	@Override
	public List<MatchResult> allMatchesPlayed(){
		//Return a copy never the original list!
		return matches.stream().collect(Collectors.toUnmodifiableList());
	}
}
