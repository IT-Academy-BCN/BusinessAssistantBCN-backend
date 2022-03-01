package com.businessassistantbcn.login.service;

import com.businessassistantbcn.login.config.TestUser;
import com.businessassistantbcn.login.config.SecurityConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService, AuthenticationProvider {
	
	@Autowired
	private SecurityConfig config;
	
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
	
	public String generateToken(Authentication authentication) {
		return generateToken(loadUserByUsername(authentication.getName()));
	}
	
// ***  WARNING -- For development purposes only  **
	@SuppressWarnings("unused")
	private static final List<GrantedAuthority> AUTHORITIES; // Potencialmente útil próximamente
	
	@Autowired
	public LoginService(TestUser adminUser) {
		tempUsers.add(new TempUser(adminUser.getUserName(), adminUser.getPassword(), List.of("ADMIN")));
		// Aquí se pueden añadir más usuarios a 'testUsers'
	}
	
	static {
		AUTHORITIES = AuthorityUtils.commaSeparatedStringToAuthorityList(""
				+ "ADMIN,"
				+ "USER"
				// Aquí se pueden añadir más credenciales separadas por comas
				);
	}
	
	// BBDD provisional
	private static List<TempUser> tempUsers = new ArrayList<>();
	
	// Elemento de la BBDD provisional
	@Getter
	@AllArgsConstructor
	private static class TempUser {
		
		private String email;
		private String password;
		private List<String> roles;
		
		public boolean match(String name) {
			return email.equals(name);
		}
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		Function<TempUser, User> conversion = user -> {
			List<String> auth = user.getRoles();
			
			return new User(user.getEmail(), user.getPassword(),
					auth.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		};
		
		return tempUsers.stream()
				.filter(u -> u.match(username)).map(conversion).findFirst()
				.orElseThrow(() -> new UsernameNotFoundException("Username \'" + username + "\' not found"));
	}
	
	private UserDetails userFound;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Optional<String> username = Optional.ofNullable((String)authentication.getPrincipal());
		Optional<String> password = Optional.ofNullable((String)authentication.getCredentials());
		
		if(credentialsMissing(username, password) || !credentialsValid(username, password))
			throw new BadCredentialsException("Invalid credentials");
		
		return new UsernamePasswordAuthenticationToken(username.get(), null, userFound.getAuthorities());
	}
	
	private boolean credentialsMissing(Optional<String> username, Optional<String> password) {
		return !username.isPresent() || !password.isPresent();
	}
	
	private boolean credentialsValid(Optional<String> username, Optional<String> password) { try {
		userFound = loadUserByUsername(username.get());			// finds username...
		return password.get().equals(userFound.getPassword());	// ...and checks the password
	} catch(UsernameNotFoundException e) { return false; } }
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}