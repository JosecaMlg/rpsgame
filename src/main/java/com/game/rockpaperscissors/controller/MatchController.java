package com.game.rockpaperscissors.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.rockpaperscissors.model.HandShapesEnum;
import com.game.rockpaperscissors.model.MatchResult;
import com.game.rockpaperscissors.model.UserRoundData;
import com.game.rockpaperscissors.model.api.MatchResultApi;
import com.game.rockpaperscissors.model.api.RoundMatchesDataApi;
import com.game.rockpaperscissors.model.player.Player;
import com.game.rockpaperscissors.model.player.PlayerMatch;
import com.game.rockpaperscissors.services.UserMatchService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class MatchController extends BaseController{
	
	private final UserMatchService matchService;
	
	@PostMapping(path="/round")
	public MatchResultApi playRound() {
		Player playerOne = Player.builder().name("RockPlayer").build();
		Player playerTwo = Player.builder().name("RandomPlayer").build();
		
		PlayerMatch player1 = PlayerMatch.builder()
								.chose(HandShapesEnum.ROCK)
								.player(playerOne)
								.build();
		PlayerMatch player2 = PlayerMatch.builder()
								.chose(HandShapesEnum.getRandomShape())
								.player(playerTwo)
								.build();
		
		log.debug("Invoking to matchService.playAMatch with these players {}, {}", player1, player2);
		MatchResult mr = matchService.playAMatch(player1, player2);
		
		log.debug("Match played! result : {}", mr);
		
		return defaultModelMapper.map(mr, MatchResultApi.class);
	}

	@GetMapping(path="/round/number")
	public UserRoundData getUserRounds() {
		int rounds = matchService.userMatchPlayed().size();
		return UserRoundData.builder()
				.numberOfRounds(rounds)
				.userInfo(String.format("The user has played %d rounds.", rounds))
				.build();
	}
	
	@GetMapping(path="/round/data")
	public RoundMatchesDataApi getTableInfo() {
		 List<MatchResult> userMatch = matchService.userMatchPlayed();
		 List<MatchResultApi> listMatches = mapList(userMatch, MatchResultApi.class);
		 return new RoundMatchesDataApi(listMatches, listMatches.size());
	}
	
	@PostMapping(path="/reset")
	public void resetRound() {
		matchService.resetUserMatches();
	}
}
