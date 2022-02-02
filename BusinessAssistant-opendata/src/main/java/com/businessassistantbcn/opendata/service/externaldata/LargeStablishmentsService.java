package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.economicactivitiescensus.EconomicActivitiesCensusDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;

import com.businessassistantbcn.opendata.proxy.HttpProxy;
import com.fasterxml.jackson.databind.ser.std.ArraySerializerBase;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


@Service
public class LargeStablishmentsService {
	
	private static final Logger log = LoggerFactory.getLogger(LargeStablishmentsService.class);
	
	@Autowired
	HttpProxy httpProxy;
	
	@Autowired
	private PropertiesConfig config;
	
	@Autowired
	private GenericResultDto<LargeStablishmentsDto> genericResultDto;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	
	
	public Mono<GenericResultDto<LargeStablishmentsDto>>getPageByDistrict(int offset, int limit, String district) {

	    URL url;
		try {
			url = new URL(config.getDs_largestablishments());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			return getPageDefault();
		}    	
    	
		Mono<LargeStablishmentsDto[]> response = httpProxy.getRequestData(url, LargeStablishmentsDto[].class);
		
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");			
		
		return circuitBreaker.run(() -> response.flatMap(largeStablismentsDto -> {			
			LargeStablishmentsDto[] largeStablismentsDtoByDistrict = Arrays.stream(largeStablismentsDto)
					.filter(dto -> dto.getAddresses().stream().anyMatch(address -> true))
					.toArray(LargeStablishmentsDto[]::new);
			LargeStablishmentsDto[] pagedDto = JsonHelper.filterDto(largeStablismentsDtoByDistrict,offset,limit);			
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(pagedDto);
			genericResultDto.setCount(largeStablismentsDtoByDistrict.length);			
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());			
		
	}
	
	public Mono<GenericResultDto<LargeStablishmentsDto>>getPageByActivity(int offset, int limit, String activity) {
		
		// log.info("Activity id: " + activity);

        URL url;
		try {
			url = new URL(config.getDs_largestablishments());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			return getPageDefault();
		} 		
		
		Mono<LargeStablishmentsDto[]> response = httpProxy.getRequestData(url, LargeStablishmentsDto[].class);
		
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");	

		return circuitBreaker.run(() -> response.flatMap(dto -> {
			LargeStablishmentsDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(filteredDto);
			genericResultDto.setCount(dto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());	
		
	}
	
    public Mono<GenericResultDto<LargeStablishmentsDto>> getLargeStablishmentsAll()
	{
	    URL url;
		try {
			url = new URL(config.getDs_largestablishments());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			return getPageDefault();
		}    	
    	
		Mono<LargeStablishmentsDto[]> response = httpProxy.getRequestData(url, LargeStablishmentsDto[].class);

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");	

		int offset = 0;
		int limit = 0;
		return circuitBreaker.run(() -> response.flatMap(dto -> {
			LargeStablishmentsDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(filteredDto);
			genericResultDto.setCount(dto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());	
		
	}    
    
    private Mono<GenericResultDto<LargeStablishmentsDto>> getPageDefault(){
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new LargeStablishmentsDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}	    
    
}
