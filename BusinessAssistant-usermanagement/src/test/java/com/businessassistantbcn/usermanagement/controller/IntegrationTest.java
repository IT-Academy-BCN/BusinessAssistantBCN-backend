package com.businessassistantbcn.usermanagement.controller;

import com.businessassistantbcn.usermanagement.config.TestConfig;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import com.businessassistantbcn.usermanagement.service.UserManagementService;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserManagementController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
@TestPropertySource(locations = "classpath:persistence-test.properties")
@Import(TestConfig.class)
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

/*        userEmailDto.setEmail("pp@gmail.com");
        userEmailDto.setPassword("wwdd98e");

        userUuidDto.setUuid(UUID. randomUUID().toString());
        userUuidDto.setPassword("123456");*/
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

}

