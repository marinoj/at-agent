package com.sabre.atrunner;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@IntegrationComponentScan
public class AtAgentApplication {

	// Inbound Channel Adapter
	@Bean
	public IntegrationFlow amqpInbound(ConnectionFactory connectionFactory) {
		return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, "foo"))
				.channel(Amqp.channel(connectionFactory)
						.queueName("foo"))
				.handle(m -> System.out.println(m.getPayload()))
				.get();
	}

	// Inbound Gateway
//	@Bean // return the upper cased payload
//	public IntegrationFlow amqpInboundGateway(ConnectionFactory connectionFactory) {
//		return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, "foo"))
//				.transform(String.class, String::toUpperCase)
//				.get();
//	}

	// Outbound Channel Adapter
	@Bean
	public IntegrationFlow amqpOutbound(AmqpTemplate amqpTemplate) {
		return IntegrationFlows.from(amqpOutboundChannel())
				.handle(Amqp.outboundAdapter(amqpTemplate)
						.routingKey("foo")) // default exchange - route to queue 'foo'
				.get();
	}

	@Bean
	public MessageChannel amqpOutboundChannel() {
		return new DirectChannel();
	}

	@MessagingGateway(defaultRequestChannel = "amqpOutboundChannel")
	public interface MyGateway {

		void sendToRabbit(String data);

	}

	// Outbound Gateway
//	@Bean
//	public IntegrationFlow amqpOutbound(AmqpTemplate amqpTemplate) {
//		return IntegrationFlows.from(amqpOutboundChannel())
//				.handle(Amqp.outboundGateway(amqpTemplate)
//						.routingKey("foo")) // default exchange - route to queue 'foo'
//				.get();
//	}
//
//	@Bean
//	public MessageChannel amqpOutboundChannel() {
//		return new DirectChannel();
//	}
//
//	@MessagingGateway(defaultRequestChannel = "amqpOutboundChannel")
//	public interface MyGateway {
//
//		String sendToRabbit(String data);
//
//	}

	public static void main(String[] args) {
//		SpringApplication.run(AtAgentApplication.class, args);

		new SpringApplicationBuilder(AtAgentApplication.class)
				.web(false)
				.run(args);
	}
}
