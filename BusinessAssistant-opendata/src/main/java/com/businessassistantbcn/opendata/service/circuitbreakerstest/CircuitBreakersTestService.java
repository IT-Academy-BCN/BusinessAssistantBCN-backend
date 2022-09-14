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

    @CircuitBreaker(name = "babcn-circuitBreaker", fallbackMethod = "fallBack")
    public Mono<ClientFlowerDTO[]> getAllClientsFlowerProxy() throws MalformedURLException {
        return httpProxy.getRequestData(new URL(config.getDs_apitestcircuitbreakers()), ClientFlowerDTO[].class);
    }

    private Mono<ClientFlowerDTO[]> fallBack(Exception e) {
        log.error("BusinessAssistant error: " + e.getMessage());
        ClientFlowerDTO[] list = new ClientFlowerDTO[1];
        list[0] = (new ClientFlowerDTO("Circuit Breaker Response", "Spain"));
        return Mono.just(list);
    }

}
