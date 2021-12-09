package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TestService {

    @Autowired
    HttpClientHelper helper;

    public <T> Mono<T> getTestData(){
        return helper.getTestRequest(StarWarsVehiclesResultDto.class);
    }

}
