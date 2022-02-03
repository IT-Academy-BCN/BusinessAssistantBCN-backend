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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


@Service
public class LargeStablishmentsService {

	@Autowired
	HttpProxy httpProxy;
	
	@Autowired
	private PropertiesConfig config;
	
	@Autowired
	private GenericResultDto<LargeEstablishmentsDto> genericResultDto;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Mono<GenericResultDto<LargeEstablishmentsDto>>getPageByDistrict(int offset, int limit, String district) {
		
		try {
			Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(new URL(config.getDs_largestablishments()),
					LargeEstablishmentsDto[].class);
			
		
			return response.flatMap(largeStablismentsDto ->{
				try {
					
					LargeEstablishmentsDto[] largeStablismentsDtoByDistrict = Arrays.stream(largeStablismentsDto)
							.filter(dto -> dto.getAddresses().stream().anyMatch(address -> true))
							.toArray(LargeEstablishmentsDto[]::new);
					
					//String key = "district_id";
					//LargeStablishmentsDto[] dtoByDsitrictId = getArrayDtoByKeyAddress(dto, key, district);
						
					
					LargeEstablishmentsDto[] pagedDto = JsonHelper.filterDto(largeStablismentsDtoByDistrict,offset,limit);
					
					genericResultDto.setLimit(limit);
					genericResultDto.setOffset(offset);
					genericResultDto.setResults(pagedDto);
					genericResultDto.setCount(largeStablismentsDtoByDistrict.length);
					
					return Mono.just(genericResultDto);
					
				} catch (Exception e) {
					//Poner Logger
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
				}

			} );

		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
	}
	
	
	public Mono<GenericResultDto<LargeEstablishmentsDto>>getPageByActivity(int offset, int limit, String activity) {
		
		log.info("Activity id: " + activity);
		
		try {
			Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(new URL(config.getDs_largestablishments()),
					LargeEstablishmentsDto[].class);
			
			return response.flatMap(largeStablismentsDto ->{
				try {
					
					LargeEstablishmentsDto[] pagedDto = JsonHelper.filterDto(largeStablismentsDto,offset,limit);
					
					genericResultDto.setLimit(limit);
					genericResultDto.setOffset(offset);
					genericResultDto.setResults(pagedDto);
					genericResultDto.setCount(largeStablismentsDto.length);
					
					return Mono.just(genericResultDto);
					
				} catch (Exception e) {
			
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
				}

			} );

		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
	}
	
    public Mono<GenericResultDto<LargeEstablishmentsDto>> getLargeStablishmentsAll()

	{
    	
		try {
			
			Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(new URL(config.getDs_largestablishments()), LargeEstablishmentsDto[].class);
			
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
