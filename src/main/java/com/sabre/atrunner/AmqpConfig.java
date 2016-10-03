package com.sabre.atrunner;

import org.springframework.context.annotation.Configuration;

/**
 * Created by marinoj on 01/10/2016
 */
@Configuration
public class AmqpConfig {
//
//	// Inbound Channel Adapter
//	@Bean
//	public IntegrationFlow amqpInbound(ConnectionFactory connectionFactory) {
//		return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, "foo"))
//				.channel(Amqp.channel(connectionFactory)
//						.queueName("foo"))
//				.handle(m -> System.out.println(m.getPayload()))
//				.get();
//	}
//
//	// Inbound Gateway
////	@Bean // return the upper cased payload
////	public IntegrationFlow amqpInboundGateway(ConnectionFactory connectionFactory) {
////		return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, "foo"))
////				.transform(String.class, String::toUpperCase)
////				.get();
////	}
//
//	// Outbound Channel Adapter
//	@Bean
//	public IntegrationFlow amqpOutbound(AmqpTemplate amqpTemplate) {
//		return IntegrationFlows.from(amqpOutboundChannel())
//				.handle(Amqp.outboundAdapter(amqpTemplate)
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
//		void sendToRabbit(String data);
//
//	}
//
//	// Outbound Gateway
////	@Bean
////	public IntegrationFlow amqpOutbound(AmqpTemplate amqpTemplate) {
////		return IntegrationFlows.from(amqpOutboundChannel())
////				.handle(Amqp.outboundGateway(amqpTemplate)
////						.routingKey("foo")) // default exchange - route to queue 'foo'
////				.get();
////	}
////
////	@Bean
////	public MessageChannel amqpOutboundChannel() {
////		return new DirectChannel();
////	}
////
////	@MessagingGateway(defaultRequestChannel = "amqpOutboundChannel")
////	public interface MyGateway {
////
////		String sendToRabbit(String data);
////
////	}
}
