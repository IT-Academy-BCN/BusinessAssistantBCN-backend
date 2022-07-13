/*package com.businessassistantbcn.usermanagement.integration.test;


import com.businessassistantbcn.usermanagement.controller.UserManagementController;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class UserManagementIT1 {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserManagementRepository userRepository;


    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/usermanagement";

    UserEmailDto userEmailDto = new UserEmailDto();

    UserUuidDto userUuidDto = new UserUuidDto();

    @BeforeEach
    void setUp() {
        String uuid_1 = "26977eee-89f8-11ec-a8a3-0242ac120002";
        String email_1 = "user1@mail.com";
        String password = "123456";
        //String uuid_2 = "26977eee-89f8-11ec-a8a3-0242ac120003";
        //String email_2 = "user2@mail.com";
        User user1 = new User(uuid_1,email_1,password,null);
        //User user2 = new User(uuid_2,email_2,"abc123",null);
        userRepository.save(user1);

        userEmailDto.setEmail(email_1);
        userEmailDto.setPassword(password);

        userUuidDto.setUuid(uuid_1);
        userUuidDto.setPassword(password);
    }

    @AfterEach
    void deleteDb(){
        userRepository.deleteAll();
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
                .value(s -> s, equalTo("Hello from BusinessAssistant User!!!"));
    }

    @Test
    @DisplayName("Test response get user")
    void testGetUserByUuidResponse(){
        final String URI_GET_USER = "/user/uuid";
        webTestClient.method(HttpMethod.GET)
                .uri(CONTROLLER_BASE_URL + URI_GET_USER)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(userUuidDto), UserEmailDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(UserDto::getUuid, equalTo(userUuidDto.getUuid()));
    }

    @Test
    void test(){
        int a = 1;
        assertEquals(1, a);
    }



}*/
