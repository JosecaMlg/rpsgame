package com.game.rockpaperscissors.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import com.game.rockpaperscissors.model.GlobalStatsSimpleData;
import com.game.rockpaperscissors.model.api.GlobalStatsSimpleDataApi;
import com.game.rockpaperscissors.services.GlobalStatsService;

import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@WebAppConfiguration
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GlobalDataControllerTest {

	@InjectMocks
	private GlobalDataController globalDataController;
	
	@Mock
	private GlobalStatsService matchService;
	
	@BeforeAll
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void playRoundTest() {
		log.info("Runing playRoundTest Starts");
		//Prepare test
		final int draws = 5;
		final int firstPlayerWin = 5;
		final int secondPlayerWin = 5;
		final int totalRounds = 5;
		
		GlobalStatsSimpleData outputService =  GlobalStatsSimpleData.builder()
								.totalDraws(draws)
								.totalRounds(firstPlayerWin)
								.totalWinsFirstPlayer(secondPlayerWin)
								.totalWinsSecondPlayer(totalRounds)
								.build();
		
		Mockito.doReturn(outputService).when(matchService).getGlobalStats();
		//End preparing test
		
		GlobalStatsSimpleDataApi outputController = globalDataController.getStats();
		verify(matchService, times(1)).getGlobalStats(); //The service matchService and the GlobalStats is invoked
		
		//Verify that the output is the expected
		Assert.notNull(outputController, "The controller output cannot be null!");
		Assert.isTrue(outputController.getTotalRounds() 			== totalRounds, "The output TotalRounds does not match!");
		Assert.isTrue(outputController.getTotalDraws() 				== draws, "The output TotalDraws does not match!");
		Assert.isTrue(outputController.getTotalWinsFirstPlayer() 	== firstPlayerWin, "The output TotalWinsFirstPlayer does not match!");
		Assert.isTrue(outputController.getTotalWinsSecondPlayer() 	== secondPlayerWin, "The output TotalWinsSecondPlayer does not match!");
		
		log.info("Runing playRoundTest Ends OK!");
	}

}
