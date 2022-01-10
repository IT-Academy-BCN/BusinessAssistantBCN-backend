package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.economicactivitiescensus.EconomicActivitiesCensusDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class EconomicActivitiesCensusService {

    @Autowired
    PropertiesConfig config;
    @Autowired
    HttpClientHelper helper;
    @Autowired
    GenericResultDto<EconomicActivitiesCensusDto> genericResultDto;

    public Mono<GenericResultDto<EconomicActivitiesCensusDto>> getAllData() {
        Mono<EconomicActivitiesCensusDto[]> response = null;
        try {
            response = helper.getRequestData(
                    new URL(config.getDs_economicactivitiescensus()), EconomicActivitiesCensusDto[].class);
            return response.flatMap( dto -> {
                genericResultDto.setResults(dto);
                genericResultDto.setCount(dto.length);
                return Mono.just(genericResultDto);
            });

        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
        }
    }
}
