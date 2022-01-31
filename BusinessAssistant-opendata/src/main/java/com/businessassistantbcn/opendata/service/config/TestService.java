package com.businessassistantbcn.opendata.service.config;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import java.net.MalformedURLException;
import java.net.URL;

import com.businessassistantbcn.opendata.proxy.HttpProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TestService {

    @Autowired
    PropertiesConfig config;

    @Autowired
    HttpProxy httpProxy;

    public Mono<StarWarsVehiclesResultDto> getTestData() throws MalformedURLException{
           return httpProxy.getRequestData(new URL(config.getDs_test()), StarWarsVehiclesResultDto.class);
    }

}
