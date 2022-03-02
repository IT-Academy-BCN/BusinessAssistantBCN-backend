package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.ClientProperties;
import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.marketfairs.MarketFairsDto;
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
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MarketFairsServiceTest {

    @MockBean(name = "urlConfig")
    private ClientProperties urlConfig;

    @MockBean
    private HttpProxy httpProxy;

    @Autowired
    @InjectMocks
    private MarketFairsService marketFairsService;

    private static String urlMarketFairs;
    private static final String JSON_FILENAME_MARKET_FAIRS = "json/twoMarketFairsForTesting.json";
    private static ObjectMapper mapper;
    private static MarketFairsDto[] twoMarketFairsDto;

    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {
        urlMarketFairs = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/" +
            "opendatabcn_mercats-centrescomercials_mercats-municipals-js.json";

        String marketFairsAsString = Files.readAllLines(
            Paths.get(MarketFairsService.class.getClassLoader().getResource(JSON_FILENAME_MARKET_FAIRS).toURI()),
            StandardCharsets.UTF_8
        ).get(0);

        mapper = new ObjectMapper();
        twoMarketFairsDto = mapper.readValue(marketFairsAsString, MarketFairsDto[].class);
    }

    @Test
    void getPageTest() throws MalformedURLException, JsonProcessingException {
        when(urlConfig.getDs_marketfairs()).thenReturn(urlMarketFairs);
        when(httpProxy.getRequestData(any(URL.class), eq(MarketFairsDto[].class)))
            .thenReturn(Mono.just(twoMarketFairsDto));

        GenericResultDto<MarketFairsDto> expectedResult = new GenericResultDto<MarketFairsDto>();
        expectedResult.setInfo(0, -1, twoMarketFairsDto.length, twoMarketFairsDto);

        GenericResultDto<MarketFairsDto> actualResult = marketFairsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
            mapper.writeValueAsString(actualResult.getResults()));

        verify(urlConfig, times(1)).getDs_marketfairs();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(MarketFairsDto[].class));
    }

    @Test
    void getPageReturnsMarketFairsDefaultPageWhenMalformedURLTest() throws MalformedURLException {
        when(urlConfig.getDs_marketfairs()).thenReturn("gibberish");
        GenericResultDto<MarketFairsDto> expectedResult = new GenericResultDto<MarketFairsDto>();
        expectedResult.setInfo(0, 0, 0, new MarketFairsDto[0]);

        GenericResultDto<MarketFairsDto> actualResult = marketFairsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(urlConfig, times(1)).getDs_marketfairs();
        verify(httpProxy, times(0)).getRequestData(any(URL.class), eq(MarketFairsDto[].class));
    }

    @Test
    void getPageReturnsMarketFairsDefaultPageTest() throws MalformedURLException {
        when(urlConfig.getDs_marketfairs()).thenReturn(urlMarketFairs);
        when(httpProxy.getRequestData(any(URL.class), eq(MarketFairsDto[].class))).thenThrow(RuntimeException.class);

        GenericResultDto<MarketFairsDto> expectedResult = new GenericResultDto<MarketFairsDto>();
        expectedResult.setInfo(0, 0, 0, new MarketFairsDto[0]);

        GenericResultDto<MarketFairsDto> actualResult = marketFairsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(urlConfig, times(1)).getDs_marketfairs();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(MarketFairsDto[].class));
    }

    private void areOffsetLimitAndCountEqual(GenericResultDto<?> expected, GenericResultDto<?> actual) {
        assertEquals(expected.getOffset(), actual.getOffset());
        assertEquals(expected.getLimit(), actual.getLimit());
        assertEquals(expected.getCount(), actual.getCount());
    }
}
