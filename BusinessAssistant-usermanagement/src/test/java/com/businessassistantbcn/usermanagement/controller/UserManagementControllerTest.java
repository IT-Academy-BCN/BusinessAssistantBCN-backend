package com.businessassistantbcn.usermanagement.controller;

import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import com.businessassistantbcn.usermanagement.service.UserManagementService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserManagementController.class,  excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
//@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class UserManagementControllerTest {

    @MockBean
    UserManagementService userManagementService;

    @Autowired
    private WebTestClient webClient;
    
    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/usermanagement";
    
    
    @DisplayName("Test response") 
    @Test
    void test_test() {
    
    final String URI_TEST = "/test";
		
		webClient.get()
				 .uri(CONTROLLER_BASE_URL + URI_TEST)
				 .accept(MediaType.APPLICATION_JSON)
				 .exchange()
				 .expectStatus().isOk()
				 .expectBody(String.class)
				 .value(s -> s.toString(), equalTo("Hello from BusinessAssistant User!!!"));  	
    	
    }

    @Test
    void test_getUserByUuid() {
    	final String URI_TEST = "/userUuid/";
    	List<String> stringRoles = new ArrayList<String>(); 
		stringRoles.add("USER");
				
		Mono<UserUuidDto> userUuidDto = Mono.just(new UserUuidDto("f14b08e3-d79a-4c64-a1f6-aa333ef2ee37", "123"));
		Mono<UserDto> userDto = Mono.just(new UserDto("f14b08e3-d79a-4c64-a1f6-aa333ef2ee37", "user@user.com", stringRoles));	
						
		Mockito.when(userManagementService.getUserByUuid("f14b08e3-d79a-4c64-a1f6-aa333ef2ee37"))
		       .thenReturn(userDto);
		
			
		webClient.get()
		         //.uri(CONTROLLER_BASE_URL + URI_TEST, userUuidDto)
		         .uri(uriBuilder -> uriBuilder.path(CONTROLLER_BASE_URL + URI_TEST).build(userUuidDto))
		         .accept(MediaType.APPLICATION_JSON)
		         .exchange()
		         .expectStatus().isOk()
		         .expectHeader().contentType(MediaType.APPLICATION_JSON)
		         .expectBody()
		         .jsonPath("$.uuid").isEqualTo("f14b08e3-d79a-4c64-a1f6-aa333ef2ee37");
    }

//    void test_createUser() {
//
//    }

//    void test_getUserByEmail(){
//    	List<Role> roles;
//        List<String> stringRoles;
//        List<User> users = new ArrayList<User>();
//
//        User user = new User("f14b08e3-d79a-4c64-a1f6-aa333ef2ee37", "user@user.com", "123", roles);
//        UserDto userDto = new UserDto("f14b08e3-d79a-4c64-a1f6-aa333ef2ee37", "user@user.com", stringRoles);
//        users.add(user);
//
//        //quitar FLUX
//        Flux<User> userFlux = Flux.fromIterable(users);
//
//        Mockito.when(userManagementService.getUserByEmail("user@user.com"))
//               .thenReturn(userFlux);
//        
//        Mockito.when(userManagementService.convertToDto(user))
//               .thenReturn(userDto);
//
//        webClient.get()
//                 .uri("/user/{email}", "user@user.com")
//                 //.header(HttpHeaders.ACCEPT, "application/json")
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectBodyList(UserDto.class);
//    }*/

}
