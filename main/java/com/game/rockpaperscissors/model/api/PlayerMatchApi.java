package com.game.rockpaperscissors.model.api;

import com.game.rockpaperscissors.model.HandShapesEnum;

import lombok.Data;

@Data
public class PlayerMatchApi {
	
	private String name;
	private HandShapesEnum chose;
	
}
