package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsFactorisizedDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;

import com.businessassistantbcn.opendata.helper.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class LargeStablishmentsService {
	private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);

	@Autowired
    HttpClientHelper httpClientHelper;
	
	@Autowired
	private PropertiesConfig config;
	
	@Autowired
	private GenericResultDto<LargeStablishmentsDto> genericResultDto;

	@Autowired
	private GenericResultDto<LargeStablishmentsFactorisizedDto> genericResultFactosisedDto;

	// Default get all data
    public Mono<GenericResultDto<LargeStablishmentsDto>> getLargeStablishmentsAll()
	{
		try {
			Mono<LargeStablishmentsDto[]> response = httpClientHelper.getRequestData(new URL(config.getDs_largestablishments()),LargeStablishmentsDto[].class);
			return response.flatMap(dto ->{
				genericResultDto.setResults(dto);
				genericResultDto.setCount(dto.length);
				return Mono.just(genericResultDto);
			} );
		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
	}

	// Factorisized: getting all data paged
	public Mono<GenericResultDto<LargeStablishmentsFactorisizedDto>> getPageFactorisized(int offset, int limit) {
		try {
			Mono<LargeStablishmentsFactorisizedDto[]> response = httpClientHelper.getRequestData(
					new URL(config.getDs_largestablishments()), LargeStablishmentsFactorisizedDto[].class);
			return response.flatMap( dto -> {
				try{
					LargeStablishmentsFactorisizedDto[] filteredDto = JsonHelper.filterDto(dto,offset,limit);
					genericResultFactosisedDto.setLimit(limit);
					genericResultFactosisedDto.setOffset(offset);
					genericResultFactosisedDto.setResults(filteredDto);
					genericResultFactosisedDto.setCount(dto.length);
					return Mono.just(genericResultFactosisedDto);
				} catch (Exception e){
					log.error("Error con el filtrado de EconomicAtivitiesCensus" +  e.getMessage());
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
				}
			});
		} catch (MalformedURLException mue) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
		}
	}

	public Mono<GenericResultDto<LargeStablishmentsDto>> getPage(int offset, int limit) {
		try {
			Mono<LargeStablishmentsDto[]> response = httpClientHelper.getRequestData(
					new URL(config.getDs_economicactivitiescensus()), LargeStablishmentsDto[].class);
			return response.flatMap( dto -> {
				try{
					LargeStablishmentsDto[] filteredDto = JsonHelper.filterDto(dto,offset,limit);
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
