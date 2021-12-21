package com.businessassistantbcn.opendata.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.commercialgaleries.CommercialGaleriesDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;

import reactor.core.publisher.Mono;

@Service
public class CommercialGaleriesService {
	
	@Autowired
    HttpClientHelper helper;
	
	@Autowired
	private PropertiesConfig config;
	
	@Autowired
	private GenericResultDto<CommercialGaleriesDto> genericResultDto;
	
	
	//String url = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/opendatabcn_mercats-centrescomercials_galeries-comercials-js.json";
	//String url = "https://api.github.com";
	
	public Mono<GenericResultDto<CommercialGaleriesDto>> getCommercialGaleriesAll()
	{
		
		try {
			
			Mono<CommercialGaleriesDto[]> response = helper.getRequestData(new URL(config.getDs_commercialgaleries()),CommercialGaleriesDto[].class);
			
			return response.flatMap(dto ->{

				genericResultDto.setResults(dto);
				genericResultDto.setCount(dto.length);
				return Mono.just(genericResultDto);
				
			} );
			
		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
		
		/*RestTemplate restTemplate = new RestTemplate();
		CommercialGaleriesResultDto response = restTemplate.getForObject(url, CommercialGaleriesResultDto.class);
		*/
		
		/*RestTemplate restTemplate = new RestTemplate();
		CommercialGaleriesResponseDto response = restTemplate.getForObject(url, CommercialGaleriesResponseDto.class);
		
		//JSONArray jsonArrayCommercialGalerie = new JSONArray(response);
		//JSONObject jsonObjectCommercialGaleries = new JSONObject();
		
		
    	/*WebClient.Builder webClient = WebClient.builder();
		
    	CommercialGaleriesResponseDto galeria = webClient.build()
								    				.get()
								    				.uri(url)
								    				.retrieve()
								    				.bodyToMono(CommercialGaleriesResponseDto.class)
								    				.block();*/
    	
		
		//jsonObjectCommercialGaleries.put("elements", jsonArrayCommercialGalerie);
		
	}
		

}
