package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.input.CcaeDto;
import com.businessassistantbcn.gencat.dto.output.CcaeResponseDto;
import com.businessassistantbcn.gencat.dto.output.CodeInfoDto;
import com.businessassistantbcn.gencat.helper.JsonHelper;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import com.jayway.jsonpath.internal.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CcaeService {

    @Autowired
    private HttpProxy httpProxy;

    @Autowired
    private PropertiesConfig config;


    public Mono<CcaeResponseDto[]> getAllCcae(int offset, int limit) throws MalformedURLException {

        return httpProxy.getRequestData(new URL(config.getDs_ccae()), CcaeDto.class)
                .flatMap(ccaeDtos -> {

                    CcaeResponseDto[] responseDtos = ccaeDtos
                            .getData()
                            .stream()
                            .map(this::convertToDto)
                            .toArray(CcaeResponseDto[]::new);

                    CcaeResponseDto[] pagedDto = JsonHelper.filterDto(responseDtos, offset, limit);

                    return Mono.just(pagedDto);
                });

    }

    private CcaeResponseDto convertToDto(List<String> ccaeDto) {
        CcaeResponseDto ccaeResponseDto = new CcaeResponseDto();
        CodeInfoDto codeInfoDto = new CodeInfoDto();

        ccaeResponseDto.setId(ccaeDto.get(config.getId()));
        ccaeResponseDto.setType(ccaeDto.get(config.getType()));

        codeInfoDto.setIdCcae(ccaeDto.get(config.getIdCode()));
        codeInfoDto.setDescription(ccaeDto.get(config.getDescription()));

        ccaeResponseDto.setCode(codeInfoDto);

        return ccaeResponseDto;
    }

}
