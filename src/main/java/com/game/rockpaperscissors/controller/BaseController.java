package com.game.rockpaperscissors.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import lombok.NonNull;

public abstract class BaseController {
	
	ModelMapper defaultModelMapper = getDefaultMapper();
	
	<S, T> List<T> mapList(@NonNull List<S> source, @NonNull Class<T> target) {
	    return source
	      .stream()
	      .map(element -> defaultModelMapper.map(element, target))
	      .collect(Collectors.toList());
	}

	private ModelMapper getDefaultMapper() {
		ModelMapper dmapper = new ModelMapper();
		dmapper.getConfiguration()
		.setMatchingStrategy(MatchingStrategies.STANDARD);
		
		return dmapper;
	}

}
