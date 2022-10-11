package com.hua.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * UI redirection rules
 */
@Configuration
public class LoginRedirect implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("main");
		registry.addViewController("/main").setViewName("main");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/register").setViewName("register");
		
	}

}