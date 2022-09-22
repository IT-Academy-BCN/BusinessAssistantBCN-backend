/*
package com.businessassistantbcn.gencat.service;


import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.input.CodeInfoDto;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CcaeServiceTest {

    @MockBean
    private HttpProxy httpProxy;

    @MockBean
    private PropertiesConfig config;

    @Autowired
    @InjectMocks
    private CcaeService ccaeService;

    private static ObjectMapper mapper;

    private static final String JSON_FILENAME_CCAE = "json/twoCcaeData.json";

    private static final String CCAE_URL = "https://analisi.transparenciacatalunya.cat/api/views/get5-imi7/rows.json";

    private CcaeDto twoCcaeDto;

    private CcaeDto[] twoCcaeDtoArray;

    private CcaeResponseDto[] responseDto;

    private static String ccaeAsString;


    @BeforeEach
    void setUp() throws URISyntaxException, IOException {

        Path path = Paths.get(CcaeServiceTest.class.getClassLoader().getResource(JSON_FILENAME_CCAE).toURI());

        ccaeAsString = Files.readAllLines(path, StandardCharsets.UTF_8).toString();

        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        twoCcaeDtoArray = mapper.readValue(ccaeAsString, CcaeDto[].class);

        twoCcaeDto = new CcaeDto(twoCcaeDtoArray[0].getData());

        responseDto =  new CcaeResponseDto[2];

        CcaeResponseDto ccaeResponseDto1 = new CcaeResponseDto();
        String id1 = twoCcaeDto.getData().get(0).get(1);
        String type = twoCcaeDto.getData().get(0).get(9);
        String idCodeInfo = twoCcaeDto.getData().get(0).get(8);
        String codeDescription = twoCcaeDto.getData().get(0).get(10);
        CodeInfoDto codeInfoDto = new CodeInfoDto(idCodeInfo, codeDescription);
        ccaeResponseDto1.setId(id1);
        ccaeResponseDto1.setType(type);
        ccaeResponseDto1.setCode(codeInfoDto);
        responseDto[0] = ccaeResponseDto1;

        CcaeResponseDto ccaeResponseDto2 = new CcaeResponseDto();
        String id2 = twoCcaeDto.getData().get(1).get(1);
        String type2 = twoCcaeDto.getData().get(1).get(9);
        String idCodeInfo2 = twoCcaeDto.getData().get(1).get(8);
        String codeDescription2 = twoCcaeDto.getData().get(1).get(10);
        CodeInfoDto codeInfoDto2 = new CodeInfoDto(idCodeInfo2, codeDescription2);
        ccaeResponseDto2.setId(id2);
        ccaeResponseDto2.setType(type2);
        ccaeResponseDto2.setCode(codeInfoDto2);
        responseDto[1] = ccaeResponseDto2;
    }


    @Test
    void convertToDtoTest() throws MalformedURLException {
        when(config.getDs_ccae()).thenReturn(CCAE_URL);
        when(config.getId()).thenReturn(1);
        when(config.getType()).thenReturn(9);
        when(config.getIdCode()).thenReturn(8);
        when(config.getDescription()).thenReturn(10);

        when(httpProxy.getRequestData(any(URL.class), eq(CcaeDto.class))).thenReturn(Mono.just(twoCcaeDto));


        Mono<GenericResultDto<CcaeResponseDto>> ccaeResponseDto = ccaeService.getPage(0, -1);

        StepVerifier.create(ccaeResponseDto)
                .expectNextMatches(ccaeResponseDtos -> ccaeResponseDtos.getResults()[0].getId().equalsIgnoreCase(responseDto[0].getId()))
                .expectComplete()
                .verify();

        StepVerifier.create(ccaeResponseDto)
                .expectNextMatches(ccaeResponseDtos -> ccaeResponseDtos.getResults().length==2)
                .expectComplete()
                .verify();
    }

    @Test
    void getPageTest() throws MalformedURLException, JsonProcessingException {

        when(config.getDs_ccae()).thenReturn(CCAE_URL);
        when(config.getId()).thenReturn(1);
        when(config.getType()).thenReturn(9);
        when(config.getIdCode()).thenReturn(8);
        when(config.getDescription()).thenReturn(10);

        when(httpProxy.getRequestData(any(URL.class), eq(CcaeDto.class))).thenReturn(Mono.just(twoCcaeDto));

        GenericResultDto<CcaeResponseDto> expectedResul = new GenericResultDto<>();
        expectedResul.setInfo(0, -1, responseDto.length, responseDto);

        GenericResultDto<CcaeResponseDto> actualResult = ccaeService.getPage(0, -1).block();

        assertThat(mapper.writeValueAsString(expectedResul.getResults())).isEqualTo(mapper.writeValueAsString(actualResult.getResults()));
    }


}
*/
