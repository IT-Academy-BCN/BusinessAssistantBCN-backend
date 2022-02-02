package com.businessassistantbcn.opendata.service.externaldata;


import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.CommercialGalleriesDto;
import com.businessassistantbcn.opendata.dto.economicactivitiescensus.EconomicActivitiesCensusDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class EconomicActivitiesCensusService {

    private static final Logger log = LoggerFactory.getLogger(EconomicActivitiesCensusService.class);
    @Autowired
    PropertiesConfig config;
    @Autowired
    HttpProxy httpProxy;
    @Autowired
    GenericResultDto<EconomicActivitiesCensusDto> genericResultDto;
    @Autowired
	private CircuitBreakerFactory circuitBreakerFactory;

    public Mono<GenericResultDto<EconomicActivitiesCensusDto>> getPage(int offset, int limit) {

	    URL url;
		try {
			url = new URL(config.getDs_economicactivitiescensus());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			return getPageDefault();
		}
		
		Mono<EconomicActivitiesCensusDto[]> response = httpProxy.getRequestData(url, EconomicActivitiesCensusDto[].class);

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");	
				
		return circuitBreaker.run(() -> response.flatMap(dto -> {
			EconomicActivitiesCensusDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(filteredDto);
			genericResultDto.setCount(dto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());			
		
    }
    
    
	private Mono<GenericResultDto<EconomicActivitiesCensusDto>> getPageDefault(){
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new EconomicActivitiesCensusDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}	    
    
}
