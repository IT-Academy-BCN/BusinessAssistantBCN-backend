package com.businessassistantbcn.opendata.helper;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResult;
import com.fasterxml.jackson.databind.JsonNode;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.net.URL;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class HttpClientHelper {

    private final PropertiesConfig config;
    HttpClient httpClient;
    WebClient client;

    /**
     * Inicialización de objetos de conexión en constructor
     */
    @Autowired
    public HttpClientHelper(PropertiesConfig config){
        this.config = config;
        httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(Long.parseLong(config.getConnection_timeout())))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(Long.parseLong(config.getConnection_timeout()), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(Long.parseLong(config.getConnection_timeout()), TimeUnit.MILLISECONDS)));

        client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

    }

    public String getStringRoot(URL url){
        return "";
    }


    public JsonNode getJsonRoot(URL url) {
        return null;
    }


    public Object getObjectRoot (URL url, Class clazz){

        return null;
    }

    /**
     * Test de conexion a URL externa. Tipos de retorno parametrizados
     * OJO RestTemplate pronto deprecada (ES BLOCKING), preferible uso de WebClient (reactive programming)
     * {@link} https://www.baeldung.com/spring-5-webclient
     * {@link} https://www.baeldung.com/spring-webclient-json-list
     */
    @SuppressWarnings("unchecked")
    public <T> Mono<T> getTestRequest(Class clazz){
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(URI.create(config.getDs_test()));

        //response.subscribe( value -> System.out.println(value));
        //response.subscribe(System.out::println);
        //return bodySpec.retrieve().bodyToMono(String.class);
        return bodySpec.retrieve().bodyToMono(clazz);
    }

}
