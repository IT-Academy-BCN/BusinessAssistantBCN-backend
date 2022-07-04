package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.input.CcaeDto;
import com.businessassistantbcn.gencat.dto.output.CcaeResponseDto;
import com.businessassistantbcn.gencat.dto.output.CodeInfoDto;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@Service
public class CcaeService {

    @Autowired
    private HttpProxy httpProxy;

    @Autowired
    private PropertiesConfig config;

    @Autowired
    private ModelMapper modelMapper;

    public Mono<CcaeDto[]> getResultDTO() throws MalformedURLException {

        return httpProxy.getRequestData(new URL(config.getDs_ccae()), CcaeDto[].class)
                /*.flatMap(ccaeDtos -> {
                    CcaeResponseDto[] responseDtos = Arrays.stream(ccaeDtos)
                            .map(this::covertToDto)
                            .toArray(CcaeResponseDto[]::new);
                    return Mono.just(responseDtos);
                })*/;

    }

    /*private CcaeResponseDto covertToDto(CcaeDto ccaeDto) {
        CcaeResponseDto ccaeResponseDto = new CcaeResponseDto();
        CodeInfoDto codeInfoDto = new CodeInfoDto();
        codeInfoDto.setIdCcae(ccaeDto.getIdCcae());
        codeInfoDto.setDescription(ccaeDto.getDescription());
        ccaeResponseDto.setId(ccaeDto.getId());
        ccaeResponseDto.setType(ccaeDto.getType());
        ccaeResponseDto.setCode(codeInfoDto);
        return ccaeResponseDto;
    }*/


}
