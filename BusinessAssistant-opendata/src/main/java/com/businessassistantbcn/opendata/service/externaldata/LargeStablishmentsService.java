package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsDto;
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
	private GenericResultDto<LargeStablishmentsDto> genericResultDto;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Mono<GenericResultDto<LargeStablishmentsDto>>getPageByDistrict(int offset, int limit, String district) {
		
		try {
			Mono<LargeStablishmentsDto[]> response = httpProxy.getRequestData(new URL(config.getDs_largestablishments()),
					LargeStablishmentsDto[].class);
			
		
			return response.flatMap(largeStablismentsDto ->{
				try {
					
					LargeStablishmentsDto[] largeStablismentsDtoByDistrict = Arrays.stream(largeStablismentsDto)
							.filter(dto -> dto.getAddresses().stream().anyMatch(address -> address.getDistrict_id().equals(district)))
							.toArray(LargeStablishmentsDto[]::new);
					
					//String key = "district_id";
					//LargeStablishmentsDto[] dtoByDsitrictId = getArrayDtoByKeyAddress(dto, key, district);
						
					
					LargeStablishmentsDto[] pagedDto = JsonHelper.filterDto(largeStablismentsDtoByDistrict,offset,limit);
					
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
	public Mono<GenericResultDto<LargeStablishmentsDto>>getPageByActivity(int offset, int limit, String activity) {
		
		log.info("Activity: " + activity);
		
		try {
			Mono<LargeStablishmentsDto[]> response = httpProxy.getRequestData(new URL(config.getDs_largestablishments()),
					LargeStablishmentsDto[].class);
			
			return response.flatMap(largeStablismentsDto ->{
				try {
					
					LargeStablishmentsDto[] pagedDto = JsonHelper.filterDto(largeStablismentsDto,offset,limit);
					
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
	
    public Mono<GenericResultDto<LargeStablishmentsDto>> getLargeStablishmentsAll()

	{
    	
		try {
			
			Mono<LargeStablishmentsDto[]> response = httpProxy.getRequestData(new URL(config.getDs_largestablishments()),LargeStablishmentsDto[].class);
			
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
