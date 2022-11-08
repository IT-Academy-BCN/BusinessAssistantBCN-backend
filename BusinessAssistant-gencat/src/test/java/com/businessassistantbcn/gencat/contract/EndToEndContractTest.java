package com.businessassistantbcn.gencat.contract;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class acts as an almost end-to-end test for all the endpoints in the gencat microservice,
 * working with a mock server that provides the required data simulating a call to the public API
 */
@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PactTestFor(providerName = "apitest-gencat-provider", port = "8760")
@PactFolder("src/test/resources/pacts")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EndToEndContractTest {

    @Autowired
    private WebTestClient webTestClient;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/gencat";
    private static final String RES0 = "$.results[0].";

    @Pact(provider = "apitest_gencat_provider", consumer = "gencat_CcaeService")
    public RequestResponsePact ccaeServerUp(PactDslWithProvider builder) throws URISyntaxException, IOException {

        Path path = Paths.get(Objects.requireNonNull(EndToEndContractTest.class.getClassLoader().getResource("json/ccaeValidData.json")).toURI());
        String ccaeAsString = Files.readAllLines(path, StandardCharsets.UTF_8).get(0);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.given("Server up - /ccae")
                .uponReceiving("Test interaction - /ccae - Server up")
                .path("/test/ccae")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .headers(headers)
                .status(HttpStatus.OK.value()) //200
                .body(ccaeAsString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    @Pact(provider = "apitest_gencat_provider", consumer = "gencat_CcaeService")
    public RequestResponsePact ccaeServerDown(PactDslWithProvider builder) {

        return builder.given("Server down - /ccae")
                .uponReceiving("Test interaction - /ccae - Server down")
                .path("/test/ccae")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()) //500
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "ccaeServerUp")
    void serverUpTest() {

        final String URI_TEST = "/ccae";

        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath("$.count").isEqualTo(1010)
                .jsonPath(RES0 + "id").isEqualTo("00000000-0000-0000-D7DC-CC770365D8FF")
                .jsonPath(RES0 + "type").isEqualTo("Secció")
                .jsonPath(RES0 + "code.idCcae").isEqualTo("A")
                .jsonPath(RES0 + "code.description").isEqualTo("Agricultura, ramaderia, silvicultura i pesca");
    }

    @Test
    @PactTestFor(pactMethod = "ccaeServerDown")
    void serverDownTest() {

        final String URI_TEST = "/ccae";

        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(0)
                .jsonPath("$.count").isEqualTo(0);
    }
}
