package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.CommercialGalleriesDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsResponseDto;
import com.businessassistantbcn.opendata.dto.marketfairs.MarketFairsDto;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import com.businessassistantbcn.opendata.helper.JsonHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MarketFairsService {
	
	@Autowired
	private PropertiesConfig config;
	@Autowired
	private HttpClientHelper httpClientHelper;
	@Autowired
	private GenericResultDto<MarketFairsDto> genericResultDto;
	
	public Mono<GenericResultDto<MarketFairsDto>>getPage(int offset, int limit) { try {
		
		Mono<MarketFairsDto[]> response = httpClientHelper.getRequestData(new URL(config.getDs_marketfairs()),
				MarketFairsDto[].class);
		
		return response.flatMap(dto -> { try {
			MarketFairsDto[] filteredDto = JsonHelper.filterDto(dto,offset,limit);
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(filteredDto);
			genericResultDto.setCount(dto.length);
			return Mono.just(genericResultDto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} });
		
	} catch (MalformedURLException e) {
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
	} }
	
}
