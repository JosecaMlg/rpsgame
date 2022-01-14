package com.game.rockpaperscissors.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.game.rockpaperscissors.model.api.error.RockPaperScissorsError;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<RockPaperScissorsError> handlerGenericException(Exception ex, HttpServletRequest request){
		RockPaperScissorsError erroroutput = RockPaperScissorsError.builder()
		.msgException(ex.getMessage())
		.title("An unexpeted error has occurred")
		.status(HttpStatus.I_AM_A_TEAPOT)
		.uri(request.getRequestURI())
		.build();
		
		return new ResponseEntity<>(erroroutput, HttpStatus.I_AM_A_TEAPOT);
	}
}
