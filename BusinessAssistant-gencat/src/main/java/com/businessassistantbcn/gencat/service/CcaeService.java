package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.input.CcaeDto;
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


    //public Mono<GenericResultDto<CcaeDto>> getPage(int offset, int limit) throws MalformedURLException {
    public Mono<CcaeDto> getPage(int offset, int limit) throws MalformedURLException {


        return httpProxy.getRequestData(new URL(config.getDs_ccae()), CcaeDto.class);

/*

        Collection colKeys = obj.values();
        Object[] arrayKeys = colKeys.toArray();

        for (int i=0; i<arrayKeys.length; i++) {
            System.out.println("********KEYS: " + obj.getClass());
        }


       Collection colValues = obj.values();
       Object[] arrayValues = colValues.toArray();

        for (int i=0; i<arrayValues.length; i++) {
            System.out.println("********VALUES: " + obj.getClass());
        }*/


/*         return httpProxy.getRequestData(new URL(config.getDs_ccae()), AllCcaeDto.class)
                .flatMap( ccaeDtos -> {

                    CcaeDto[] responseDtos = ccaeDtos
                            .getAllCcae()
                            .toArray(CcaeDto[]::new);

                    CcaeDto[] pagedDto = JsonHelper.filterDto(responseDtos, offset, limit);

                    genericResultDto.setInfo(offset, limit, pagedDto.length, pagedDto);

                    return Mono.just(genericResultDto);
                });*/

    }

    private Mono<GenericResultDto<CcaeDto>> getBigMallsDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new CcaeDto[0]);
        return Mono.just(genericResultDto);
    }


}
