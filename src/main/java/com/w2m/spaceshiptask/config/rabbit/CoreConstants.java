package com.w2m.spaceshiptask.config.rabbit;

public final class CoreConstants {

	public static final String RABBIT_EXCHANGE_NAME = "defaultExchange";

	public static final String RABBIT_DEAD_LETTER = "dead.letter.queue";

	public static final String RABBIT_DEAD_LETTER_EXCHANGE_KEY = "x-dead-letter-exchange";

	public static final String RABBIT_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

	public static final String RABBIT_SHOW_SPACESHIP_REQUEST = "request.to.fetch.spaceship.images.to.send.queue";

	public static final String RABBIT_SHOW_SPACESHIP_REQUEST_ROUTING_KEY = "request.to.fetch.spaceship.images.to.send";
	
	private CoreConstants() {
		
	}

}
