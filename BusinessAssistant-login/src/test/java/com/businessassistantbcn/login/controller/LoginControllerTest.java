package com.businessassistantbcn.login.controller;

import com.businessassistantbcn.login.dto.AuthenticationRequest;
import com.businessassistantbcn.login.dto.AuthenticationResponse;
import com.businessassistantbcn.login.dto.UserDto;
import com.businessassistantbcn.login.service.LoginService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = LoginController.class)
public class LoginControllerTest {
	
	@Autowired
	private Environment env;
	@MockBean
	private LoginService loginService;
	@Autowired
	private WebTestClient webTestClient;
	
	public static MockWebServer mockLogin;
	
	@BeforeAll
	static void setUp() throws IOException {
		mockLogin = new MockWebServer();
		mockLogin.start();
	}
	
	@AfterAll
	static void tearDown() throws IOException {
		mockLogin.shutdown();
	}
	
	@DisplayName("Login response test")
	@Test
	public void loginTest() {
		// usermanagement mock
		WebTestClient mockClient = webTestClient.mutate()
				.apply(csrf())
				.baseUrl(String.format("http://localhost:%s", mockLogin.getPort()))
				.build();
		
		UserDto mockResponse = new UserDto(
				env.getProperty("security.testuser.email"),
				List.of(env.getProperty("security.testuser.roles[0]")));
		
		try {
			mockLogin.enqueue(new MockResponse()
					.setResponseCode(200)
					.setBody(new ObjectMapper().writeValueAsString(mockResponse))
					.addHeader("Content-Type", "application/json"));
		} catch (JsonProcessingException e) {
			log.error("Error with \'mockResponse\': {}", e);
		}
		
		UserDto superUser = new UserDto(
				env.getProperty("security.superuser.email"),
				List.of(env.getProperty("security.superuser.roles[0]")));
		
		AuthenticationRequest request = new AuthenticationRequest(
				env.getProperty("security.testuser.email"),
				env.getProperty("security.testuser.password"));
		
		// POST login
//		FluxExchangeResult<ResponseEntity<AuthenticationResponse>> response = mockClient.post()
		FluxExchangeResult<AuthenticationResponse> response = mockClient.post()
//		mockClient.post()
				.uri("http://localhost:"
						+ env.getProperty("server.port")
						+ "/businessassistantbcn/api/v1/login")
//				.header(env.getProperty("security.datasource.headerString"), generateToken(superUser))
				.accept(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.exchange()
//				.expectStatus().isOk()
//				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.returnResult(AuthenticationResponse.class);
//				.returnResult(new ParameterizedTypeReference<ResponseEntity<AuthenticationResponse>>() {});
//		log.error(response.getResponseBody().blockFirst());
		Assertions.assertNotNull(response.getResponseBody().blockFirst());
	}
	
	// JSON Web Token generator
	private String generateToken(UserDto userDetails) {
		Map<String, Object> claims = new HashMap<>();
		long epochTime = System.currentTimeMillis();
		byte[] bytes = env.getProperty("security.datasource.secret").getBytes();
		SignatureAlgorithm alg = SignatureAlgorithm.HS256;
		
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(userDetails.getEmail())
			.claim(env.getProperty("security.datasource.authoritiesClaim"),
					AuthorityUtils.authorityListToSet(
							userDetails.getRoles().stream()
									.map(SimpleGrantedAuthority::new)
									.collect(Collectors.toList())))
			.setIssuedAt(new Date(epochTime))
			.setExpiration(new Date(
					epochTime + Long.decode(env.getProperty("security.datasource.expiresIn"))))
			.signWith(new SecretKeySpec(bytes, alg.getJcaName()), alg)
			.compact();
	}
	
}