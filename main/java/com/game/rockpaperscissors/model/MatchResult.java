package com.game.rockpaperscissors.model;

import com.game.rockpaperscissors.model.player.PlayerMatch;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
public class MatchResult {

	private final PlayerMatch player1;
	private final PlayerMatch player2;
	private final Result result;

	MatchResult(@NonNull PlayerMatch player1, @NonNull PlayerMatch player2) {
		this.player1 = player1;
		this.player2 = player2;
		this.result = player1.getChose().beats(player2.getChose());
	}

	public static MatchResultBuilder builder() {
		return new MatchResultBuilder();
	}
	
	public static class MatchResultBuilder {
		private PlayerMatch player1;

		private PlayerMatch player2;

		public MatchResultBuilder player2(@NonNull PlayerMatch player2) {
			this.player2 = player2;
			return this;
		}

		public MatchResultBuilder player1(@NonNull PlayerMatch player1) {
			this.player1 = player1;
			return this;
		}

		public MatchResult build() {
			return new MatchResult(this.player1, this.player2);
		}

	}
}
