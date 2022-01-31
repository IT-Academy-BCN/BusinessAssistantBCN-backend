package com.businessassistantbcn.opendata.service.externaldata;

import java.net.MalformedURLException;
import java.net.URL;

import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.CommercialGalleriesDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.businessassistantbcn.opendata.config.PropertiesConfig;

import reactor.core.publisher.Mono;

@Service
public class CommercialGalleriesService {
	
	@Autowired
	HttpProxy httpProxy;
	
	@Autowired
	private PropertiesConfig config;
	
	@Autowired
	private GenericResultDto<CommercialGalleriesDto> genericResultDto;
	
	
	//String url = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/opendatabcn_mercats-centrescomercials_galeries-comercials-js.json";
	//String url = "https://api.github.com";
	
	public Mono<GenericResultDto<CommercialGalleriesDto>> getCommercialGalleriesAll()
	{
		
		try {
			
			Mono<CommercialGalleriesDto[]> response = httpProxy.getRequestData(new URL(config.getDs_commercialgalleries()),CommercialGalleriesDto[].class);
			
			return response.flatMap(dto ->{

				genericResultDto.setResults(dto);
				genericResultDto.setCount(dto.length);
				return Mono.just(genericResultDto);
				
			} );
			
		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
	}


	public Mono<GenericResultDto<CommercialGalleriesDto>> getPage(int offset, int limit) {

		try {

			Mono<CommercialGalleriesDto[]> response = httpProxy.getRequestData(new URL(config.getDs_commercialgalleries()),
					CommercialGalleriesDto[].class);

			return response.flatMap(dto -> {
				try {
					CommercialGalleriesDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
					genericResultDto.setLimit(limit);
					genericResultDto.setOffset(offset);
					genericResultDto.setResults(filteredDto);
					genericResultDto.setCount(dto.length);
					return Mono.just(genericResultDto);
				} catch (Exception e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
				}
			});

		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
	}
		

}
