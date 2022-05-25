package com.businessassistantbcn.login.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.datasource")
@Getter @Setter
public class SecurityPropertiesConfig {
	
	private String signUpUrl;
	private String secret;
	private String tokenPrefix;
	private long expiresIn;
	private String authoritiesClaim;
	private String err;
	private String userManagementUrl;
	
}