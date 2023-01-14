package com.hua.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * UI redirection rules
 * @author      John Nikolaou
 */
@Configuration
public class LoginRedirect implements WebMvcConfigurer {

	/**
	 * The urls and the pages the user could open without login to the UI.
	 * @param registry
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("main");
		registry.addViewController("/main").setViewName("main");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/register").setViewName("register");
		
	}

}