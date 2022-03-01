package com.businessassistantbcn.login.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("url")
@Getter @Setter
public class ProxyConfig {
	
	private int connection_timeout;
	
}