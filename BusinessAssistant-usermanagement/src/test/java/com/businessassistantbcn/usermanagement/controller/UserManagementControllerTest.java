package com.businessassistantbcn.usermanagement.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.businessassistantbcn.usermanagement.dto.output.UserDto;
import com.businessassistantbcn.usermanagement.dto.input.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.input.UserUuidDto;
import com.businessassistantbcn.usermanagement.service.UserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserManagementController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
public class UserManagementControllerTest {


	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	UserManagementService userManagementService;
	
	private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/usermanagement";

	UserEmailDto userEmailDto = new UserEmailDto();

	UserUuidDto userUuidDto = new UserUuidDto();
	UserDto userDto = new UserDto();

	@BeforeEach
	void setUp(){
		MockitoAnnotations.openMocks(this);

		userEmailDto.setEmail("pp@gmail.com");
		userEmailDto.setPassword("wwdd98e");

		userUuidDto.setUuid(UUID. randomUUID().toString());

		userDto.setEmail("user@user.es");
		userDto.setUuid("cb5f0578-6574-4e9a-977d-fca06c7cb67b");
		List<String> roles = new ArrayList<String>();
		roles.add("USER");
		userDto.setRoles(roles);
	}
	
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
	@DisplayName("Test response get user")
	void testGetUserByUuidResponse(){
		final String URI_GET_USER = "/user/uuid";
		when(userManagementService.getUserByUuid(userUuidDto)).thenReturn(Mono.just(userDto));
		webTestClient.method(HttpMethod.GET)
				.uri(CONTROLLER_BASE_URL + URI_GET_USER)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(userUuidDto), UserEmailDto.class)
				.exchange()
				.expectStatus().isOk()
				.equals(Mono.just(userDto));
	}

	@Test
	@DisplayName("Test response get user")
	void testGetUserByMailResponse(){
		final String URI_GET_USER = "/user/email";
		when(userManagementService.getUserByEmail(userEmailDto)).thenReturn(Mono.just(userDto));
		webTestClient.method(HttpMethod.GET)

				.uri(CONTROLLER_BASE_URL + URI_GET_USER)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(userEmailDto), UserEmailDto.class)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.equals(Mono.just(userDto));
  }

  @Test
  void AddUserTest(){
		final String URI_ADD_USER="/user";
		when(userManagementService.addUser(userEmailDto)).thenAnswer(x->(Mono.just(userDto)));
		webTestClient.post()
				.uri(CONTROLLER_BASE_URL + URI_ADD_USER)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(userEmailDto), UserEmailDto.class)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.equals(Mono.just(userDto));
	}
}