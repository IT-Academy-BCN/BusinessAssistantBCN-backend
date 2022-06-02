package com.businessassistantbcn.login.service;

import com.businessassistantbcn.login.config.SecurityPropertiesConfig;
import com.businessassistantbcn.login.config.SuperUserConfig;
import com.businessassistantbcn.login.config.TestUserConfig;
import com.businessassistantbcn.login.dto.AuthenticationRequest;
import com.businessassistantbcn.login.dto.UserDto;
import com.businessassistantbcn.login.proxy.HttpProxy;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class LoginService implements AuthenticationProvider {
	
	@Autowired
	private SecurityPropertiesConfig config;
	
	@Autowired
	private HttpProxy httpProxy;

	private final UserDto superUser, testUser;
	private final AuthenticationRequest testUserA;

	@Autowired
	public LoginService(SuperUserConfig su, TestUserConfig tu) {
		superUser = new UserDto(su.getEmail(), su.getRoles());
		testUser = new UserDto(tu.getEmail(), tu.getRoles());
		testUserA = new AuthenticationRequest(tu.getEmail(), tu.getPassword());
	}
	
	// JSON Web Token generator
	public String generateToken(UserDto userDetails) {
		Map<String, Object> claims = new HashMap<>();
		long epochTime = System.currentTimeMillis();
		byte[] bytes = config.getSecret().getBytes();
		SignatureAlgorithm alg = SignatureAlgorithm.HS256;
		
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(userDetails.getEmail())
			.claim(config.getAuthoritiesClaim(), AuthorityUtils.authorityListToSet(
					conversionStringAuthorities(userDetails.getRoles())))
			.setIssuedAt(new Date(epochTime))
			.setExpiration(new Date(epochTime + config.getExpiresIn()))
			.signWith(new SecretKeySpec(bytes, alg.getJcaName()), alg)
			.compact();
	}
	
	public String generateToken() {
		return generateToken(userFound);
	}
	
	private List<GrantedAuthority> conversionStringAuthorities(List<String> roles) {
		return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

// TODO **** Provisional response with testUser ****
	public Mono<UserDto> loadUser(AuthenticationRequest request) {
		return request.equals(testUserA)
				? Mono.just(testUser)
				: Mono.error(new UsernameNotFoundException("Unknown user \'" + testUserA.getEmail()  + "\'"));
	}
	
	// Database liaison
// TODO **** Enable the following code once the secured endpoint in 'usermanagement' is established ****
//	public Mono<UserDto> loadUser(AuthenticationRequest request) { try {
//		String jwt = generateToken(superUser);
//		
//		return httpProxy.getRequestData(new URL(config.getUserManagementUrl()), jwt, request, UserDto.class);
//	} catch(MalformedURLException e) {
//		return Mono.error(e);
//	} }
	
	private UserDto userFound;
	
	// Authentication provider
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authenticate(new AuthenticationRequest(
				(String)authentication.getPrincipal(), (String)authentication.getCredentials()));
	}
	
	public Authentication authenticate(AuthenticationRequest request) throws AuthenticationException {
		Optional<String> username = Optional.ofNullable((String)request.getEmail());
		Optional<String> password = Optional.ofNullable((String)request.getPassword());
		
		if(credentialsMissing(username, password) || !credentialsValid(request))
			throw new BadCredentialsException("Invalid credentials");
		
		return new UsernamePasswordAuthenticationToken(username.get(), null,
				conversionStringAuthorities(userFound.getRoles()));
	}
	
	private boolean credentialsMissing(Optional<String> username, Optional<String> password) {
		return !username.isPresent() || !password.isPresent();
	}
	
	private boolean credentialsValid(AuthenticationRequest request) { // Request to DB
		loadUser(request).subscribe(user -> userFound = user, t -> userFound = null);
		
		return userFound != null;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}