package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.marketfairs.MarketFairsDto;
import com.businessassistantbcn.opendata.dto.output.MarketFairsResponseDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
public class MarketFairsServiceTest {

    @MockBean
    private PropertiesConfig config;

    @MockBean
    private HttpProxy httpProxy;

    @Autowired
    @InjectMocks
    private MarketFairsService marketFairsService;

    private static String urlMarketFairs;
    private static final String JSON_FILENAME_MARKET_FAIRS = "json/twoMarketFairsForTesting.json";
    private static ObjectMapper mapper;
    private static MarketFairsDto[] twoMarketFairsDto;
    private static MarketFairsResponseDto[] responseDto;

    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {
        urlMarketFairs = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/" +
            "opendatabcn_mercats-centrescomercials_mercats-municipals-js.json";

        String marketFairsAsString = Files.readAllLines(
            Paths.get(MarketFairsService.class.getClassLoader().getResource(JSON_FILENAME_MARKET_FAIRS).toURI()),
            StandardCharsets.UTF_8
        ).get(0);

        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        twoMarketFairsDto = mapper.readValue(marketFairsAsString, MarketFairsDto[].class);
        
        responseDto = new MarketFairsResponseDto[2];
        MarketFairsResponseDto responseDto1 = new MarketFairsResponseDto();
        responseDto1.setName(twoMarketFairsDto[0].getName());
        responseDto1.setValue(twoMarketFairsDto[0].getValues());
        responseDto1.setActivities(responseDto1.mapClassificationDataListToActivityInfoList(twoMarketFairsDto[0].getClassifications_data()));
        responseDto1.setAddresses(twoMarketFairsDto[0].getAddresses());
        responseDto[0] = responseDto1;
        MarketFairsResponseDto responseDto2 = new MarketFairsResponseDto();
        responseDto2.setName(twoMarketFairsDto[1].getName());
        responseDto2.setValue(twoMarketFairsDto[1].getValues());
        responseDto2.setActivities(responseDto2.mapClassificationDataListToActivityInfoList(twoMarketFairsDto[1].getClassifications_data()));
        responseDto2.setAddresses(twoMarketFairsDto[1].getAddresses());
        responseDto[1] =responseDto2;
    }

    @Test
    void getPageTest() throws MalformedURLException, JsonProcessingException {
        when(config.getDs_marketfairs()).thenReturn(urlMarketFairs);
        when(httpProxy.getRequestData(any(URL.class), eq(MarketFairsDto[].class)))
            .thenReturn(Mono.just(twoMarketFairsDto));

        GenericResultDto<MarketFairsResponseDto> expectedResult = new GenericResultDto<MarketFairsResponseDto>();
        expectedResult.setInfo(0, -1, twoMarketFairsDto.length, responseDto);

        GenericResultDto<MarketFairsResponseDto> actualResult = marketFairsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
            mapper.writeValueAsString(actualResult.getResults()));

        verify(config, times(1)).getDs_marketfairs();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(MarketFairsDto[].class));
    }

    @Test
    void getPageReturnsMarketFairsDefaultPageWhenInternalErrorTest() throws MalformedURLException {
        when(config.getDs_marketfairs()).thenReturn(urlMarketFairs);
        when(httpProxy.getRequestData(any(URL.class), eq(MarketFairsDto[].class))).thenThrow(RuntimeException.class);
        this.returnsMarketFairsDefaultPage(marketFairsService.getPage(0, -1).block());
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(MarketFairsDto[].class));
    }

    @Test
    void getPageReturnsActivitiesDefaultPageWhenServerIsDownTest() throws MalformedURLException {
        when(config.getDs_marketfairs()).thenReturn(urlMarketFairs);
        when(httpProxy.getRequestData(any(URL.class), eq(MarketFairsDto[].class)))
                .thenReturn(Mono.error(new RuntimeException()));
        this.returnsMarketFairsDefaultPage(marketFairsService.getPage(0, -1).block());
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(MarketFairsDto[].class));
    }

    private void areOffsetLimitAndCountEqual(GenericResultDto<?> expected, GenericResultDto<?> actual) {
        assertEquals(expected.getOffset(), actual.getOffset());
        assertEquals(expected.getLimit(), actual.getLimit());
        assertEquals(expected.getCount(), actual.getCount());
    }

    private void returnsMarketFairsDefaultPage(GenericResultDto<MarketFairsResponseDto> actualResult) {
        GenericResultDto<MarketFairsResponseDto> expectedResult = new GenericResultDto<MarketFairsResponseDto>();
        expectedResult.setInfo(0, 0, 0, new MarketFairsResponseDto[0]);

        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(config, times(1)).getDs_marketfairs();
    }
}
