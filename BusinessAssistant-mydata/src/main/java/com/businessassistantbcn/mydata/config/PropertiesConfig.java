package com.businessassistantbcn.mydata.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Getter @Setter
public class PropertiesConfig {

	//Security Datasource
	@Value("${security.datasource.secret}")
	private String secret;
	@Value("${security.datasource.headerString}")
	private String headerString;
	@Value("${security.datasource.authoritiesClaim}")
	private String authoritiesClaim;
	@Value("${security.datasource.err}")
	private String err;

	//Search limit for user
	@Value("${user.searchLimit.limitValue}")
	private Integer limitValue;
	@Value("${user.searchLimit.isLimitEnabled}")
	private Boolean isLimitEnabled;
	@Value("${user.searchLimit.errorMessage}")
	private String errorMessage;
	
}
