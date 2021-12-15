package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsResponseDto;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class BigMallsService {
    @Autowired
    PropertiesConfig config;

    @Autowired
    HttpClientHelper helper;

    public <T> Mono<T> getBigmallsData()throws MalformedURLException {

        return helper.getRequestData(new URL(config.getDs_bigmalls()), BigMallsDto[].class);

    }
}
