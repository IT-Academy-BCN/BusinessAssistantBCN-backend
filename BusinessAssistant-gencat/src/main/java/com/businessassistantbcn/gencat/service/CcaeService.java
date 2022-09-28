package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.helper.CcaeDeserializer;
import com.businessassistantbcn.gencat.helper.JsonHelper;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;


@Service
public class CcaeService {

    @Autowired
    private HttpProxy httpProxy;

    @Autowired
    private PropertiesConfig config;

    @Autowired
    private GenericResultDto<CcaeDto> genericResultDto;

    @Autowired
    private CcaeDeserializer ccaeDeserializer;

    public Mono<GenericResultDto<CcaeDto>> getPage(int offset, int limit) throws MalformedURLException {

        return getData()
                .flatMap(ccaeDtos -> {
                        CcaeDto[] pageResult = JsonHelper.filterDto(ccaeDtos, offset, limit);
                        genericResultDto.setInfo(offset, limit, pageResult.length, pageResult);
                        return Mono.just(genericResultDto);
                    });
    }

    private Mono<CcaeDto[]> getData() throws MalformedURLException {
        return httpProxy.getRequestData(new URL(config.getDs_ccae()), Object.class)
                .flatMap(ccaeDto -> {
                    CcaeDto[] codes = ccaeDeserializer.deserialize(ccaeDto).toArray(CcaeDto[]::new);
                    return Mono.just(codes);
                });
    }



}
