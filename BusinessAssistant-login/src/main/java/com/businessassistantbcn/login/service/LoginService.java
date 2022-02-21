package com.businessassistantbcn.login.service;

import com.businessassistantbcn.login.config.PropertiesConfig;
import com.businessassistantbcn.login.security.DevelopAuthenticationProvider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {
	
	@Autowired
	private PropertiesConfig config;
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		long epochTime = System.currentTimeMillis();
		byte[] bytes = config.getSecret().getBytes();
		SignatureAlgorithm alg = SignatureAlgorithm.HS256;
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(userDetails.getUsername())
			.claim(config.getAuthoritiesClaim(),
					AuthorityUtils.authorityListToSet(userDetails.getAuthorities()))
			.setIssuedAt(new Date(epochTime))
			.setExpiration(new Date(epochTime + config.getExpiresIn()))
			.signWith(new SecretKeySpec(bytes, alg.getJcaName()), alg)
			.compact();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		//TODO
		///QQQ - Warning! For development purposes only
		return DevelopAuthenticationProvider
				.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username \'" + username + "\' not found"));
	}
	
}
