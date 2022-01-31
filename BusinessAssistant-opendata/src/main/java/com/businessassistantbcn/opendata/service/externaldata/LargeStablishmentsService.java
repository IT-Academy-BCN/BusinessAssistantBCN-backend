package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;

import com.businessassistantbcn.opendata.proxy.HttpProxy;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.function.Predicate;


@Service
public class LargeStablishmentsService {

	@Autowired
	HttpProxy httpProxy;
	
	@Autowired
	private PropertiesConfig config;
	
	@Autowired
	private GenericResultDto<LargeStablishmentsDto> genericResultDto;
	
	
	private LargeStablishmentsDto[] getArrayDtoByKeyAddress(LargeStablishmentsDto[] dto, String key, String value) {
		
		
		/*
		LinkedHashMap<?, ?> hashMap = null; 
		
		int elements = 0;
		
		for(LargeStablishmentsDto eDto: dto) {
			hashMap = (LinkedHashMap<?, ?>) eDto.getAddresses().get(0);
			if(hashMap.get(key).equals(value)) elements++;
		}
		
		LargeStablishmentsDto[] dtoByKey = new LargeStablishmentsDto[elements];
		
		int element = 0;
		
		for(int i = 0; i < dto.length; i++) {
			 hashMap = (LinkedHashMap<?, ?>) dto[i].getAddresses().get(0);
			 
			 if(hashMap.get(key).equals(value)) {		 
				dtoByKey[element] = dto[i];
				element++;
			 }
		}
		*/
		return null;
	}
	
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
