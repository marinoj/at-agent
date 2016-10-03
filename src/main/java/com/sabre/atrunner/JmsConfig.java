package com.sabre.atrunner;

import org.aopalliance.aop.Advice;
import org.apache.commons.lang3.Validate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.dsl.support.GenericHandler;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import javax.jms.ConnectionFactory;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by marinoj on 01/10/2016
 */
@Configuration
public class JmsConfig {

	@MessagingGateway
	public interface MyGateway {

		@Gateway(requestChannel = "jms.outbound")
		void send(String data);

	}

	@Bean
	public IntegrationFlow jmsOutboundAdapter(ConnectionFactory connectionFactory) {
		return IntegrationFlows.from("jms.outbound")
				.handle(Jms.outboundAdapter(connectionFactory).destination("test.queue").configureJmsTemplate(c -> c.receiveTimeout(TimeUnit.SECONDS.toMillis(10))))
				.get();
	}

	@Bean
	public IntegrationFlow jmsInboundAdapter(ConnectionFactory connectionFactory) {
		return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(container(connectionFactory))
//					.concurrentConsumers(2)
//					.destination("test.queue")
				)
				.transform(String.class, String::toUpperCase)
				.<String>handle((payload, headers) -> {
					System.out.println(payload);
					if(!payload.equalsIgnoreCase("baby!")) {
						throw new RuntimeException("NOT BABY!");
					}
					return null;
				}, e -> e.advice(retryAdvice())) //new RequestHandlerRetryAdvice()))
				.get();
	}

	@Bean
	public DefaultMessageListenerContainer container(ConnectionFactory connectionFactory) {
		final DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setConcurrentConsumers(2);
		container.setDestinationName("test.queue");
		return container;
	}

	@Bean
	public Advice retryAdvice() {
		final RequestHandlerRetryAdvice advice = new RequestHandlerRetryAdvice();
		RetryPolicy policy = new SimpleRetryPolicy(2, Collections.singletonMap(RuntimeException.class, true)); // default = 3 retries
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(policy);
		advice.setRetryTemplate(template);
		return advice;
	}
}
