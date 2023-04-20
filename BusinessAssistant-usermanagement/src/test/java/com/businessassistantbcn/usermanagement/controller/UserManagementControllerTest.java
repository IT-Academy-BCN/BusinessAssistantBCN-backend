package com.businessassistantbcn.usermanagement.controller;

import static com.businessassistantbcn.usermanagement.integration.UserManagementIntegrationTest.assertGenericResponseWithOneResult;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserManagementController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
@PropertySource("classpath:test.properties")
public class UserManagementControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	IUserManagementService userManagementService;
	
	private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/usermanagement";

	final String USER_URI = "/user";

	final String USER_ID_URI = "/user/uuid";

	final String USER_EMAIL_URI = "/user/email";

	private UserDto userDto;

	@Value("${test.json.uuidField}")
	private String uuidfield;

	@Value("${test.json.emailField}")
	private String emailField;

	@Value("${test.json.roleField}")
	private String roleField;

	@Value("${test.json.passwordField}")
	private String passwordField;




	@BeforeEach
	void setUp(){
		MockitoAnnotations.openMocks(this);
		userDto = UserDto.builder()
				.userId(UUID.randomUUID().toString())
				.userEmail("user@user.es")
				.userRoles(List.of("USER"))
				.userPassword("wwdd98e")
				.build();
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





	private WebTestClient.BodySpec<GenericResultDto, ?> assertRoleUserAndPasswordNotIncluded
			(WebTestClient.BodySpec<GenericResultDto, ?> bodySpec){
		return bodySpec.value(genericResultDto -> {
			Map<Object,Object> userDetails =
					(Map<Object, Object>) genericResultDto.getResults()[0];
			assertNull(userDetails.get(passwordField));
			List<String> roles = (List<String>) userDetails.get(roleField);
			//System.out.println(roles);
			assertEquals(1, roles.size());
			assertEquals(Role.USER.toString(), roles.get(0));
		});
	}

	@Test
	@DisplayName("Test response get user by id")
	void getUserByIdTest(){
		GenericResultDto<UserResponse> genericResult = new GenericResultDto<>(userDto);
		when(userManagementService.getUserById(any(IdOnly.class))).thenReturn(Mono.just(genericResult));

		String body = initJsonOnlyId();
		WebTestClient.ResponseSpec responseSpec = doGetRequest(USER_ID_URI,body);
		WebTestClient.BodySpec<GenericResultDto, ?> bodySpec =
				assertGenericResponseWithOneResult(responseSpec)
				.value( genericResultDto -> {
					Map<Object,Object> userDetails =
							(Map<Object, Object>) genericResultDto.getResults()[0];
					//System.out.println(userDetails);
					assertEquals(3, userDetails.size());
					assertEquals(userDto.getUserId(),userDetails.get(uuidfield));
					assertNotNull(userDetails.get(emailField));
				});
		assertRoleUserAndPasswordNotIncluded(bodySpec);
	}

	private String initJsonOnlyId(){
		return "{" +
				"\""+uuidfield +"\":\""+userDto.getUserId()+"\"" +
				"}";
	}

	private WebTestClient.ResponseSpec doGetRequest(String uri, String jsonBodyRequest){
		return webTestClient.method(HttpMethod.GET)
				.uri(CONTROLLER_BASE_URL + uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON) //if json String as body instead an instance of X.class
				.bodyValue(jsonBodyRequest)
				.exchange();
	}

	@Test
	@DisplayName("Test response get user by email")
	void getUserByEmailTest(){
		GenericResultDto<UserResponse> genericResult = new GenericResultDto<>(userDto);
		when(userManagementService.getUserByEmail(any(EmailOnly.class))).thenReturn(Mono.just(genericResult));

		String body = initJsonOnlyEmail();
		WebTestClient.ResponseSpec responseSpec = doGetRequest(USER_EMAIL_URI,body);
		WebTestClient.BodySpec<GenericResultDto, ?> bodySpec =
				assertGenericResponseWithOneResult(responseSpec)
						.value( genericResultDto -> {
							Map<Object,Object> userDetails =
									(Map<Object, Object>) genericResultDto.getResults()[0];
							//System.out.println(userDetails);
							assertEquals(3, userDetails.size());
							assertNotNull(userDetails.get(uuidfield));
							assertEquals(userDto.getUserEmail(), userDetails.get(emailField));
						});
		assertRoleUserAndPasswordNotIncluded(bodySpec);
  	}

	private String initJsonOnlyEmail(){
		return "{" +
				"\""+emailField +"\":\""+userDto.getUserEmail()+"\"" +
				"}";
	}


  @Test
  @DisplayName("Test response add new user")
  void addUserTest(){
	  GenericResultDto<UserResponse> genericResult = new GenericResultDto<>(userDto);
	  when(userManagementService.addUser(any(SingUpRequest.class))).thenReturn(Mono.just(genericResult));

	  WebTestClient.ResponseSpec responseSpec = doSingup(userDto.getUserEmail(), userDto.getUserPassword());
	  WebTestClient.BodySpec<GenericResultDto, ?> bodySpec =
			  assertGenericResponseWithOneResult(responseSpec)
					  .value( genericResultDto -> {
						  Map<Object,Object> userDetails =
								  (Map<Object, Object>) genericResultDto.getResults()[0];
						  //System.out.println(userDetails);
						  assertEquals(3, userDetails.size());
						  assertNotNull(userDetails.get(uuidfield));
						  assertEquals(userDto.getUserEmail(), userDetails.get(emailField));
					  });
	  assertRoleUserAndPasswordNotIncluded(bodySpec);
	}

	private WebTestClient.ResponseSpec doSingup(String emailExpected, String password){
		String body =initSingupJsonBody(emailExpected,password);
		return postSingup(body);
	}

	private String initSingupJsonBody(String email, String password){
		return "{" +
				"\""+emailField+"\":\""+email+"\"," +
				"\""+passwordField +"\":\""+password+"\"" +
				"}";
	}

	private WebTestClient.ResponseSpec postSingup(String jsonSingup){
		return webTestClient.post()
				.uri(CONTROLLER_BASE_URL + USER_URI)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON) //if json String as body instead an instance of X.class
				.bodyValue(jsonSingup)
				.exchange();
	}
}