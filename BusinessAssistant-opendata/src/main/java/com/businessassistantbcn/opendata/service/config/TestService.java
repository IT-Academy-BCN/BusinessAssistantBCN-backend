package com.businessassistantbcn.opendata.service.config;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TestService {

    @Autowired
    PropertiesConfig config;

    @Autowired
    HttpClientHelper helper;

    public Mono<StarWarsVehiclesResultDto> getTestData() throws MalformedURLException{
           return helper.getRequestData(new URL(config.getDs_test()), StarWarsVehiclesResultDto.class);
    }

}
