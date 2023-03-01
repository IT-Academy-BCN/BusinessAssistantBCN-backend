package com.businessassistantbcn.usermanagement.integration;

import com.businessassistantbcn.usermanagement.dto.input.UserEmailDto;
import com.businessassistantbcn.usermanagement.dto.output.ErrorDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@PropertySource("classpath:persistence-test.properties")
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserManagementIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/usermanagement";

    //TODO - DEBUG trace: 'Failed to close the response', see https://github.com/testcontainers/testcontainers-java/issues/6420
    @Container
    static MongoDBContainer container = new MongoDBContainer("mongo")
            .withStartupTimeout(Duration.ofSeconds(60));

    @DynamicPropertySource
    static void initMongoProperties(DynamicPropertyRegistry registry) {
        System.out.println("container url: {}" + container.getReplicaSetUrl("babcn-users"));
        System.out.println("container host/port: {}/{}" + container.getHost() + " - " + container.getFirstMappedPort());

        registry.add("spring.data.mongodb.uri", () -> container.getReplicaSetUrl("babcn-users"));
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
    void addUserTest(){
        final String URI_ADD_USER = "/user";
        UserEmailDto userEmailDto1 = new UserEmailDto("user@user.com", "user");
        ErrorDto errorDto = new ErrorDto("Users limit on database");
        for(int i = 0; i < 200; i++){
            UserEmailDto userEmailDto = new UserEmailDto("user"+i+"@gmail.com", "user"+i);
            webTestClient.post()
                    .uri(CONTROLLER_BASE_URL + URI_ADD_USER)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(userEmailDto), UserEmailDto.class)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.email").isEqualTo("user"+i+"@gmail.com")
                    .equals(Mono.just(userEmailDto).block());
        }

        webTestClient.post()
                .uri(CONTROLLER_BASE_URL + URI_ADD_USER)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(userEmailDto1), UserEmailDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Users limit on database")
                .equals(Mono.just(errorDto).block());
    }

}
