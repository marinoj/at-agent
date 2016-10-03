package com.sabre.atrunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtAgentApplicationTests {

	@Autowired
	public JmsConfig.MyGateway gateway;

//	@Test
//	public void contextLoads() {
//	}

	@Test
	public void shouldPublishMessage() throws Exception {
		gateway.send("yeah!");
		gateway.send("baby!");
		Thread.sleep(TimeUnit.SECONDS.toMillis(20));
	}
}
