package com.game.rockpaperscissors.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.internal.util.Assert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.game.rockpaperscissors.model.HandShapesEnum;
import com.game.rockpaperscissors.model.MatchResult;
import com.game.rockpaperscissors.model.UserRoundData;
import com.game.rockpaperscissors.model.api.MatchResultApi;
import com.game.rockpaperscissors.model.api.RoundMatchesDataApi;
import com.game.rockpaperscissors.model.player.Player;
import com.game.rockpaperscissors.model.player.PlayerMatch;
import com.game.rockpaperscissors.services.UserMatchService;

import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@WebAppConfiguration
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MatchControllerTest {
	
	@InjectMocks
	private MatchController matchController;
	
	@Mock
	private UserMatchService matchService;
	
	@BeforeAll
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	@BeforeEach
	public void setUpTest() throws Exception{
		Mockito.clearInvocations(matchService);
	}
	
	
	//Check the correct invocation to the service
	@Captor ArgumentCaptor<PlayerMatch> atr1;
	@Test
	void playRoundTest() {
		log.info("Starting playRoundTest");
		MatchResult matchResult = matchResultExample();
		doReturn(matchResult).when(matchService).playAMatch(Mockito.any(), Mockito.any());
		
		MatchResultApi resultApi = matchController.playRound();
		//Verify service is invoked
		ArgumentCaptor<PlayerMatch> player1 = ArgumentCaptor.forClass(PlayerMatch.class);
		ArgumentCaptor<PlayerMatch> player2 = ArgumentCaptor.forClass(PlayerMatch.class);
		verify(matchService, times(1)).playAMatch(player1.capture(), player2.capture());
		
		Assert.notNull(player1.getValue());
		Assert.notNull(player2.getValue());
		
		//Mapping is correct to MatchResultApi!
		assertEquals(resultApi.getPlayer1().getName(), matchResult.getPlayer1().getPlayer().getName());
		assertEquals(resultApi.getPlayer1().getChose(), matchResult.getPlayer1().getChose());
		assertEquals(resultApi.getPlayer2().getName(), matchResult.getPlayer2().getPlayer().getName());
		assertEquals(resultApi.getPlayer2().getChose(), matchResult.getPlayer2().getChose());
		assertEquals(resultApi.getResult(), matchResult.getResult());
		
		log.info("playRoundTest ends OK!");
	}
	
	@Test
	void getUserRounds() {
		log.info("Starting getUserRounds");
		List<MatchResult> matches = new ArrayList<MatchResult>();
		matches.add(matchResultExample());
		
		doReturn(matches).when(matchService).userMatchPlayed();
		
		UserRoundData output = matchController.getUserRounds();

		//verify the correct invocation
		verify(matchService, times(1)).userMatchPlayed();
		
		Assert.isTrue(output.getNumberOfRounds() == matches.size());
		
		log.info("getUserRounds ends OK!");
	}
	
	@Test
	void getTableInfo() {
		log.info("Starting getTableInfo");
		List<MatchResult> matches = new ArrayList<MatchResult>();
		matches.add(matchResultExample());
		doReturn(matches).when(matchService).userMatchPlayed();
		
		RoundMatchesDataApi output = matchController.getTableInfo();
		
		//verify the correct invocation
		verify(matchService, times(1)).userMatchPlayed();
		
		//Verify the correct mapping
		Assert.notNull(output, "output cannot be null!");
		Assert.isTrue(output.getMatches().size() == matches.size());
		MatchResultApi outputController = output.getMatches().get(0);
		MatchResult outputService = matches.get(0);
		
		assertEquals(outputController.getPlayer1().getName(), outputService.getPlayer1().getPlayer().getName());
		assertEquals(outputController.getPlayer1().getChose(), outputService.getPlayer1().getChose());
		assertEquals(outputController.getPlayer2().getName(), outputService.getPlayer2().getPlayer().getName());
		assertEquals(outputController.getPlayer2().getChose(), outputService.getPlayer2().getChose());
		assertEquals(outputController.getResult(), outputService.getResult());
		log.info("getTableInfo ends OK!");
	}
	
	@Test
	void resetRound() {
		log.info("Starting resetRound");
		
		matchController.resetRound();
		verify(matchService, times(1)).resetUserMatches();
		
		log.info("resetRound ends OK!");
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
								.chose(HandShapesEnum.getRandomShape())
								.player(playerTwo)
								.build();
	}
	
	private static MatchResult matchResultExample() {
		return MatchResult.builder().player1(getGenericPlayerMatch1()).player2(getGenericPlayerMatch2()).build();
	}
}
