package com.businessassistantbcn.usermanagement.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserManagementController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
public class UserManagementControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	UserManagementService userManagementService;
	
	private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/usermanagement";

	UserEmailDto userEmailDto = new UserEmailDto();
	UserDto userDto = new UserDto();

	@BeforeEach
	void setUp(){
		MockitoAnnotations.openMocks(this);

		userEmailDto.setEmail("pp@gmail.com");
		userEmailDto.setPassword("wwdd98e");
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
	public void AddUserTest(){

		final String URI_ADD_USER="/user";

		when(userManagementService.addUser(Mono.just(userEmailDto))).thenReturn(Mono.just(userDto));

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
