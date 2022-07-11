package com.businessassistantbcn.login.config;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.superuser")
@Data
public class SuperUserConfig {
	
	private String email;
	private String password;
	private List<String> roles;
	
}