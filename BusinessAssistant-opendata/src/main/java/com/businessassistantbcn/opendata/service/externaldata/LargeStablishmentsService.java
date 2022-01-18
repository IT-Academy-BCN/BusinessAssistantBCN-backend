package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsDto;
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

	// Default get all data
    public Mono<GenericResultDto<LargeStablishmentsDto>> getLargeStablishmentsAll()
	{
		try {
			Mono<LargeStablishmentsDto[]> response = httpClientHelper.getRequestData(new URL(config.getDs_largestablishments()), LargeStablishmentsDto[].class);
			return response.flatMap(dto ->{
				genericResultDto.setResults(dto);
				genericResultDto.setCount(dto.length);
				return Mono.just(genericResultDto);
			} );
		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
	}


	public Mono<GenericResultDto<LargeStablishmentsDto>> getPageLargeStablishments(int offset, int limit) {
		try {
			Mono<LargeStablishmentsDto[]> response = httpClientHelper.getRequestData(
					new URL(config.getDs_largestablishments()), LargeStablishmentsDto[].class);
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

	public Mono<GenericResultDto<LargeStablishmentsDto>> getPage(int offset, int limit) {
		try {
			Mono<LargeStablishmentsDto[]> response = httpClientHelper.getRequestData(
					new URL(config.getDs_largestablishments()), LargeStablishmentsDto[].class);
			return response.flatMap( dto -> {
				try{
					LargeStablishmentsDto[] filteredDto = JsonHelper.filterDto(dto,offset,limit);
					genericResultDto.setLimit(limit);
					genericResultDto.setOffset(offset);
					genericResultDto.setResults(filteredDto);
					genericResultDto.setCount(dto.length);
					return Mono.just(genericResultDto);
				} catch (Exception e){
					log.error("Error con el filtrado de LargeStablishments" +  e.getMessage());
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
				}
			});
		} catch (MalformedURLException mue) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
		}
	}



}
