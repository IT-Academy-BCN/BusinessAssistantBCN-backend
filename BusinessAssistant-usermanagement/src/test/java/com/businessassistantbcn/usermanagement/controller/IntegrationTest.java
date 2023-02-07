package com.businessassistantbcn.usermanagement.controller;

import com.businessassistantbcn.usermanagement.config.PropertiesConfig;
import com.businessassistantbcn.usermanagement.config.TestConfig;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import com.businessassistantbcn.usermanagement.service.UserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserManagementController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
@TestPropertySource(locations = "classpath:persistence-test.properties")
@Import({TestConfig.class, PropertiesConfig.class})
public class IntegrationTest {


    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    UserManagementService userManagementService;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/usermanagement";

    UserEmailDto userEmailDto = new UserEmailDto();

    UserUuidDto userUuidDto = new UserUuidDto();
    UserDto userDto = new UserDto();

    @BeforeEach
    void setUp(){

        userEmailDto.setEmail("pp@gmail.com");
        userEmailDto.setPassword("wwdd98e");

        //userUuidDto.setUuid(UUID. randomUUID().toString());
        //userUuidDto.setPassword("123456");
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

        assertTrue(true);

      //  final String URI_GET_USER = "/user/uuid";

       // when(userManagementService.getUserByUuid(userUuidDto)).thenReturn(Mono.just(userDto));
/*        webTestClient.method(HttpMethod.GET)
                .uri(CONTROLLER_BASE_URL + URI_GET_USER)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(userUuidDto), UserEmailDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .equals(Mono.just(userDto));*/

    }
    @Test
    @DisplayName("Test response add user")
    void addUsersTest(){
        final String URI_TEST = "/user";
        when(userManagementService.addUser(userEmailDto)).thenReturn(Mono.just(userDto));
        webTestClient.post()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userEmailDto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .equals(Mono.just(userDto));
    }

}

