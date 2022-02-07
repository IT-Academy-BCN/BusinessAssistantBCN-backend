package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.LargeEstablishmentsDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;

import java.util.Arrays;
import java.util.function.Predicate;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Slf4j
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
	
	// Get paged results
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPage(int offset, int limit) {
		return getResultDto(offset, limit, dto -> true);
	}
	
	// Get paged results filtered by district
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPageByDistrict(int offset, int limit, int district) {
		return getResultDto(offset, limit, dto ->
				dto.getAddresses().stream().anyMatch(a ->
						Integer.parseInt(a.getDistrict_id()) == district
		));
	}
	
	// Get paged results filtered by activity
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPageByActivity(int offset, int limit, String activity) {
		return null; // *** FILTRO PENDIENTE ***
	}
	
	private Mono<GenericResultDto<LargeEstablishmentsDto>> getResultDto(
			int offset, int limit, Predicate<LargeEstablishmentsDto> dtoFilter) { try {
		
		Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(new URL(config.getDs_largeestablishments()),
				LargeEstablishmentsDto[].class);
		
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
		
		return circuitBreaker.run(() ->	response.flatMap(dto -> {
			LargeEstablishmentsDto[] filteredDto = Arrays.stream(dto)
					.filter(dtoFilter)
					.toArray(LargeEstablishmentsDto[]::new);
			
			LargeEstablishmentsDto[] pagedDto = JsonHelper
					.pageDto(filteredDto, offset, limit);
			
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(pagedDto);
			genericResultDto.setCount(filteredDto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());
		
	} catch(MalformedURLException e) {
		log.error("URL bad configured: " + e.getMessage());
		return getPageDefault();
	} }
	
	private Mono<GenericResultDto<LargeEstablishmentsDto>> getPageDefault() {
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new LargeEstablishmentsDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}
	
}