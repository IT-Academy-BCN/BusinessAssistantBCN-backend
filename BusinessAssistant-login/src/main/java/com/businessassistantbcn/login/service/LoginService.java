package com.businessassistantbcn.login.service;

import com.businessassistantbcn.login.config.PropertiesConfig;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
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
        byte[] bytes = Decoders.BASE64.decode(config.getSecret());
        SignatureAlgorithm alg = SignatureAlgorithm.HS256;
        return Jwts.builder()
        		.setClaims(claims)
        		.setSubject(userDetails.getUsername())
        		.setIssuedAt(new Date(epochTime))
                .setExpiration(new Date(epochTime + config.getExpiresIn()))
                .signWith(new SecretKeySpec(bytes, alg.getJcaName()), alg)
                .compact();
    }
    
	@Override
	public UserDetails loadUserByUsername(String username) {
		return TestAuthenticationProvider
				.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username \'" + username + "\' not found"));
	}
	
}