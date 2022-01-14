package com.game.rockpaperscissors.services.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.game.rockpaperscissors.model.GlobalStatsSimpleData;
import com.game.rockpaperscissors.model.GlobalStatsSimpleData.GlobalStatsSimpleDataBuilder;
import com.game.rockpaperscissors.model.MatchResult;
import com.game.rockpaperscissors.model.Result;
import com.game.rockpaperscissors.model.player.PlayerMatch;
import com.game.rockpaperscissors.services.GlobalStatsService;
import com.game.rockpaperscissors.services.UserMatchService;
import com.game.rockpaperscissors.services.storage.MatchStorageService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatchServiceImpl implements UserMatchService, GlobalStatsService {

	private final MatchStorageService userStorage;
	private final MatchStorageService globalStorage;

	public MatchServiceImpl(@Qualifier("gameUserStorage") MatchStorageService userStorage,
			@Qualifier("globalUserStorage") MatchStorageService globalStorage) {
		super();
		this.userStorage = userStorage;
		this.globalStorage = globalStorage;
	}

	@Override
	public MatchResult playAMatch(@NonNull PlayerMatch player1, @NonNull PlayerMatch player2) {
		log.debug("Going to play a match between {} and {}", player1, player2);
		MatchResult matchResult = MatchResult.builder().player1(player1).player2(player2).build();
		log.debug("Match result -> {}", matchResult);
		
		log.debug("adding the match to the User And GlobalStorage Match - {}", matchResult);
		userStorage.addMatch(matchResult);
		globalStorage.addMatch(matchResult);
		log.debug("Match added to the storages! - {}", matchResult);
		
		return matchResult;
	}

	@Override
	public List<MatchResult> userMatchPlayed() {
		return userStorage.allMatchesPlayed();
	}

	@Override
	public void resetUserMatches() {
		userStorage.counterReset();
	}

	@Override
	public List<MatchResult> allGlobalMatchPlayed() {
		return globalStorage.allMatchesPlayed();
	}

	@Override
	public GlobalStatsSimpleData getGlobalStats() {
		List<MatchResult> allGames = globalStorage.allMatchesPlayed();
		Map<Result, Integer> output = allGames.stream()
				.collect(Collectors.toMap(MatchResult::getResult, mr -> 1, (a1, a2) -> ++a1));
		log.debug("Get all raw global statics -> {}",output);
		
		GlobalStatsSimpleDataBuilder builderOutput = GlobalStatsSimpleData.builder()
				.totalDraws(output.containsKey(Result.DRAW) ? output.get(Result.DRAW) : 0)
				.totalWinsFirstPlayer(output.containsKey(Result.PLAYER1WINS) ? output.get(Result.PLAYER1WINS) : 0)
				.totalWinsSecondPlayer(output.containsKey(Result.PLAYER2WINS) ? output.get(Result.PLAYER2WINS) : 0)
				.totalRounds(allGames.size());
		log.debug("Get all procesed global statics -> {}",builderOutput);
		return builderOutput.build();
	}
}
