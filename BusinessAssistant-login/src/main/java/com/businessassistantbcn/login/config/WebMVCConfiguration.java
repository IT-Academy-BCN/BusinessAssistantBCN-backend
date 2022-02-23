package com.businessassistantbcn.login.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@ComponentScan(basePackages = {"com.businessassistantbcn"})
@Configuration
@EnableWebMvc
public class WebMVCConfiguration {
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/");
	}
	
}
