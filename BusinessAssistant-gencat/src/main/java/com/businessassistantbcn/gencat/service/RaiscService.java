package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.output.RaiscResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RaiscService {

    private static final Logger log = LoggerFactory.getLogger(RaiscService.class);

    @Autowired
    private GenericResultDto<RaiscResponseDto> genericResultDto;

    @Autowired
    private RaiscResponseDto raiscResponseDto;

    public Mono<GenericResultDto<RaiscResponseDto>> getPageByRaiscYear(int offset, int limit, String year) {
        return getRaiscDefaultPage();
    }

    private Mono<GenericResultDto<RaiscResponseDto>> getRaiscDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new RaiscResponseDto[0]);
        return Mono.just(genericResultDto);
    }
}
