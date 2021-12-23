package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.bcnzones.BcnZonesDto;
import com.businessassistantbcn.opendata.dto.bcnzones.BcnZonesResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Array;

@Service
public class BcnZonesService {

    @Autowired
    private PropertiesConfig config;

    public Mono<BcnZonesResponseDto> getBcnZones(){
        String[] districts = config.getDistricts();

        BcnZonesDto[] zones = new BcnZonesDto[districts.length];
        for(int i = 1; i <= districts.length; i++ ){
            zones[i-1] = new BcnZonesDto(i,districts[i-1]);
            }
        BcnZonesResponseDto response = new BcnZonesResponseDto(zones.length, zones);
        return Mono.just(response);

    }

}
