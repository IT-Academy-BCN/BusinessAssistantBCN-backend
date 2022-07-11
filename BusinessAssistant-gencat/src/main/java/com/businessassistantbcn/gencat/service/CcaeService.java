package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.input.CcaeDto;
import com.businessassistantbcn.gencat.dto.output.CcaeResponseDto;
import com.businessassistantbcn.gencat.dto.output.CodeInfoDto;
import com.businessassistantbcn.gencat.helper.JsonHelper;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class CcaeService {

    @Autowired
    private HttpProxy httpProxy;

    @Autowired
    private PropertiesConfig config;

    @Autowired
    private ModelMapper modelMapper;

    public Mono<CcaeResponseDto[]> getResultDTO(int offset, int limit) throws MalformedURLException {

        return httpProxy.getRequestData(new URL(config.getDs_ccae()), CcaeDto.class)
                .flatMap(ccaeDtos -> {
                    CcaeResponseDto[] responseDtos = ccaeDtos
                            .getData()
                            .stream()
                            .map(this::covertToDto)
                            .toArray(CcaeResponseDto[]::new);

                    CcaeResponseDto[] pagedDto = JsonHelper.filterDto(responseDtos, offset, limit);

                    return Mono.just(pagedDto);
                });

    }

    private CcaeResponseDto covertToDto(List<String> ccaeDto) {
        final int id = 1;
        final int type = 9;
        final int idCcae = 8;
        final int description = 10;
        CcaeResponseDto ccaeResponseDto = modelMapper.map(ccaeDto, CcaeResponseDto.class);
        CodeInfoDto codeInfoDto = new CodeInfoDto();
        ccaeResponseDto.setId(ccaeDto.get(id));
        ccaeResponseDto.setType(ccaeDto.get(type));
        codeInfoDto.setIdCcae(ccaeDto.get(idCcae));
        codeInfoDto.setDescription(ccaeDto.get(description));
        ccaeResponseDto.setCode(codeInfoDto);
        return ccaeResponseDto;
    }


}
