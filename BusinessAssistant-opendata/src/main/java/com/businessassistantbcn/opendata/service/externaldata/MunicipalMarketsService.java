package com.businessassistantbcn.opendata.service.externaldata;


import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.municipalmarkets.MunicipalMarketsDto;
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
public class MunicipalMarketsService {

    @Autowired
    private PropertiesConfig config;

    @Autowired
    HttpClientHelper helper;

    @Autowired
    private HttpClientHelper httpClientHelper;

    @Autowired
    private GenericResultDto<MunicipalMarketsDto> genericResultDto;

    public Mono<GenericResultDto<MunicipalMarketsDto>> getPage(int offset, int limit) {

        try {
            Mono<MunicipalMarketsDto[]> response = httpClientHelper.getRequestData(new URL(config.getDs_municipalmarkets()),
                    MunicipalMarketsDto[].class);
            return response.flatMap(dto ->{
                try {
                    MunicipalMarketsDto[] filteredDto = JsonHelper.filterDto(dto,offset,limit);
                    genericResultDto.setLimit(limit);
                    genericResultDto.setOffset(offset);
                    genericResultDto.setResults(filteredDto);
                    //genericResultDto.setResults(Arrays.stream(filteredDto).filter(x->"01".equals(x.getDistrict_id())));
                    genericResultDto.setCount(dto.length);
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

}
