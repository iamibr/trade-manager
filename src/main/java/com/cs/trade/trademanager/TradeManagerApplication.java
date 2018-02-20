package com.cs.trade.trademanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


@ComponentScan("com.cs.trade.trademanager")
@SpringBootApplication
public class TradeManagerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TradeManagerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
