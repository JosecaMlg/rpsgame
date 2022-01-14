package com.game.rockpaperscissors.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.rockpaperscissors.model.GlobalStatsSimpleData;
import com.game.rockpaperscissors.model.api.GlobalStatsSimpleDataApi;
import com.game.rockpaperscissors.services.GlobalStatsService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/global")
public class GlobalDataController extends BaseController {
	
	private final GlobalStatsService matchService;
	
	@GetMapping(path="/stats")
	public GlobalStatsSimpleDataApi getStats() {
		GlobalStatsSimpleData gStats = matchService.getGlobalStats();
		return defaultModelMapper.map(gStats, GlobalStatsSimpleDataApi.class);
	}
}
