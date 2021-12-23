package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebFluxTest(OpendataController.class)
public class OpendataControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private final String CONTROLLER_BASE_URL = "/v1/api/opendata";

    @MockBean
    private BigMallsService bigMallsService;
    @MockBean
    private TestService testService;
    @MockBean
    private EconomicActivitiesCensusService economicActivitiesCensusService;
    @MockBean
    private CommercialGalleriesService commercialGalleriesService;
    @MockBean
    private LargeStablishmentsService largeStablishmentsService;

    @Test
    public void testHello(){

        final String URI_TEST = "/test";
        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(s -> s.toString(), equalTo("Hello from BusinessAssistant Barcelona!!!"));
    }
}
