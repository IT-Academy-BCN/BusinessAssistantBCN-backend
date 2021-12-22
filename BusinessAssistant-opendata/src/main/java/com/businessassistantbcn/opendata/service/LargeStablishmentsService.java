package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class LargeStablishmentsService {

	@Autowired
    HttpClientHelper helper;
	
	@Autowired
	private PropertiesConfig config;
	
	@Autowired
	private GenericResultDto<LargeStablishmentsDto> genericResultDto;
    

    public Mono<GenericResultDto<LargeStablishmentsDto>> getLargeStablishmentsAll()
	{
		
		try {
			
			Mono<LargeStablishmentsDto[]> response = helper.getRequestData(new URL(config.getDs_largestablishments()),LargeStablishmentsDto[].class);
			
			return response.flatMap(dto ->{

				genericResultDto.setResults(dto);
				genericResultDto.setCount(dto.length);
				return Mono.just(genericResultDto);
				
			} );
			
		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
				
	}
}
