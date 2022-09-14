package com.businessassistantbcn.opendata.service.circuitbreakerstest;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.test.ClientFlowerDTO;
import com.businessassistantbcn.opendata.exception.OpendataUnavailableServiceException;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class CircuitBreakersTestService {

    private static final Logger log = LoggerFactory.getLogger(CircuitBreakersTestService.class);

    @Autowired
    private PropertiesConfig config;

    @Autowired
    private HttpProxy httpProxy;

    @CircuitBreaker(name = "FLOWERS", fallbackMethod = "fallBack")
    public Mono<ClientFlowerDTO[]> getAllClientsFlowerProxy() throws MalformedURLException {

        return httpProxy.getRequestData(new URL(config.getDs_apitestcircuitbreakers()), ClientFlowerDTO[].class)
                .onErrorResume(throwable -> this.fallBack(new OpendataUnavailableServiceException("Server is down")));
    }

    private Mono<ClientFlowerDTO[]> fallBack(Exception e) {
        ClientFlowerDTO[] list = new ClientFlowerDTO[1];
        list[0] = (new ClientFlowerDTO("Circuit Breaker Response", "Spain"));
        log.error("BusinessAssistant error: " + e.getMessage());
        return Mono.just(list);
    }

}
