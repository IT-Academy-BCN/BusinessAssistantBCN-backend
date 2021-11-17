package com.businessassistantbcn.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.businessassistantbcn.login.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	public void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
		http.addFilterAfter(new JwtAuthenticationFilter(), 
				UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests().anyRequest().authenticated();
	}
}
