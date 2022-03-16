package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.municipalmarkets.MunicipalMarketsDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MunicipalMarketsServiceTest {

    @MockBean
    private PropertiesConfig config;

    @MockBean
    private HttpProxy httpProxy;

    @Autowired
    @InjectMocks
    private MunicipalMarketsService municipalMarketsService;

    private static String urlMunicipalMarkets;
    private static final String JSON_FILENAME_ECONOMIC_MUNICIPAL_MARKETS = "json/twoMunicipalMarketsForTesting.json";
    private static ObjectMapper mapper;
    private static MunicipalMarketsDto[] twoMunicipalMarkets;

    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {
        urlMunicipalMarkets = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/" +
            "opendatabcn_mercats-centrescomercials_mercats-municipals-js.json";

        String municipalMarketsAsString = Files.readAllLines(
            Paths.get(MunicipalMarketsService.class.getClassLoader()
                    .getResource(JSON_FILENAME_ECONOMIC_MUNICIPAL_MARKETS).toURI()),
            StandardCharsets.UTF_8
        ).get(0);

        mapper = new ObjectMapper();
        twoMunicipalMarkets =
                mapper.readValue(municipalMarketsAsString, MunicipalMarketsDto[].class);
    }

    @Test
    void getPageTest() throws MalformedURLException, JsonProcessingException {
        when(config.getDs_municipalmarkets()).thenReturn(urlMunicipalMarkets);
        when(httpProxy.getRequestData(any(URL.class), eq(MunicipalMarketsDto[].class)))
            .thenReturn(Mono.just(twoMunicipalMarkets));

        GenericResultDto<MunicipalMarketsDto> expectedResult = new GenericResultDto<MunicipalMarketsDto>();
        expectedResult.setInfo(0, -1, twoMunicipalMarkets.length, twoMunicipalMarkets);

        GenericResultDto<MunicipalMarketsDto> actualResult = municipalMarketsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
            mapper.writeValueAsString(actualResult.getResults()));

        verify(config, times(1)).getDs_municipalmarkets();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(MunicipalMarketsDto[].class));
    }

    @Test
    void getPageReturnsMunicipalMarketsDefaultPageWhenMalformedURLTest() throws MalformedURLException {
        when(config.getDs_municipalmarkets()).thenReturn("gibberish");
        GenericResultDto<MunicipalMarketsDto> expectedResult = new GenericResultDto<MunicipalMarketsDto>();
        expectedResult.setInfo(0, 0, 0, new MunicipalMarketsDto[0]);

        GenericResultDto<MunicipalMarketsDto> actualResult = municipalMarketsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(config, times(1)).getDs_municipalmarkets();
        verify(httpProxy, times(0)).getRequestData(any(URL.class), eq(MunicipalMarketsDto[].class));
    }

    @Test
    void getPageReturnsMunicipalMarketsDefaultPageTest() throws MalformedURLException {
        when(config.getDs_municipalmarkets()).thenReturn(urlMunicipalMarkets);
        when(httpProxy.getRequestData(any(URL.class), eq(MunicipalMarketsDto[].class)))
            .thenThrow(RuntimeException.class);

        GenericResultDto<MunicipalMarketsDto> expectedResult = new GenericResultDto<MunicipalMarketsDto>();
        expectedResult.setInfo(0, 0, 0, new MunicipalMarketsDto[0]);

        GenericResultDto<MunicipalMarketsDto> actualResult = municipalMarketsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(config, times(1)).getDs_municipalmarkets();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(MunicipalMarketsDto[].class));
    }

    private void areOffsetLimitAndCountEqual(GenericResultDto<?> expected, GenericResultDto<?> actual) {
        assertEquals(expected.getOffset(), actual.getOffset());
        assertEquals(expected.getLimit(), actual.getLimit());
        assertEquals(expected.getCount(), actual.getCount());
    }

}
