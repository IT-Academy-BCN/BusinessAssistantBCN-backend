package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.municipalmarkets.MunicipalMarketsDTO;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MunicipalMarketsService {

    @Autowired
    PropertiesConfig config;

    @Autowired
    HttpClientHelper helper;

    @Autowired
    GenericResultDto<MunicipalMarketsDTO> genericResultDto;

    public <T> Mono<T> getTestData()throws MalformedURLException{
           return helper.getRequestData(new URL(config.getDs_test()), MunicipalMarketsDTO.class);
    }

    public Mono<GenericResultDto<MunicipalMarketsDTO>> getAllData()throws MalformedURLException {
        Mono<MunicipalMarketsDTO[]> response = helper.getRequestData(
                new URL(config.getDs_municipalmarkets()), MunicipalMarketsDTO[].class);
        return response.flatMap( dto -> {
            genericResultDto.setResults(dto);
            genericResultDto.setCount(dto.length);
            return Mono.just(genericResultDto);
        });
    }

    public Mono<GenericResultDto<MunicipalMarketsDTO>> getAllData(int offset, int  limit) throws MalformedURLException {
        Mono<MunicipalMarketsDTO[]> response = helper.getRequestData(
                new URL(config.getDs_municipalmarkets()), MunicipalMarketsDTO[].class);
        return response.flatMap(dto -> {
            genericResultDto.setResults(dto,offset,limit);
            genericResultDto.setCount(dto.length);
            return Mono.just(genericResultDto);
        });
    }

}
