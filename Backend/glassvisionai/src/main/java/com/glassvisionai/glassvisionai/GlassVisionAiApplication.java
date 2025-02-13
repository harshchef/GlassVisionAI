package com.glassvisionai.glassvisionai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

//
//@ComponentScan(basePackages = {"com.glassvisionai.glassvisionai.services"})
@SpringBootApplication
public class GlassVisionAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlassVisionAiApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
