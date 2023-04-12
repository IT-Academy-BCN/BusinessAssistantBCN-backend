package com.businessassistantbcn.usermanagement.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Getter
public class PropertiesConfig {

	//Security datasource properties
	@Value("${security.datasource.secret}")
	private String secret;
	@Value("${security.datasource.headerString}")
	private String headerString;
	@Value("${security.datasource.authoritiesClaim}")
	private String authoritiesClaim;
	@Value("${security.datasource.err}")
	private String errorTokenUnvailables;

	//limitDb properties
	@Value("${limitdb.enabled}")
	private Boolean enabled;
	@Value("${limitdb.maxusers}")
	private Integer maxusers;
	@Value("${limitdb.err}")
	private String errorLimitDb;
}
