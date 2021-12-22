package com.businessassistantbcn.opendata.service;

import java.net.MalformedURLException;
import java.net.URL;

import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.CommercialGalleriesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;

import reactor.core.publisher.Mono;

@Service
public class CommercialGalleriesService {
	
	@Autowired
    HttpClientHelper helper;
	
	@Autowired
	private PropertiesConfig config;
	
	@Autowired
	private GenericResultDto<CommercialGalleriesDto> genericResultDto;
	
	
	//String url = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/opendatabcn_mercats-centrescomercials_galeries-comercials-js.json";
	//String url = "https://api.github.com";
	
	public Mono<GenericResultDto<CommercialGalleriesDto>> getCommercialGaleriesAll()
	{
		
		try {
			
			Mono<CommercialGalleriesDto[]> response = helper.getRequestData(new URL(config.getDs_commercialgaleries()),CommercialGalleriesDto[].class);
			
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
