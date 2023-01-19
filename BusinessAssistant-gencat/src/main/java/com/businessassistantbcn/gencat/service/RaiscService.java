package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.input.RaiscInputDto;
import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class RaiscService {

    private static final Logger log = LoggerFactory.getLogger(RaiscService.class);

    @Autowired
    private GenericResultDto genericResultDto;


    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logServerErrorRaiscDefaultPage")
    public Mono<GenericResultDto<RaiscInputDto>> getPageByRaiscYear(int offset, int limit, LocalDate year) {

        return getRaiscDefaultPage();
    }

    private Mono<GenericResultDto<RaiscInputDto>> getRaiscDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new RaiscInputDto[0]);
        return Mono.just(genericResultDto);
    }
}
