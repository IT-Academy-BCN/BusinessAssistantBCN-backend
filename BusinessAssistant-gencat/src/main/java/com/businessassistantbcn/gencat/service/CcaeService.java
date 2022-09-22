package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.output.AllCcaeDto;
import com.businessassistantbcn.gencat.dto.output.CcaeDto;
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


    public Mono<GenericResultDto<CcaeDto>> getPage(int offset, int limit) throws MalformedURLException {

         return httpProxy.getRequestData(new URL(config.getDs_ccae()), AllCcaeDto.class)
                .flatMap( ccaeDtos -> {

                    CcaeDto[] resposeDtos = ccaeDtos
                            .getAllCcae()
                            .toArray(CcaeDto[]::new);

                    CcaeDto[] pagedDto = JsonHelper.filterDto(resposeDtos, offset, limit);

                    genericResultDto.setInfo(offset, limit, pagedDto.length, pagedDto);

                    return Mono.just(genericResultDto);
                });

    }

    private Mono<GenericResultDto<CcaeDto>> getBigMallsDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new CcaeDto[0]);
        return Mono.just(genericResultDto);
    }


}
