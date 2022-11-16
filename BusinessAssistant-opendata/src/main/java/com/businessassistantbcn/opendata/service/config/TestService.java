package com.businessassistantbcn.opendata.service.config;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class TestService {

    @Autowired
    PropertiesConfig config;

    @Autowired
    HttpProxy httpProxy;

    public Mono<StarWarsVehiclesResultDto> getTestData() {
           return httpProxy.getRequestData(URI.create(config.getDs_test()), StarWarsVehiclesResultDto.class);
    }

}
