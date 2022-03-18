package com.businessassistantbcn.login.config;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.testuser")
@Getter @Setter
public class TestUserConfig {
	
	private String email;
	private String password;
	private List<String> roles;
	
}