package com.businessassistantbcn.opendata.service.config;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.input.bcnzones.BcnZonesDto;
import com.businessassistantbcn.opendata.dto.output.BcnZonesResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DataConfigService {

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
