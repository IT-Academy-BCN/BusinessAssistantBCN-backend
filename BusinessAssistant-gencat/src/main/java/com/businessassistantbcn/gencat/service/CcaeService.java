package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.helper.CcaeDeserializer;
import com.businessassistantbcn.gencat.helper.JsonHelper;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class CcaeService {

    private static final Logger log = LoggerFactory.getLogger(CcaeService.class);
    @Autowired
    private HttpProxy httpProxy;

    @Autowired
    private PropertiesConfig config;

    @Autowired
    private GenericResultDto<CcaeDto> genericResultDto;

    @Autowired
    private CcaeDeserializer ccaeDeserializer;

    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logServerErrorCcaeDefaultPage")
    public Mono<GenericResultDto<CcaeDto>> getPage(int offset, int limit) throws MalformedURLException {

        return getData()
                .flatMap(ccaeDtos -> {
                        CcaeDto[] pageResult = JsonHelper.filterDto(ccaeDtos, offset, limit);
                        genericResultDto.setInfo(offset, limit, ccaeDtos.length, pageResult);
                        return Mono.just(genericResultDto);
                    });
    }

    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logServerErrorCcaeDefaultPage")
    public Mono<GenericResultDto<CcaeDto>> getPageByCcaeId(int offset, int limit, String ccaeId) {

        return this.getCcaeDefaultPage();
    }

    @SuppressWarnings("unused")
    private Mono<GenericResultDto<CcaeDto>> logServerErrorCcaeDefaultPage(Throwable exception) {
        log.error("Gencat server is down");
        return this.getCcaeDefaultPage();
    }

    private Mono<GenericResultDto<CcaeDto>> getCcaeDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new CcaeDto[0]);
        return Mono.just(genericResultDto);
    }

    private Mono<CcaeDto[]> getData() throws MalformedURLException {
        //Returns deserialized data from JSON
        return httpProxy.getRequestData(new URL(config.getDs_ccae()), Object.class)
                .flatMap(ccaeDto -> {
                    CcaeDto[] codes = ccaeDeserializer.deserialize(ccaeDto).toArray(CcaeDto[]::new);
                    return Mono.just(codes);
                });
    }
}
