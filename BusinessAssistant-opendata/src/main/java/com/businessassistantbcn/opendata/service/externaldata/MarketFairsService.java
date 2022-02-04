package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.marketfairs.MarketFairsDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;

import com.businessassistantbcn.opendata.proxy.HttpProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MarketFairsService {
	
	private static final Logger log = LoggerFactory.getLogger(MarketFairsService.class);
	
	@Autowired
	private PropertiesConfig config;
	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private GenericResultDto<MarketFairsDto> genericResultDto;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	
	public Mono<GenericResultDto<MarketFairsDto>>getPage(int offset, int limit) {
	
		URL url;

		try {
			url = new URL(config.getDs_marketfairs());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			return getPageDefault();
		}

		Mono<MarketFairsDto[]> response = httpProxy.getRequestData(url, MarketFairsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

		return circuitBreaker.run(() -> response.flatMap(dto -> {
			MarketFairsDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(filteredDto);
			genericResultDto.setCount(dto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());
	
	}


	private Mono<GenericResultDto<MarketFairsDto>> getPageDefault(){
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new MarketFairsDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}
}
