package com.businessassistantbcn.opendata.service;


import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.economicactivitiescensus.EconomicActivitiesCensusDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class EconomicActivitiesCensusService {

    private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);
    @Autowired
    PropertiesConfig config;
    @Autowired
    HttpClientHelper httpClientHelper;
    @Autowired
    GenericResultDto<EconomicActivitiesCensusDto> genericResultDto;

    public Mono<GenericResultDto<EconomicActivitiesCensusDto>> getPage(int offset, int limit) {
        try {
            Mono<EconomicActivitiesCensusDto[]> response = httpClientHelper.getRequestData(
                    new URL(config.getDs_economicactivitiescensus()), EconomicActivitiesCensusDto[].class);
                return response.flatMap( dto -> {
                    try{
                        EconomicActivitiesCensusDto[] filteredDto = JsonHelper.filterDto(dto,offset,limit);
                        genericResultDto.setLimit(limit);
                        genericResultDto.setOffset(offset);
                        genericResultDto.setResults(filteredDto);
                        genericResultDto.setCount(dto.length);
                        return Mono.just(genericResultDto);
                     } catch (Exception e){
                        log.error("Error con el filtrado de EconomicAtivitiesCensus" +  e.getMessage());
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                }
            });
        } catch (MalformedURLException mue) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }
}
