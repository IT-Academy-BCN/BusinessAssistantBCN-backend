package com.businessassistantbcn.gencat.controller;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Mono;

import java.io.*;
import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = GencatController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
class GencatControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    @Autowired
    private HttpProxy httpProxy;

    @MockBean
    @Autowired
    private PropertiesConfig config;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/gencat";
    private static String urlEconomicActivitiesString;
    private static final String JSON_FILENAME = "/get5-imi7.json";
    private static ObjectMapper mapper;
    private static Object[] economicActivities;


    @BeforeEach
    void setUp() throws IOException {
        urlEconomicActivitiesString = "https://analisi.transparenciacatalunya.cat/resource/get5-imi7.json";
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File file = ResourceUtils.getFile(this.getClass().getResource(JSON_FILENAME));
        economicActivities = mapper.readValue(file, Object[].class);
        System.out.println(economicActivities);
    }

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

    //Una vez implementado correctamente el método, el test se debe adecuar
    @Test
    void getAllEconomicActivities() throws IOException {
        when(config.getDs_economicActivities()).thenReturn(urlEconomicActivitiesString);
        when(httpProxy.getRequestData(any(URL.class), eq(Object[].class))).thenReturn((Mono.just(economicActivities)));

        final String URI_TEST = "/ccae";

        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        verify(config, times(1)).getDs_economicActivities();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(Object[].class));
    }

    //Una vez implementado correctamente el método, el test se debe adecuar
    @Test
    void getEconomicActivityById() {
        final String URI_TEST = "/ccae/1";
        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_IMPLEMENTED);
    }

    //Una vez implementado correctamente el método, el test se debe adecuar
    @Test
    void getEconomicActivityType() {
        final String URI_TEST = "/ccae/type/1";
        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_IMPLEMENTED);
    }

    //Una vez implementado correctamente el método, el test se debe adecuar
    @Test
    void getEconomicActivitiesTypes() {
        final String URI_TEST = "/ccae/types";
        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_IMPLEMENTED);

    }

}