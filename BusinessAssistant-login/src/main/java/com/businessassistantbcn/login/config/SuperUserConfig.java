package com.businessassistantbcn.login.config;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.superuser")
@Getter @Setter
public class SuperUserConfig {
	
	private String email;
	private List<String> roles;
	
}