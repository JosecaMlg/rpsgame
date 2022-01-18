package com.game.rockpaperscissors.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.game.rockpaperscissors.model.GlobalStatsSimpleData;
import com.game.rockpaperscissors.model.HandShapesEnum;
import com.game.rockpaperscissors.model.MatchResult;
import com.game.rockpaperscissors.model.player.Player;
import com.game.rockpaperscissors.model.player.PlayerMatch;
import com.game.rockpaperscissors.services.storage.MatchStorageService;

import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@WebAppConfiguration
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MatchServiceImplTest {
	
	@Mock
	private MatchStorageService gameUserStorage;
	@Mock
	private MatchStorageService globalUserStorage;
	
	private MatchServiceImpl matchService;
	
	@BeforeAll
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		matchService = new MatchServiceImpl(gameUserStorage, globalUserStorage);
	}
	
	@BeforeEach
	public void setUpTest() throws Exception{
		Mockito.clearInvocations(gameUserStorage);
		Mockito.clearInvocations(globalUserStorage);
	}
	
	@Test
	public void playAMatchTestArgument() {
		log.info("Starts playAMatchTestArgument");
		Assertions.assertThatThrownBy(() -> matchService.playAMatch(null, null))
		.isInstanceOf(NullPointerException.class);
		
		Assertions.assertThatThrownBy(() -> matchService.playAMatch(getGenericPlayerMatch1(), null))
		.isInstanceOf(NullPointerException.class)
		.hasMessage("player2 is marked @NonNull but is null");
		
		Assertions.assertThatThrownBy(() -> matchService.playAMatch(null, getGenericPlayerMatch2()))
		.isInstanceOf(NullPointerException.class)
		.hasMessage("player1 is marked @NonNull but is null");
				
		log.info("Finish playAMatchTestArgument OK!");
	}

	
	@Captor ArgumentCaptor<MatchResult> atr1;
	@Test
	public void playAMatchAddingToStorage() {
		log.info("Starts playAMatchAddingToStorage");
		MatchResult matchResult = matchService.playAMatch(getGenericPlayerMatch1(), getGenericPlayerMatch2());
		
		assertNotNull(matchResult.getResult());
		
		ArgumentCaptor<MatchResult> matchArgument = ArgumentCaptor.forClass(MatchResult.class);
		ArgumentCaptor<MatchResult> matchArgument2 = ArgumentCaptor.forClass(MatchResult.class);
		
		//the match is added to the gameUserStorage and globalStorage
		verify(gameUserStorage, times(1)).addMatch(matchArgument.capture());
		verify(globalUserStorage, times(1)).addMatch(matchArgument2.capture());
		
		//Its the same match what is added and return
		assertEquals(matchArgument.getValue(), matchResult);
		assertEquals(matchArgument2.getValue(), matchResult);
		
		log.info("Finish playAMatchAddingToStorage OK!");
	}
	
	
	@Test
	public void userMatchPlayed() {
		log.info("Starts userMatchPlayed");
		List<MatchResult> allMatches = Arrays.asList(matchPlayer1WinResultExample());
		doReturn(allMatches).when(gameUserStorage).allMatchesPlayed();

		List<MatchResult> matchResult = matchService.userMatchPlayed();
		
		assertEquals(matchResult, allMatches);
		
		log.info("Finish userMatchPlayed OK!");
	}
	
	@Test
	public void resetUserMatches() {
		log.info("Starts resetUserMatches");
		matchService.resetUserMatches();
		
		verify(gameUserStorage, times(1)).counterReset();
		log.info("Finish resetUserMatches OK!");
	}
	
	@Test
	public void allGlobalMatchPlayed() {
		log.info("Starts allGlobalMatchPlayed");
		matchService.allGlobalMatchPlayed();
		
		verify(globalUserStorage, times(1)).allMatchesPlayed();
		log.info("Finish allGlobalMatchPlayed OK!");
	}
	
	@Test
	public void getGlobalStats() {
		log.info("Starts getGlobalStats");
		List<MatchResult> allMatches = Arrays.asList(
				matchPlayer1WinResultExample(), 	matchPlayer1WinResultExample(), 
				matchPlayer2WinResultExample(), 
				matchDrawResultExample(),matchDrawResultExample(),matchDrawResultExample());
		doReturn(allMatches).when(globalUserStorage).allMatchesPlayed();
		
		GlobalStatsSimpleData result = matchService.getGlobalStats();
		
		assertNotNull(result);
		assertEquals(result.getTotalDraws(), 3);
		assertEquals(result.getTotalWinsFirstPlayer(), 2);
		assertEquals(result.getTotalWinsSecondPlayer(), 1);
		assertEquals(result.getTotalRounds(), 6);
		
		log.info("Finish getGlobalStats OK!");
	}
	
	private static PlayerMatch getGenericPlayerMatch1(){
		Player playerOne = Player.builder().name("RockPlayer").build();
			
		return PlayerMatch.builder()
								.chose(HandShapesEnum.ROCK)
								.player(playerOne)
								.build();
	}
	
	private static PlayerMatch getGenericPlayerMatch2(){
		Player playerTwo = Player.builder().name("RandomPlayer").build();
		
		return PlayerMatch.builder()
								.chose(HandShapesEnum.PAPER)
								.player(playerTwo)
								.build();
	}
	
	private static MatchResult matchPlayer2WinResultExample() {
		return MatchResult.builder().player1(getGenericPlayerMatch1()).player2(getGenericPlayerMatch2()).build();
	}
	
	private static MatchResult matchPlayer1WinResultExample() {
		return MatchResult.builder().player1(getGenericPlayerMatch2()).player2(getGenericPlayerMatch1()).build();
	}
	
	private static MatchResult matchDrawResultExample() {
		return MatchResult.builder().player1(getGenericPlayerMatch1()).player2(getGenericPlayerMatch1()).build();
	}
}
