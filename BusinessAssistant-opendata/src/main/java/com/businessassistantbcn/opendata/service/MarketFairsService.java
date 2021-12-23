package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.CommercialGalleriesDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsResponseDto;
import com.businessassistantbcn.opendata.dto.marketfairs.MarketFairsResponseDto;
import com.businessassistantbcn.opendata.dto.municipalmarkets.MunicipalMarketsResponseDto;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import com.businessassistantbcn.opendata.helper.JsonHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MarketFairsService {
    @Autowired
    PropertiesConfig config;
    @Autowired
    HttpClientHelper helper;
    @Autowired
	private GenericResultDto<MarketFairsResponseDto> genericResultDto;

    public GenericResultDto<MarketFairsResponseDto> getPage(int offset, int limit) {
        return null;
    }
    
    public String getAllMarketsFairs(int offset, int limit) {
    	return null; 
    }
    
	public Mono<GenericResultDto<MarketFairsResponseDto>> getAllMarketsFairs() {
		try {
			Mono<MarketFairsResponseDto[]> response = helper.getRequestData(new URL(config.getDs_marketsfairs()),MarketFairsResponseDto[].class);
			return response.flatMap(dto -> {
				genericResultDto.setResults(dto);
				genericResultDto.setCount(dto.length);
				return Mono.just(genericResultDto);
			} );
		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
	}

}
