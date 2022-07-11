package com.businessassistantbcn.opendata.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("security.datasource")
@Getter @Setter
public class SecurityPropertiesConfig {

	private String secret;
	private String headerString;
	private String authoritiesClaim;
	private String err;
}
