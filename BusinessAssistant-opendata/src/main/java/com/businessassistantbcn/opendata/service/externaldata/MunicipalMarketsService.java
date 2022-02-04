package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.municipalmarkets.MunicipalMarketsDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MunicipalMarketsService {

	private static final Logger log = LoggerFactory.getLogger(MunicipalMarketsService.class);
	
    @Autowired
    private PropertiesConfig config;

    @Autowired
    private HttpProxy httpProxy;

    @Autowired
    private GenericResultDto<MunicipalMarketsDto> genericResultDto;

    @Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
    
    public Mono<GenericResultDto<MunicipalMarketsDto>> getPage(int offset, int limit) {

    	URL url;

		try {
			url = new URL(config.getDs_municipalmarkets());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			return getPageDefault();
		}

		Mono<MunicipalMarketsDto[]> response = httpProxy.getRequestData(url, MunicipalMarketsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

		return circuitBreaker.run(() -> response.flatMap(dto -> {
			MunicipalMarketsDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(filteredDto);
			//genericResultDto.setResults(Arrays.stream(filteredDto).filter(x->"01".equals(x.getDistrict_id())));
			genericResultDto.setCount(dto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());
    }

    private Mono<GenericResultDto<MunicipalMarketsDto>> getPageDefault(){
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new MunicipalMarketsDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}
}
