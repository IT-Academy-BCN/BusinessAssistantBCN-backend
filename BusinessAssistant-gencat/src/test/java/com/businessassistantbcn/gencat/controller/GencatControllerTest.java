package com.businessassistantbcn.gencat.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = GencatController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
class GencatControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/gencat";

    @Test
    void testHello() {

        final String URI_TEST = "/test";

        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(s -> s.toString(), equalTo("Hello from GenCat Controller!!!"));
    }
}