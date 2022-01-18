package com.businessassistantbcn.opendata.service.externaldata;


import java.net.MalformedURLException;
import java.net.URL;

import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;

import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import com.businessassistantbcn.opendata.helper.JsonHelper;

import reactor.core.publisher.Mono;

@Service
public class BigMallsService {

	private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);
	@Autowired
	private PropertiesConfig config;
	@Autowired
	private HttpClientHelper httpClientHelper;
	@Autowired
	private GenericResultDto<BigMallsDto> genericResultDto;

	public Mono<GenericResultDto<BigMallsDto>>getPage(int offset, int limit) {

		try {
			Mono<BigMallsDto[]> response = httpClientHelper.getRequestData(new URL(config.getDs_bigmalls()),
					BigMallsDto[].class);
			return response.flatMap(dto ->{
				try {
					BigMallsDto[] filteredDto = JsonHelper.filterDto(dto,offset,limit);
					genericResultDto.setLimit(limit);
					genericResultDto.setOffset(offset);
					genericResultDto.setResults(filteredDto);
					genericResultDto.setCount(dto.length);
					return Mono.just(genericResultDto);
				} catch (Exception e) {
					log.error("Error con el filtrado de BigMalls" +  e.getMessage());
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
				}

			} );

		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
	}
	
	public GenericResultDto<BigMallsDto> getBigMallsByActivityDto(int[] activities, int offset, int limit) {
		//lambda filter
		return null;
	}
	
	public GenericResultDto<BigMallsDto> getBigMallsByDistrictDto(int[] districts, int offset, int limit) {
		//lambda filter
		return null;
	}
	
	public String getBigMallsByActivity(int[] activities, int offset, int limit) {
		//JsonPath search
		/* OJO a formato de salida:
		{
		"count": 1217,
		"elements": [
		{
		"id": 3716,
		"name": "Paola",
		"surnames": "dos Reis Figueira",
		...
		*/
		return null;
	}
	
	public String getBigMallsByDistrict(int[] districts, int offset, int limit) {
		//JsonPath search
		/* OJO a formato de salida:
		{
		"count": 1217,
		"elements": [
		{
		"id": 3716,
		"name": "Paola",
		"surnames": "dos Reis Figueira",
		...
		*/
		
		return null;
	}



}

