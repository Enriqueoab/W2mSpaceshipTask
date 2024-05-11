package com.w2m.spaceshiptask.config.rabbit;

import java.util.HashMap;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

@Configuration
public class RabbitConfig {

	@Autowired
	private CachingConnectionFactory connectionFactory;

	@Bean
	HashMap<String, Object> deadLetterArgs() {
		var args = new HashMap<String, Object>();
		args.put(CoreConstants.RABBIT_DEAD_LETTER_EXCHANGE_KEY, CoreConstants.RABBIT_EXCHANGE_NAME);
		args.put(CoreConstants.RABBIT_DEAD_LETTER_ROUTING_KEY, CoreConstants.RABBIT_DEAD_LETTER);
		return args;
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(CoreConstants.RABBIT_EXCHANGE_NAME);
	}

	@Bean
	Queue deadLetterQueue() {
		return new Queue(CoreConstants.RABBIT_DEAD_LETTER);
    }

	@Bean
	Queue showSpaceshipRequestEventQueue(HashMap<String, Object> deadLetterArgs) {
		return new Queue(CoreConstants.RABBIT_SHOW_SPACESHIP_REQUEST, true, false, false, deadLetterArgs);
	}

	@Bean
	Binding bindingShowSpaceshipRequestEventQueue(Queue showSpaceshipRequestEventQueue,DirectExchange exchange) {
		return BindingBuilder.bind(showSpaceshipRequestEventQueue).to(exchange)
				.with(CoreConstants.RABBIT_SHOW_SPACESHIP_REQUEST_ROUTING_KEY);
	}

}
