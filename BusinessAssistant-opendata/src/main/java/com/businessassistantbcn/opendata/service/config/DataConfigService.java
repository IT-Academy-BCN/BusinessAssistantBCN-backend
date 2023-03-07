package com.businessassistantbcn.opendata.service.config;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.input.bcnzones.BcnZonesDto;
import com.businessassistantbcn.opendata.dto.output.BcnZonesResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DataConfigService {

    @Autowired
    private PropertiesConfig config;

    public Mono<BcnZonesResponseDto> getBcnZones(){
        List<PropertiesConfig.DistrictItem> districts = config.getDistricts();

        BcnZonesDto[] zones = new BcnZonesDto[districts.size()];
        for(int i = 0; i < districts.size(); i++ ){
            zones[i] = new BcnZonesDto(districts.get(i).getNumber(),districts.get(i).getDescription());
            }
        BcnZonesResponseDto response = new BcnZonesResponseDto(zones.length, zones);
        return Mono.just(response);
    }
}
