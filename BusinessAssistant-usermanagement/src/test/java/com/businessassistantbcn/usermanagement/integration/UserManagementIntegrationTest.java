package com.businessassistantbcn.usermanagement.integration;

import com.businessassistantbcn.usermanagement.config.PropertiesConfig;
import com.businessassistantbcn.usermanagement.document.Role;
import com.businessassistantbcn.usermanagement.dto.output.GenericResultDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@PropertySource("classpath:test.properties")
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserManagementIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Value("${test.json.uuidField}")
    private String uuidfield;

    @Value("${test.json.emailField}")
    private String emailField;

    @Value("${test.json.roleField}")
    private String roleField;

    @Value("${test.json.passwordField}")
    private String passwordField;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/usermanagement";

    //TODO - DEBUG trace: 'Failed to close the response', see https://github.com/testcontainers/testcontainers-java/issues/6420
    @Container
    static MongoDBContainer container = new MongoDBContainer("mongo")
            .withStartupTimeout(Duration.ofSeconds(60));

    @Autowired
    PropertiesConfig config;

    //@Autowired
    //UserManagementRepository repo; //if is needed for check initial user registered

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
    @DisplayName("Integration test add user")
    void addUsersTest(){
        int maxUsers = config.getMaxusers();
        String email = "user@gmail.com";
        String password = "whatever";
        assertSuccessSingupCheckingEmail(email,doSingup(email,password));
        for(int i = 1; i < maxUsers ; i++){ //assert max users is reached
            //System.out.println(i);
            doSingup(i+email,password);
        }
        assertLimitDBReached(doSingup(email, password));
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
        final String URI_ADD_USER = "/user";
        return webTestClient.post()
                .uri(CONTROLLER_BASE_URL + URI_ADD_USER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON) //if json String as body instead an instance of X.class
                .bodyValue(jsonSingup)
                .exchange();
    }

    private void assertSuccessSingupCheckingEmail(String emailExpected, WebTestClient.ResponseSpec responseSpec){
        WebTestClient.BodySpec<GenericResultDto, ?> body = assertGenericResponseWithOneResult(responseSpec);
        String expectedRole = Role.USER.toString();
        body.value( genericResultDto -> {
            Map<Object,Object> userDetails =
                    (Map<Object, Object>) genericResultDto.getResults()[0];
            assertEquals(3, userDetails.size());
            assertNotNull(userDetails.get(uuidfield));
            assertEquals(emailExpected, userDetails.get(emailField));
            assertNull(userDetails.get(passwordField));
            List<String> roles = (List<String>) userDetails.get(roleField);
            //System.out.println(roles);
            assertEquals(1,roles.size());
            assertEquals(expectedRole, roles.get(0));
        });
    }

    public static WebTestClient.BodySpec<GenericResultDto, ?>
        assertGenericResponseWithOneResult(WebTestClient.ResponseSpec responseSpec){

        return responseSpec.expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(GenericResultDto.class)
                .value(Assertions::assertNotNull)
                .value( genericResultDto -> {
                    //System.out.println(genericResultDto);
                    assertEquals(1, genericResultDto.getCount());
                    assertEquals(1, genericResultDto.getResults().length);
                });
    }

    private void assertLimitDBReached(WebTestClient.ResponseSpec responseSpec){
        WebTestClient.BodySpec<GenericResultDto, ?> body =
                assertGenericResponseWithOneResult(responseSpec);
        body.value(genericResultDto -> {
            Map<Object,Object> error =
                    (Map<Object, Object>) genericResultDto.getResults()[0];
            assertEquals(1, error.size());
            assertEquals(config.getErrorLimitDb(), error.get("errorMessage"));
        });
    }



}
