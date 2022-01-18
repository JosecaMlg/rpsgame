package com.game.rockpaperscissors.services.storage.impl;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.game.rockpaperscissors.model.MatchResult;
import com.game.rockpaperscissors.services.storage.MatchStorageService;

import lombok.NonNull;

@SessionScope
@Service("gameUserStorage")
public class MatchSessionStorageImpl implements MatchStorageService {

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
