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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CcaeService {

    @Autowired
    private HttpProxy httpProxy;

    @Autowired
    private PropertiesConfig config;

    @Autowired
    private GenericResultDto<CcaeDto> genericResultDto;


    public Mono<GenericResultDto<CcaeDto>> getPage(int offset, int limit) throws MalformedURLException {

        Mono<Object> input = httpProxy.getRequestData(new URL(config.getDs_ccae()), Object.class);
        LinkedHashMap inputData = (LinkedHashMap) input.block();
        ArrayList data = (ArrayList) inputData.get("data");

        CcaeDto ccaeDto;
        List<CcaeDto> listCcae = new ArrayList<CcaeDto>();

        for(int i=0; i<data.size(); i++){
            ArrayList element = (ArrayList) data.get(i);
            ccaeDto = new CcaeDto(
                    element.get(0).toString(),//'id' field
                    element.get(1).toString(),//'name'
                    element.get(2).toString());//'assetType'
            listCcae.add(ccaeDto);
        }
        CcaeDto[] codes = listCcae.toArray(new CcaeDto[0]);
        GenericResultDto resultDto = new GenericResultDto<CcaeDto>();
        resultDto.setInfo(offset, limit, codes.length, codes);
        return Mono.just(resultDto);
    }

    private Mono<GenericResultDto<CcaeDto>> getBigMallsDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new CcaeDto[0]);
        return Mono.just(genericResultDto);
    }


}
