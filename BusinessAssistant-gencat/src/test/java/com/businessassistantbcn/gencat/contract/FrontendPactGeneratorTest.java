package com.businessassistantbcn.gencat.contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class acts as a pact generator with the gencat microservice as the provider and an external
 * frontend application as its consumer
 */

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "gencat_CcaeService", port = "8762")
@PactFolder("src/test/resources/pacts")
class FrontendPactGeneratorTest {

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/gencat";

    @Pact(provider = "gencat_CcaeService", consumer = "frontend_application")
    public RequestResponsePact ccaeServerUp(PactDslWithProvider builder) throws URISyntaxException, IOException {

        Path path = Paths.get(Objects.requireNonNull(EndToEndContractTest.class.getClassLoader().getResource("json/ccaeAllResults.json")).toURI());
        String ccaeAsString = Files.readAllLines(path, StandardCharsets.UTF_8).get(0);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.given("Server up - /ccae")
                .uponReceiving("Call to /ccae - Server up")
                .path("/businessassistantbcn/api/v1/gencat/ccae")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(HttpStatus.OK.value()) //200
                .headers(headers)
                .body(ccaeAsString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "ccaeServerUp")
    void serverUpTest(MockServer mockServer) throws IOException {

        final String URI_TEST = "/ccae";

        HttpResponse response = Request.Get(mockServer.getUrl() + CONTROLLER_BASE_URL + URI_TEST).execute().returnResponse();

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
    }

    @Pact(provider = "gencat_CcaeService", consumer = "frontend_application")
    public RequestResponsePact ccaeServerDown(PactDslWithProvider builder) throws URISyntaxException, IOException {

        Path path = Paths.get(Objects.requireNonNull(EndToEndContractTest.class.getClassLoader().getResource("json/ccaeNoData.json")).toURI());
        String ccaeAsString = Files.readAllLines(path, StandardCharsets.UTF_8).get(0);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.given("Server down - /ccae")
                .uponReceiving("Call to /ccae - Server down")
                .path("/businessassistantbcn/api/v1/gencat/ccae")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(HttpStatus.OK.value()) //200
                .headers(headers)
                .body(ccaeAsString, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "ccaeServerDown")
    void serverDownTest(MockServer mockServer) throws IOException {

        final String URI_TEST = "/ccae";

        HttpResponse response = Request.Get(mockServer.getUrl() + CONTROLLER_BASE_URL + URI_TEST).execute().returnResponse();

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
    }
}
