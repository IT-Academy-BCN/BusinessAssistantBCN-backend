package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.LargeEstablishmentsDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;

import com.businessassistantbcn.opendata.proxy.HttpProxy;

import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;

import org.springframework.stereotype.Service;


import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;



@Service
public class LargeEstablishmentsService {

	@Autowired
	HttpProxy httpProxy;
	
	@Autowired
	private PropertiesConfig config;
	
	@Autowired
	private GenericResultDto<LargeEstablishmentsDto> genericResultDto;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Mono<GenericResultDto<LargeEstablishmentsDto>>getPageByDistrict(int offset, int limit, String district) {
		

		URL url;


		try {
			url = new URL(config.getDs_largeestablishments());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: " + e.getMessage());
			return getPageDefault();
		}

		Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(url, LargeEstablishmentsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

		return circuitBreaker.run(() -> response.flatMap(largeStablismentsDto -> {

			LargeEstablishmentsDto[] largeStablismentsDtoByDistrict = Arrays.stream(largeStablismentsDto)
					.filter(dto -> dto.getAddresses().stream().anyMatch(address -> true))
					.toArray(LargeEstablishmentsDto[]::new);

			LargeEstablishmentsDto[] pagedDto = JsonHelper.filterDto(largeStablismentsDtoByDistrict, offset, limit);
			
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(pagedDto);
			genericResultDto.setCount(largeStablismentsDtoByDistrict.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());
	}
	
	public Mono<GenericResultDto<LargeEstablishmentsDto>>getPageByActivity(int offset, int limit, String activityId) {
		
		log.info("Activity id: " + activityId);
		
		URL url;

		try {
			url = new URL(config.getDs_largeestablishments());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: " + e.getMessage());
			return getPageDefault();
		}

		Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(url, LargeEstablishmentsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
		
		return circuitBreaker.run(() -> response.flatMap(largeEstablismentsDto -> {
				
			LargeEstablishmentsDto[] dtoByActivity = Arrays.stream(largeEstablismentsDto)
					.filter(dto -> dto.getClassifications_data().stream().anyMatch(id -> id.getId() == Integer.parseInt(activityId)))
					.toArray(LargeEstablishmentsDto[]::new);
			
			Arrays.stream(dtoByActivity).forEach(dto -> log.info("Name: " + dto.getName() + " id: " + dto.getRegister_id()));
			
			LargeEstablishmentsDto[] pagedDto = JsonHelper.filterDto(dtoByActivity,offset,limit);
			
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(pagedDto);
			genericResultDto.setCount(dtoByActivity.length);
			
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());
			
		}
		

	
    public Mono<GenericResultDto<LargeEstablishmentsDto>> getLargeEstablishmentsAll()

	{
    	
    	URL url;
		try {
			url = new URL(config.getDs_largeestablishments());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: " + e.getMessage());
			return getPageDefault();
		}
		
		Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(url, LargeEstablishmentsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
		
		return circuitBreaker.run(() -> response.flatMap(dto -> {

			genericResultDto.setResults(dto);
			genericResultDto.setCount(dto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());
	}
	
    
    private Mono<GenericResultDto<LargeEstablishmentsDto>> getPageDefault() {
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new LargeEstablishmentsDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}
}
