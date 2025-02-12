package com.glassvisionai.glassvisionai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//@ComponentScan(basePackages = {"com.glassvisionai.glassvisionai.services"})
@SpringBootApplication
public class GlassVisionAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlassVisionAiApplication.class, args);
	}

}
