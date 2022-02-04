package com.businessassistantbcn.opendata.service.externaldata;

import java.net.MalformedURLException;
import java.net.URL;

import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.CommercialGalleriesDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.businessassistantbcn.opendata.config.PropertiesConfig;

import com.businessassistantbcn.opendata.helper.JsonHelper;

import reactor.core.publisher.Mono;

@Service
public class CommercialGalleriesService {
	
	private static final Logger log = LoggerFactory.getLogger(CommercialGalleriesService.class);
	
	@Autowired
	HttpProxy httpProxy;
	
	@Autowired
	private PropertiesConfig config;
	
	@Autowired
	private GenericResultDto<CommercialGalleriesDto> genericResultDto;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;	
	
	//String url = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/opendatabcn_mercats-centrescomercials_galeries-comercials-js.json";
	//String url = "https://api.github.com";
	
	public Mono<GenericResultDto<CommercialGalleriesDto>> getCommercialGalleriesAll()
	{
	    URL url;
		try {
			url = new URL(config.getDs_commercialgalleries());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			return getPageDefault();
		}		
		
		Mono<CommercialGalleriesDto[]> response = httpProxy.getRequestData(url, CommercialGalleriesDto[].class);

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");			
			
		int offset = 0;
        int limit  = 0;
		return circuitBreaker.run(() -> response.flatMap(dto -> {			
			CommercialGalleriesDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(filteredDto);
			genericResultDto.setCount(dto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());		
		
	}
		
	private Mono<GenericResultDto<CommercialGalleriesDto>> getPageDefault(){
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new CommercialGalleriesDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}		

}
