package com.businessassistantbcn.usermanagement.controller;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserManagementController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
public class UserManagementControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/usermanagement";
	
	@Test
	@DisplayName("Test response Hello")
	void testDevProfile_OKwithoutAuthentication() {
		final String URI_TEST = "/test";
		webTestClient.get()
				.uri(CONTROLLER_BASE_URL + URI_TEST)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.value(s -> s.toString(), equalTo("Hello from BusinessAssistant User!!!"));
	}

	@Test
	@DisplayName("Test fails get user")
	void testGetUserKo(){
		final String URI_GET_USER = "/user";
		webTestClient.get()
				.uri(CONTROLLER_BASE_URL + URI_GET_USER)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(400);
	}

	@Test
	@DisplayName("Test response get user")
	void testGetUserResponse(){
		final String URI_GET_USER = "/user?email=user@mail.com&password=abc123";
		webTestClient.get()
				.uri(CONTROLLER_BASE_URL + URI_GET_USER)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.equals("{\"uuid\": \"user_uuid\",\"email\": \"user_email\",\"role\": \"user_role\"}");
	}
}
