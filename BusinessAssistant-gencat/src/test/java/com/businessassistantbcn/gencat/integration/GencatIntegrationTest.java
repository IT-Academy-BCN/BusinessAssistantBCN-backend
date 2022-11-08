package com.businessassistantbcn.gencat.integration;

import com.businessassistantbcn.gencat.proxy.HttpProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class GencatIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    @Autowired
    private HttpProxy httpProxy;

    private final String CONTROLLER_BASE_URL = "/businessassistantbcn/api/v1/gencat";
    private static final String RES0 = "$.results[0].";

    private static final String JSON_FILENAME_CCAE = "json/twoCcaeData.json";
    private static String ccaeAsString;

    private static ObjectMapper mapper;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {

        Path path = Paths.get(Objects.requireNonNull(GencatIntegrationTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE)).toURI());
        ccaeAsString = Files.readAllLines(path, StandardCharsets.UTF_8).get(0);

        mapper = new ObjectMapper();
    }

    @Test
    void ccaeTest() throws IOException {

        final String URI_TEST = "/ccae";

        Object data = mapper.readValue(ccaeAsString, Object.class);

        when(httpProxy.getRequestData(any(URL.class), eq(Object.class))).thenReturn(Mono.just(data));

        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(-1)
                .jsonPath("$.count").isEqualTo(2)
                .jsonPath(RES0 + "id").isEqualTo("00000000-0000-0000-D7DC-CC770365D8FF")
                .jsonPath(RES0 + "type").isEqualTo("Secció")
                .jsonPath(RES0 + "code.idCcae").isEqualTo("A")
                .jsonPath(RES0 + "code.description").isEqualTo("Agricultura, ramaderia, silvicultura i pesca")
                .consumeWith(System.out::println);
    }

    @Test
    void ccaeServerErrorTest() {

        final String URI_TEST = "/ccae";

        when(httpProxy.getRequestData(any(URL.class), eq(Object.class))).thenReturn(Mono.error(new RuntimeException()));

        webTestClient.get()
                .uri(CONTROLLER_BASE_URL + URI_TEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.offset").isEqualTo(0)
                .jsonPath("$.limit").isEqualTo(0)
                .jsonPath("$.count").isEqualTo(0)
                .consumeWith(System.out::println);
    }
}
