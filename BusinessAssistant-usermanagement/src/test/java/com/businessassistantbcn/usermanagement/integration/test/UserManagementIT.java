package com.businessassistantbcn.usermanagement.integration.test;


import com.businessassistantbcn.usermanagement.controller.UserManagementController;
import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import com.businessassistantbcn.usermanagement.dto.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.UserUuidDto;
import com.businessassistantbcn.usermanagement.repository.UserManagementRepository;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserManagementIT {

    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    private MongodExecutable mongodExecutable;
    private MongoTemplate mongoTemplate;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserManagementRepository userRepository;


    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/usermanagement";

    UserEmailDto userEmailDto = new UserEmailDto();

    UserUuidDto userUuidDto = new UserUuidDto();

    @BeforeEach
    void setUp() throws IOException {
        String ip = "localhost";
        int port = 27017;

        ImmutableMongodConfig mongodConfig = MongodConfig
                .builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");


    }

    @AfterEach
    void clean(){
        userRepository.deleteAll();
        mongodExecutable.stop();
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

        //given
        String uuid_1 = "26977eee-89f8-11ec-a8a3-0242ac120002";
        String email_1 = "user1@mail.com";
        String password = "123456";
        User user1 = new User(uuid_1,email_1,password,null);
        //userRepository.save(user1);

        userEmailDto.setEmail(email_1);
        userEmailDto.setPassword(password);

        userUuidDto.setUuid(uuid_1);
        userUuidDto.setPassword(password);

        //when
        mongoTemplate.save(user1, "users");


        final String URI_GET_USER = "/user/uuid";
        webTestClient.method(HttpMethod.GET)
                .uri(CONTROLLER_BASE_URL + URI_GET_USER)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(userUuidDto), UserEmailDto.class)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(UserDto.class)
                .value(UserDto::getUuid, equalTo(userUuidDto.getUuid()));
    }

    @Test
    void test(){
        int a = 1;
        assertEquals(1, a);
    }



}
