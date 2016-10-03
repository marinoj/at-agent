package com.sabre.atrunner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
public class AtAgentApplication {


	public static void main(String[] args) {
//		SpringApplication.run(AtAgentApplication.class, args);

		new SpringApplicationBuilder(AtAgentApplication.class, JmsConfig.class)
				.web(false)
				.run(args);
	}
}
