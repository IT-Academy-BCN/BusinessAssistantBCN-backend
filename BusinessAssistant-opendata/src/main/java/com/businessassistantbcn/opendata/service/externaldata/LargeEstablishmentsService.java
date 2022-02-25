package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.LargeEstablishmentsDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.Arrays;
import java.util.function.Predicate;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	// Get paged results
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPage(int offset, int limit) throws MalformedURLException {
		return getResultDto(offset, limit, dto -> true);
	}
	
	// Get paged results filtered by district
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPageByDistrict(int offset, int limit, int district) throws MalformedURLException {
		return getResultDto(offset, limit, dto ->
				dto.getAddresses().stream().anyMatch(a ->
						Integer.parseInt(a.getDistrict_id()) == district
		));
	}
	
	// Get paged results filtered by activity
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPageByActivity(int offset, int limit, String activityId) throws MalformedURLException {
	
		Predicate<LargeEstablishmentsDto> doFilter = largeEstablishmentsDto -> 
				largeEstablishmentsDto.getClassifications_data()
				.stream()
				.anyMatch(classificationsDataDto -> classificationsDataDto.getId() == Integer.parseInt(activityId));
		
		return getResultDto(offset, limit, doFilter);
	}
	//@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")	
	private Mono<GenericResultDto<LargeEstablishmentsDto>> getResultDto(
			int offset, int limit, Predicate<LargeEstablishmentsDto> dtoFilter) throws MalformedURLException {
			
		Mono<LargeEstablishmentsDto[]> response = getLargeEstablishments();

		return 	response.flatMap(dto -> {
			LargeEstablishmentsDto[] filteredDto = Arrays.stream(dto)
					.filter(dtoFilter)
					.toArray(LargeEstablishmentsDto[]::new);
			
			LargeEstablishmentsDto[] pagedDto = JsonHelper
					.filterDto(filteredDto, offset, limit);
			
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(pagedDto);
			genericResultDto.setCount(filteredDto.length);
			return Mono.just(genericResultDto);
		});
		
	}
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	private Mono<LargeEstablishmentsDto[]> getLargeEstablishments() throws MalformedURLException {
		URL url = new URL(config.getDs_largeestablishments());
		return httpProxy.getRequestData(url, LargeEstablishmentsDto[].class);
	}
	
	private Mono<GenericResultDto<LargeEstablishmentsDto>> getPageDefault(Throwable exception) {
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new LargeEstablishmentsDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}
	
}