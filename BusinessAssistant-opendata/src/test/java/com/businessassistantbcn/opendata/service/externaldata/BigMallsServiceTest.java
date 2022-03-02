package com.businessassistantbcn.opendata.service.externaldata;
import com.businessassistantbcn.opendata.config.ClientProperties;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BigMallsServiceTest {

    @MockBean(name = "urlConfig")
    private ClientProperties urlConfig;

    @MockBean
    private HttpProxy httpProxy;

    @Autowired
    @InjectMocks
    private BigMallsService bigMallsService;

    private static String urlBigMalls;
    private static final String JSON_FILENAME_BIG_MALLS = "json/twoBigMallsForTesting.json";
    private static final String JSON_FILENAME_BIG_MALLS_ACTIVITIES = "json/activitiesFromTwoBigMallsForTesting.json";
    private static ObjectMapper mapper;
    private static BigMallsDto[] twoBigMallsDto;
    private static ActivityInfoDto[] activities;

    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {
        urlBigMalls = "http://www.bcn.cat/tercerlloc/files/" +
            "mercats-centrescomercials/opendatabcn_mercats-centrescomercials_grans-centres-comercials-js.json";

        String bigMallsAsString = Files.readAllLines(
                Paths.get(BigMallsServiceTest.class.getClassLoader().getResource(JSON_FILENAME_BIG_MALLS).toURI()),
                StandardCharsets.UTF_8
        ).get(0);
        String bigMallsActivitiesAsString = Files.readAllLines(
                Paths.get(BigMallsServiceTest.class.getClassLoader()
                    .getResource(JSON_FILENAME_BIG_MALLS_ACTIVITIES).toURI()),
                StandardCharsets.UTF_8
        ).get(0);

        mapper = new ObjectMapper();
        twoBigMallsDto = mapper.readValue(bigMallsAsString, BigMallsDto[].class);
        activities = mapper.readValue(bigMallsActivitiesAsString, ActivityInfoDto[].class);
    }

    @Test
    void getPageTest() throws MalformedURLException, JsonProcessingException {
        when(urlConfig.getDs_bigmalls()).thenReturn(urlBigMalls);
        when(httpProxy.getRequestData(any(URL.class), eq(BigMallsDto[].class))).thenReturn(Mono.just(twoBigMallsDto));

        GenericResultDto<BigMallsDto> expectedResult = new GenericResultDto<BigMallsDto>();
        expectedResult.setInfo(0, -1, twoBigMallsDto.length, twoBigMallsDto);

        GenericResultDto<BigMallsDto> actualResult = bigMallsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
            mapper.writeValueAsString(actualResult.getResults()));

        verify(urlConfig, times(1)).getDs_bigmalls();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(BigMallsDto[].class));
    }

    @Test
    void getPageReturnsBigMallsDefaultPageWhenMalformedURLTest() throws MalformedURLException {
        when(urlConfig.getDs_bigmalls()).thenReturn("gibberish");
        GenericResultDto<BigMallsDto> expectedResult = new GenericResultDto<BigMallsDto>();
        expectedResult.setInfo(0, 0, 0, new BigMallsDto[0]);

        GenericResultDto<BigMallsDto> actualResult = bigMallsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(urlConfig, times(1)).getDs_bigmalls();
        verify(httpProxy, times(0)).getRequestData(any(URL.class), eq(BigMallsDto[].class));
    }

    @Test
    void getPageReturnsBigMallsDefaultPageTest() throws MalformedURLException {
        when(urlConfig.getDs_bigmalls()).thenReturn(urlBigMalls);
        when(httpProxy.getRequestData(any(URL.class), any(Class.class))).thenThrow(RuntimeException.class);

        GenericResultDto<BigMallsDto> expectedResult = new GenericResultDto<BigMallsDto>();
        expectedResult.setInfo(0, 0, 0, new BigMallsDto[0]);

        GenericResultDto<BigMallsDto> actualResult = bigMallsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(urlConfig, times(1)).getDs_bigmalls();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(BigMallsDto[].class));
    }

    @Test
    void getBigMallsAllActivitiesTest() throws MalformedURLException, JsonProcessingException {
        when(urlConfig.getDs_bigmalls()).thenReturn(urlBigMalls);
        when(httpProxy.getRequestData(any(URL.class), eq(BigMallsDto[].class))).thenReturn(Mono.just(twoBigMallsDto));

        GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<ActivityInfoDto>();
        expectedResult.setInfo(0, -1, activities.length, activities);

        GenericResultDto<ActivityInfoDto> actualResult = bigMallsService.bigMallsAllActivities(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
            mapper.writeValueAsString(actualResult.getResults()));

        verify(urlConfig, times(1)).getDs_bigmalls();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(BigMallsDto[].class));
    }

    @Test
    void getBigMallsAllActivitiesReturnsBigMallsDefaultPageWhenMalformedURLTest() throws MalformedURLException {
        when(urlConfig.getDs_bigmalls()).thenReturn("gibberish");
        GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<ActivityInfoDto>();
        expectedResult.setInfo(0, 0, 0, new ActivityInfoDto[0]);

        GenericResultDto<ActivityInfoDto> actualResult = bigMallsService.bigMallsAllActivities(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(urlConfig, times(1)).getDs_bigmalls();
        verify(httpProxy, times(0)).getRequestData(any(URL.class), eq(BigMallsDto[].class));
    }

    @Test
    void getBigMallsAllActivitiesReturnsActivitiesDefaultPageTest() throws MalformedURLException {
        when(urlConfig.getDs_bigmalls()).thenReturn(urlBigMalls);
        when(httpProxy.getRequestData(any(URL.class), eq(BigMallsDto[].class))).thenThrow(RuntimeException.class);

        GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<ActivityInfoDto>();
        expectedResult.setInfo(0, 0, 0, new ActivityInfoDto[0]);

        GenericResultDto<ActivityInfoDto> actualResult = bigMallsService.bigMallsAllActivities(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(urlConfig, times(1)).getDs_bigmalls();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(BigMallsDto[].class));
    }

    private void areOffsetLimitAndCountEqual(GenericResultDto<?> expected, GenericResultDto<?> actual) {
        assertEquals(expected.getOffset(), actual.getOffset());
        assertEquals(expected.getLimit(), actual.getLimit());
        assertEquals(expected.getCount(), actual.getCount());
    }

}