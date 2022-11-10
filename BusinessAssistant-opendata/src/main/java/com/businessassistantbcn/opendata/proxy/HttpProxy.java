package com.businessassistantbcn.opendata.proxy;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.apache.commons.validator.routines.UrlValidator;
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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Optional;
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

    public <T> Mono<T> getRequestData(URI uri, Class<T> clazz) {

        //Checks if given uri is a valid URL, including localhost
        UrlValidator validator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);

        if (validator.isValid(uri.toString())) {
            log.info("Proxy: Executing remote invocation to " + uri);
            WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
            WebClient.RequestBodySpec bodySpec = uriSpec.uri(uri);
            return bodySpec.retrieve().bodyToMono(clazz);
        } else if (uri.toString().startsWith("backup/opendata/")){
            log.info("Proxy: Executing local invocation to " + uri);
            Optional<T> result = jsonLoader(uri, clazz);
            return result.map(Mono::just).orElseGet(Mono::empty);
        } else {
            log.error("Invalid resource URI: " + uri);
            return Mono.empty();
        }
    }

    private <T> Optional<T> jsonLoader(URI uri, Class<T> clazz) {

        String absolutePath = new File(uri.toString()).getAbsolutePath();

        String fileString;
        try {
            fileString = Files.readAllLines(Path.of(absolutePath), StandardCharsets.UTF_8).get(0);
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Optional.of(mapper.readValue(fileString, clazz));
        } catch (IOException e) {
            log.error("IOError: not able to find file " + uri);
            return Optional.empty();
        }
    }
}

