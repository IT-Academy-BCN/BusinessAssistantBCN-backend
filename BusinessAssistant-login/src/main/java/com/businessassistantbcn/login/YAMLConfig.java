package com.businessassistantbcn.login;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("security.datasource")
public class YAMLConfig {
	
	private String signUpUrl;
	private String secret;
	private String headerString;
	private String tokenPrefix;
	private String authorities; // lista de permisos (ROLES)
	
	public String getSignUpUrl() {
		return signUpUrl;
	}
	
	public String getSecret() {
		return secret;
	}
	
	public String getHeaderString() {
		return headerString;
	}
	
	public String getTokenPrefix() {
		return tokenPrefix;
	}
	
	public String getAuthorities() {
		return authorities;
	}
	
	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public void setHeaderString(String headerString) {
		this.headerString = headerString;
	}
	
	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}
	
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	
}
