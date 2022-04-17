package com.hua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class DecisionMakerApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(DecisionMakerApplication.class, args);
	}


}
