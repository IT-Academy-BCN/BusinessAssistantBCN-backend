package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.economicactivitiescensus.EconomicActivitiesCensusDTO;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    GenericResultDto<EconomicActivitiesCensusDTO> genericResultDto;

    /**
     *
     * @param <T>
     * @return
     * @throws MalformedURLException
     */
    public <T> Mono<T> getOriginalData()throws MalformedURLException{
        return helper.getRequestData(new URL(config.getDs_economicactivitiescensus()), EconomicActivitiesCensusDTO[].class);
    }


    public Mono<GenericResultDto<EconomicActivitiesCensusDTO>> getAllData()throws MalformedURLException {
        Mono<EconomicActivitiesCensusDTO[]> response = helper.getRequestData(
                new URL(config.getDs_economicactivitiescensus()), EconomicActivitiesCensusDTO[].class);
        return response.flatMap( dto -> {
            genericResultDto.setResults(dto);
            genericResultDto.setCount(dto.length);
            return Mono.just(genericResultDto);
        });
    }

}
