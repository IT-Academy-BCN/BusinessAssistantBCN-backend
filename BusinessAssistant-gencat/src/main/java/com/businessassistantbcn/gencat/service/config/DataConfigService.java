package com.businessassistantbcn.gencat.service.config;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.input.TypeDataDto;
import com.businessassistantbcn.gencat.dto.output.TypeDataResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DataConfigService {

    @Autowired
    private PropertiesConfig config;

    public Mono<TypeDataResponseDto> getTypes(){
        String[] types = config.getTypesData();

        TypeDataDto[] typesData = new TypeDataDto[types.length];
        for(int i = 1; i <= types.length; i++){
            typesData[i-1] = new TypeDataDto(i, types[i-1]);
        }

        TypeDataResponseDto response = new TypeDataResponseDto(typesData.length, typesData);
        return Mono.just(response);
    }

}
