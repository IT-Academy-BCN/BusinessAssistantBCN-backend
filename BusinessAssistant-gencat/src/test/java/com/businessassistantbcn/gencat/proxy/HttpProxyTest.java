package com.businessassistantbcn.gencat.proxy;

import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import io.netty.channel.ChannelOption;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class HttpProxyTest {

    @Autowired
    private Environment env;

    @Autowired
    private HttpProxy httpProxy;

    public static MockWebServer mockWebServer;

    private URL url;

    private static final String JSON_FILENAME_CCAE = "json/twoCcaeData.json";

    private static String ccaeAsString;

    @BeforeAll
    static void setUp() throws IOException, URISyntaxException {

        mockWebServer = new MockWebServer();
        mockWebServer.start();

        Path path = Paths.get(Objects.requireNonNull(HttpProxyTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE)).toURI());
        ccaeAsString = Files.readAllLines(path, StandardCharsets.UTF_8).get(0);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void initialize() throws MalformedURLException {

        url = new URL(String.format("http://localhost:%s", mockWebServer.getPort()));
    }

    @DisplayName("Timeout verification")
    @Test
    void timeoutTest() {
        HttpClient client1 = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1); // Absurd 1 ms connection timeout
        WebClient briefClient = httpProxy.client.mutate()
                .clientConnector(new ReactorClientHttpConnector(client1))
                .build();
        Assertions.assertThrows(WebClientRequestException.class, () ->
                briefClient.get()
                        .uri("https://swapi.py4e.com/api/vehicles/")
                        .exchangeToMono(response ->
                                response.statusCode().equals(HttpStatus.OK) ?
                                        response.bodyToMono(CcaeDto.class) :
                                        response.createException().flatMap(Mono::error))
                        .block());
    }

    @Test
    void getRequestDataTest() {
        mockWebServer.enqueue(new MockResponse().addHeader("Content-Type", "application/json")
                                                .setBody(ccaeAsString));

        Object data = httpProxy.getRequestData(url, Object.class).block();

        assertThat(data).isNotNull();
    }

    @Test
    void getRequestDataServerIsDownTest(){
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));
        assertThrows(WebClientResponseException.class, () -> httpProxy.getRequestData(url, CcaeDto.class).block());
    }
}
