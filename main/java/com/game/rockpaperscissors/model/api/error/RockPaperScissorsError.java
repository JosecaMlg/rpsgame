package com.game.rockpaperscissors.model.api.error;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RockPaperScissorsError {

	private String title;
	private HttpStatus status;
	private String msgException;
	private String uri;
	
}
