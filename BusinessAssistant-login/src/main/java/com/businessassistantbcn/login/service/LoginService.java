package com.businessassistantbcn.login.service;

import com.businessassistantbcn.login.config.SecurityConfig;
import com.businessassistantbcn.login.config.SuperUserConfig;
import com.businessassistantbcn.login.config.TestUserConfig;
import com.businessassistantbcn.login.dto.AuthenticationRequest;
import com.businessassistantbcn.login.dto.UserDto;
import com.businessassistantbcn.login.proxy.HttpProxy;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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

import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoginService implements AuthenticationProvider {

	@Autowired
	private SecurityConfig config;

	@Autowired
	private HttpProxy httpProxy;

	private final UserDto superUser;
	private final UserDto testUser;
	private UserDto userFound;
	private final AuthenticationRequest testUserA;
	private final AuthenticationRequest superUserA;

	public LoginService(SuperUserConfig superUserConfig, TestUserConfig testUserConfig) {
		superUser = new UserDto(superUserConfig.getEmail(), superUserConfig.getRoles());
		superUserA = new AuthenticationRequest(superUserConfig.getEmail(), superUserConfig.getPassword());
		testUser = new UserDto(testUserConfig.getEmail(), testUserConfig.getRoles());
		testUserA = new AuthenticationRequest(testUserConfig.getEmail(), testUserConfig.getPassword());
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

	// TODO **** DISABLE following code once the secured endpoint in 'usermanagement' is established ****
	public Mono<UserDto> loadUser(AuthenticationRequest request) {
		return request.equals(testUserA)
				? Mono.just(testUser)
				: Mono.error(new UsernameNotFoundException("Unknown user \'" + testUserA.getEmail()  + "\'"));
	}

	// Database liaison
 // TODO **** Enable the following code once the secured endpoint in 'usermanagement' is established ****
//	public Mono<UserDto> loadUser(AuthenticationRequest request) {
//		try {
//			String jwt = generateToken(superUser);
//			return httpProxy.getRequestData(new URL(config.getUserManagementUrl()), jwt, request, UserDto.class);
//		} catch(MalformedURLException e) {
//			return Mono.error(e);
//		}
//	}
	
	// Authentication provider
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authenticate(new AuthenticationRequest(
				(String)authentication.getPrincipal(), (String)authentication.getCredentials()));
	}
	
	public Authentication authenticate(AuthenticationRequest request) throws AuthenticationException {
		Optional<String> username = Optional.ofNullable(request.getEmail());
		Optional<String> password = Optional.ofNullable(request.getPassword());
		
		if(credentialsMissing(username, password) || !credentialsValid(request))
			throw new BadCredentialsException("Invalid credentials");
		
		return new UsernamePasswordAuthenticationToken(username.get(), null,
				conversionStringAuthorities(userFound.getRoles()));
	}
	
	private boolean credentialsMissing(Optional<String> username, Optional<String> password) {
		return username.isEmpty() || password.isEmpty();
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