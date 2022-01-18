package com.game.rockpaperscissors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.slf4j.Slf4j;

@ContextConfiguration
@WebAppConfiguration
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RockpaperscissorsApplicationTests {

	@Test
	void contextLoads() {
	}

}
