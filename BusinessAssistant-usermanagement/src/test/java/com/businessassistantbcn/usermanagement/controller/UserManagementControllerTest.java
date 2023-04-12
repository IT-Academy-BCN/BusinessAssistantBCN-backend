package com.businessassistantbcn.usermanagement.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.dto.input.EmailOnly;
import com.businessassistantbcn.usermanagement.dto.input.IdOnly;
import com.businessassistantbcn.usermanagement.dto.input.SingUpRequest;
import com.businessassistantbcn.usermanagement.dto.io.UserDto;
import com.businessassistantbcn.usermanagement.dto.output.GenericResultDto;
import com.businessassistantbcn.usermanagement.dto.output.UserResponse;
import com.businessassistantbcn.usermanagement.service.IUserManagementService;
import org.junit.jupiter.api.Assertions;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserManagementController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
public class UserManagementControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	IUserManagementService userManagementService;
	
	private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/usermanagement";

	UserDto userDto = new UserDto();

	private final String idField = "user_uuid";
	private final String emailField = "user_email";

	private final String passwordField = "user_password";

	private final String roleField = "user_role";


	@BeforeEach
	void setUp(){
		MockitoAnnotations.openMocks(this);
		userDto = new UserDto("cb5f0578-6574-4e9a-977d-fca06c7cb67b","user@user.es",
				List.of("USER"),"wwdd98e");
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
	@DisplayName("Test response get user by id")
	void getUserByIdTest(){
		final String URI_GET_USER = "/user/uuid";
		String idExpexted = userDto.getUserId();
		String body = "{" +
				"\""+idField +"\":\""+idExpexted+"\"" +
				"}";

		IdOnly idOnly =  new UserDto(idExpexted,null,null,null);
		UserResponse userResponse = userDto;
		GenericResultDto<UserResponse> genericResult = new GenericResultDto<>(userResponse);
		when(userManagementService.getUserById(idOnly)).thenReturn(Mono.just(genericResult));
		webTestClient.method(HttpMethod.GET)
				.uri(CONTROLLER_BASE_URL + URI_GET_USER)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON) //if json String as body instead an instance of X.class
				.bodyValue(body)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody(GenericResultDto.class)
				.value(Assertions::assertNotNull)
				.value( genericResultDto -> {
					Assertions.assertEquals(1, genericResultDto.getCount());
					assertNotNull(genericResultDto.getResults());
					Assertions.assertEquals(1 , genericResultDto.getResults().length);
					Map<Object,Object> userDetails =
							(Map<Object, Object>) genericResultDto.getResults()[0];
					//System.out.println(userDetails);
					assertTrue(userDetails.size() == 3);
					assertEquals(idExpexted,userDetails.get(idField));
					assertNull(userDetails.get(passwordField));
					assertNotNull(userDetails.get(emailField));
					List<String> roles = (List<String>) userDetails.get(roleField);
					//System.out.println(roles);
					assertTrue(roles.size()==1);
					assertEquals(Role.USER.toString(), roles.get(0));
				});
	}

	@Test
	@DisplayName("Test response get user by email")
	void getUserByEmailTest(){
		final String URI_GET_USER = "/user/email";
		String emailExpected = userDto.getUserEmail();
		String body = "{" +
				"\""+emailField +"\":\""+emailExpected+"\"" +
				"}";

		EmailOnly emailOnly =  new UserDto(null,emailExpected,null,null);
		UserResponse userResponse = userDto;
		GenericResultDto<UserResponse> genericResult = new GenericResultDto<>(userResponse);
		when(userManagementService.getUserByEmail(emailOnly)).thenReturn(Mono.just(genericResult));
		webTestClient.method(HttpMethod.GET)
				.uri(CONTROLLER_BASE_URL + URI_GET_USER)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON) //if json String as body instead an instance of X.class
				.bodyValue(body)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody(GenericResultDto.class)
				.value(Assertions::assertNotNull)
				.value( genericResultDto -> {
					Assertions.assertEquals(1, genericResultDto.getCount());
					assertNotNull(genericResultDto.getResults());
					Assertions.assertEquals(1 , genericResultDto.getResults().length);
					//results field is generic -> returns an array of objects
					//each element is Map instance
					Map<Object,Object> userDetails =
							(Map<Object, Object>) genericResultDto.getResults()[0];
					//System.out.println(userDetails);
					assertTrue(userDetails.size() == 3);
					assertNotNull(userDetails.get(idField));
					assertNull(userDetails.get(passwordField));
					assertEquals(emailExpected, userDetails.get(emailField));
					List<String> roles = (List<String>) userDetails.get(roleField);
					//System.out.println(roles);
					assertTrue(roles.size()==1);
					assertEquals(Role.USER.toString(), roles.get(0));
				});
  }

  @Test
  @DisplayName("Test response add new user")
  void addUserTest(){
		final String URI_ADD_USER="/user";
		String emailExpected = userDto.getUserEmail();
		String password = userDto.getUserPassword();
	  	String body = "{" +
			  "\""+emailField+"\":\""+emailExpected+"\"," +
			  "\""+passwordField +"\":\""+password+"\"" +
			  "}";
		UserResponse userResponse = userDto;
	  	GenericResultDto<UserResponse> genericResult = new GenericResultDto<>(userResponse);
	  	when(userManagementService.addUser(any(SingUpRequest.class))).thenReturn(Mono.just(genericResult));
	  	webTestClient.method(HttpMethod.POST)
			  .uri(CONTROLLER_BASE_URL + URI_ADD_USER)
			  .accept(MediaType.APPLICATION_JSON)
			  .contentType(MediaType.APPLICATION_JSON) //if json String as body instead an instance of X.class
			  .bodyValue(body)
			  .exchange()
			  .expectStatus().isOk()
			  .expectHeader().contentType(MediaType.APPLICATION_JSON)
			  .expectBody(GenericResultDto.class)
			  .value(Assertions::assertNotNull)
			  .value( genericResultDto -> {
				  Assertions.assertEquals(1, genericResultDto.getCount());
				  Assertions.assertEquals(1 , genericResultDto.getResults().length);
				  //results field is generic -> returns an array of objects
				  //each element is Map instance
				  Map<Object,Object> userDetails =
						  (Map<Object, Object>) genericResultDto.getResults()[0];
				  //System.out.println(userDetails);
				  assertTrue(userDetails.size() == 3);
				  assertNotNull(userDetails.get(idField));
				  assertNull(userDetails.get(passwordField));
				  assertEquals(emailExpected, userDetails.get(emailField));
				  List<String> roles = (List<String>) userDetails.get(roleField);
				  //System.out.println(roles);
				  assertTrue(roles.size()==1);
				  assertEquals(Role.USER.toString(), roles.get(0));
			  });
	}
}