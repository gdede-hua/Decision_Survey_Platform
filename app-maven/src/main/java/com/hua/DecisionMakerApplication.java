package com.hua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class DecisionMakerApplication {

	/**
	 * Start up the Spring project
	 *  @param args - Start uo arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(DecisionMakerApplication.class, args);
	}


}
