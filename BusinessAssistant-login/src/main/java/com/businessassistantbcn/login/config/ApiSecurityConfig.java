package com.businessassistantbcn.login.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("security.apisecurity")
@Getter @Setter
public class ApiSecurityConfig {

	private String secret;
	private String tokenPrefix;
	private String authoritiesClaim;
	private String err;
}
