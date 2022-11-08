package com.businessassistantbcn.gencat.service;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.dto.io.CodeInfoDto;
import com.businessassistantbcn.gencat.helper.CcaeDeserializer;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class CcaeServiceTest {

    @MockBean
    private HttpProxy httpProxy;

    @MockBean
    private PropertiesConfig config;

    @MockBean
    private CcaeDeserializer ccaeDeserializer;

    @Autowired
    @InjectMocks
    private CcaeService ccaeService;

    private static final String CCAE_URL = "https://analisi.transparenciacatalunya.cat/api/views/get5-imi7/rows.json";

    private List<CcaeDto> allData;
    private CcaeDto[] responseDto;

    @BeforeEach
    void setUp() {

        allData = new ArrayList<>();

        CcaeDto ccaeDto1 = new CcaeDto();
        String id1 = "00000000-0000-0000-D7DC-CC770365D8FF";
        String type1 = "Secció";
        String idCodeInfo1 = "A";
        String codeDescription1 = "Agricultura, ramaderia, silvicultura i pesca";
        CodeInfoDto codeInfoDto1 = new CodeInfoDto(idCodeInfo1, codeDescription1);
        ccaeDto1.setId(id1);
        ccaeDto1.setType(type1);
        ccaeDto1.setCode(codeInfoDto1);
        allData.add(ccaeDto1);

        CcaeDto ccaeDto2 = new CcaeDto();
        String id2 = "00000000-0000-0000-2335-839767DDAEAB";
        String type2 = "Divisió";
        String idCodeInfo2 = "01";
        String codeDescription2 = "Agricultura, ramaderia, caça i activitats dels serveis que s'hi relacionen";
        CodeInfoDto codeInfoDto2 = new CodeInfoDto(idCodeInfo2, codeDescription2);
        ccaeDto2.setId(id2);
        ccaeDto2.setType(type2);
        ccaeDto2.setCode(codeInfoDto2);
        allData.add(ccaeDto2);

        responseDto = allData.toArray(CcaeDto[]::new);
    }


    @Test
    void getPageUnitTest() throws MalformedURLException {

        when(config.getDs_ccae()).thenReturn(CCAE_URL);
        when(ccaeDeserializer.deserialize(any(Object.class))).thenReturn(allData);
        when(httpProxy.getRequestData(any(URL.class), eq(Object.class))).thenReturn(Mono.just(responseDto));

        Mono<GenericResultDto<CcaeDto>> ccaeResponseDto = ccaeService.getPage(0, -1);

        StepVerifier.create(ccaeResponseDto)
                .expectNextMatches(ccaeResponseDtos ->
                        ccaeResponseDtos.getResults()[0].getId().equalsIgnoreCase(responseDto[0].getId()) &&
                                ccaeResponseDtos.getResults()[1].getId().equalsIgnoreCase(responseDto[1].getId()) &&
                                ccaeResponseDtos.getResults().length == 2 &&
                                ccaeResponseDtos.getOffset() == 0 &&
                                ccaeResponseDtos.getLimit() == -1)
                .expectComplete()
                .verify();

        verify(config, times(1)).getDs_ccae();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(Object.class));
    }

    @Test
    void circuitBreakerConfigurationTest() throws MalformedURLException {

        when(config.getDs_ccae()).thenReturn(CCAE_URL);
        when(ccaeDeserializer.deserialize(any(Object.class))).thenReturn(allData);
        when(httpProxy.getRequestData(any(URL.class), eq(Object.class))).thenReturn(Mono.error(new RuntimeException()));

        GenericResultDto<CcaeDto> expected = new GenericResultDto<>();
        expected.setInfo(0, 0, 0, new CcaeDto[0]);

        GenericResultDto<CcaeDto> actual = ccaeService.getPage(0, -1).block();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(Object.class));
    }
}
