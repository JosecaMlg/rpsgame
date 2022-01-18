package com.game.rockpaperscissors.model.player;

import com.game.rockpaperscissors.model.HandShapesEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PlayerMatch {
	
	private final Player player;
	private final HandShapesEnum chose;
	
	PlayerMatch (@NonNull Player player, @NonNull HandShapesEnum chose){
		this.player = player;
		this.chose = chose;
	}

}
