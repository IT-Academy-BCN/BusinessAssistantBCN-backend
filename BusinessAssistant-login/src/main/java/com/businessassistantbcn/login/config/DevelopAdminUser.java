package com.businessassistantbcn.login.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("security.testuser")
@Getter @Setter
public class DevelopAdminUser {
	
	private String userName;
	private String password;
	
}
