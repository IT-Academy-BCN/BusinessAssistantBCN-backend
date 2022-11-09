package com.businessassistantbcn.opendata.proxy;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class HttpProxy {

    private final PropertiesConfig config;
    HttpClient httpClient;
    public WebClient client;

    private static final Logger log = LoggerFactory.getLogger(HttpProxy.class);

    @Autowired
    public HttpProxy(PropertiesConfig config) {
        this.config = config;
        httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnection_timeout())
                .responseTimeout(Duration.ofMillis(config.getConnection_timeout()))
                .compress(true)
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(config.getConnection_timeout(), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(config.getConnection_timeout(), TimeUnit.MILLISECONDS)));

        client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(ExchangeStrategies.builder().codecs(this::acceptedCodecs).build())
                .build();

    }

    private void acceptedCodecs(ClientCodecConfigurer clientCodecConfigurer) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        clientCodecConfigurer.defaultCodecs().maxInMemorySize(config.getMaxBytesInMemory());
        clientCodecConfigurer.customCodecs().registerWithDefaultConfig(new Jackson2JsonDecoder(mapper, MediaType.TEXT_PLAIN));
    }

    public <T> Mono<T> getRequestData(URL url, Class<T> clazz) {
        log.info("Proxy: Executing remote invocation to " + url.toString());
        if (url.toString().startsWith("http:///")) {
            return Mono.just(jsonLoader(url.toString(), clazz));
        } else {
            WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
            WebClient.RequestBodySpec bodySpec = uriSpec.uri(URI.create(url.toString()));
            return bodySpec.retrieve().bodyToMono(clazz);
        }
    }

    private <T> T jsonLoader(String resourceURI, Class<T> clazz) {
        URL resourceURL = this.getClass().getClassLoader().getResource(resourceURI.replace("http:/", ""));
        String fileString;
        try {
            fileString = Files.readAllLines(Path.of(Objects.requireNonNull(resourceURL).toURI()), StandardCharsets.UTF_8).get(0);
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(fileString, clazz);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

