package com.game.rockpaperscissors.model.player;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
@Getter
@EqualsAndHashCode
public class Player {

	private final String name;

	public Player(@NonNull String name) {
		super();
		this.name = name;
	}
	
}
