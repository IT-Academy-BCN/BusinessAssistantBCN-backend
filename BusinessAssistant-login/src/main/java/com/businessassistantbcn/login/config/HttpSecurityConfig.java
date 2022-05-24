package com.businessassistantbcn.login.config;

import com.businessassistantbcn.login.security.JwtAuthenticationFilter;
import com.businessassistantbcn.login.service.LoginService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

	@Profile("dev")
	@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
	@EnableWebSecurity
	public static class DisableSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private LoginService provider;

		@Override
		public void configure(AuthenticationManagerBuilder builder) throws Exception {
			builder.authenticationProvider(provider);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().permitAll();
		}

		@Override
		public void configure(WebSecurity web) {
			web.ignoring().antMatchers("/swagger-ui/**", "/swagger-ui-custom.html", "/api-docs/**").anyRequest();
		}
	}

	
}