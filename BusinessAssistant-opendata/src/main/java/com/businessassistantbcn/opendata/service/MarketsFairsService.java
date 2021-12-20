package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsResponseDto;
import com.businessassistantbcn.opendata.dto.marketfairs.MarketFairsResponseDto;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import com.businessassistantbcn.opendata.helper.JsonHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MarketsFairsService {
    @Autowired
    PropertiesConfig config;
    @Autowired
    HttpClientHelper helper;
    @Autowired
    private HttpClientHelper httpClientHelper;
    @Autowired
    private JsonHelper jsonHelper;

    public GenericResultDto<MarketFairsResponseDto> getAllData() {
        return null;
    }

    public GenericResultDto<MarketFairsResponseDto> getPage(int offset, int limit) {
        return null;
    }
    
    public String getAllMarketsFairs(int offset, int limit) {
    	return null; 
    }

}
