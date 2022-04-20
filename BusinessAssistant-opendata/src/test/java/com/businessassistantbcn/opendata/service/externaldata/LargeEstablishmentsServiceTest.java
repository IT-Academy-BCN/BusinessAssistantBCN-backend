package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.LargeEstablishmentsDto;
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
public class LargeEstablishmentsServiceTest {

    @MockBean
    private PropertiesConfig config;

    @MockBean
    private HttpProxy httpProxy;

    @Autowired
    @InjectMocks
    private LargeEstablishmentsService largeEstablishmentsService;

    private static String urlLargeEstablishments;
    private static final String JSON_FILENAME_LARGE_ESTABLISHMENTS = "json/twoLargeEstablishmentsForTesting.json";
    private static final String JSON_FILENAME_LARGE_ESTABLISHMENTS_ACTIVITIES =
        "json/activitiesFromTwoLargeEstablishmentsForTesting.json";
    private static ObjectMapper mapper;
    private static LargeEstablishmentsDto[] twoLargeEstablishmentsDto;
    private static ActivityInfoDto[] activities;

    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {
        urlLargeEstablishments = "http://www.bcn.cat/tercerlloc/files/" +
            "mercats-centrescomercials/opendatabcn_mercats-centrescomercials_grans-establiments-js.json";

        String largeEstablishmentsAsString = Files.readAllLines(
            Paths.get(
                LargeEstablishmentsServiceTest.class.getClassLoader()
                    .getResource(JSON_FILENAME_LARGE_ESTABLISHMENTS).toURI()
            ),
            StandardCharsets.UTF_8
        ).get(0);
        String largeEstablishmentsActivitiesAsString = Files.readAllLines(
            Paths.get(LargeEstablishmentsServiceTest.class.getClassLoader()
                .getResource(JSON_FILENAME_LARGE_ESTABLISHMENTS_ACTIVITIES).toURI()),
            StandardCharsets.UTF_8
        ).get(0);

        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        twoLargeEstablishmentsDto = mapper.readValue(largeEstablishmentsAsString, LargeEstablishmentsDto[].class);
        activities = mapper.readValue(largeEstablishmentsActivitiesAsString, ActivityInfoDto[].class);
    }

    @Test
    void getPageTest() throws MalformedURLException, JsonProcessingException {
        when(config.getDs_largeestablishments()).thenReturn(urlLargeEstablishments);
        when(httpProxy.getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class)))
            .thenReturn(Mono.just(twoLargeEstablishmentsDto));

        GenericResultDto<LargeEstablishmentsDto> expectedResult = new GenericResultDto<LargeEstablishmentsDto>();
        expectedResult.setInfo(0, -1, twoLargeEstablishmentsDto.length, twoLargeEstablishmentsDto);

        GenericResultDto<LargeEstablishmentsDto> actualResult =
            largeEstablishmentsService.getPage(0, -1).block();
        this.areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
            mapper.writeValueAsString(actualResult.getResults()));

        verify(config, times(1)).getDs_largeestablishments();
        verify(httpProxy, times(1))
            .getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class));
    }

    @Test
    void getPageReturnsLargeEstablishmentsDefaultPageWhenInternalErrorTest() throws MalformedURLException {
        when(config.getDs_largeestablishments()).thenReturn(urlLargeEstablishments);
        when(httpProxy.getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class)))
            .thenThrow(RuntimeException.class);
        this.returnsLargeEstablishmentsDefaultPage(largeEstablishmentsService.getPage(0, -1).block());
        verify(httpProxy, times(1))
            .getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class));
    }

    @Test
    void getPageReturnsLargeEstablishmentsDefaultPageWhenServerIsDownTest() throws MalformedURLException {
        when(config.getDs_largeestablishments()).thenReturn(urlLargeEstablishments);
        when(httpProxy.getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class)))
                .thenReturn(Mono.error(new RuntimeException()));
        this.returnsLargeEstablishmentsDefaultPage(largeEstablishmentsService.getPage(0, -1).block());
        verify(httpProxy, times(1))
            .getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class));
    }

    @Test
    void getLargeEstablishmentsActivitiesTest() throws MalformedURLException, JsonProcessingException {
        when(config.getDs_largeestablishments()).thenReturn(urlLargeEstablishments);
        when(httpProxy.getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class)))
            .thenReturn(Mono.just(twoLargeEstablishmentsDto));

        GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<ActivityInfoDto>();
        expectedResult.setInfo(0, -1, activities.length, activities);

        GenericResultDto<ActivityInfoDto> actualResult =
            largeEstablishmentsService.getLargeEstablishmentsActivities(0, -1).block();
        this.areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
            mapper.writeValueAsString(actualResult.getResults()));

        verify(config, times(1)).getDs_largeestablishments();
        verify(httpProxy, times(1))
            .getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class));
    }

    @Test
    void getLargeEstablishmentsActivitiesReturnsActivitiesDefaultPageWhenInternalErrorTest() throws MalformedURLException {
        when(config.getDs_largeestablishments()).thenReturn(urlLargeEstablishments);
        when(httpProxy.getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class)))
            .thenThrow(RuntimeException.class);
        this.returnsActivitiesDefaultPage(largeEstablishmentsService.getLargeEstablishmentsActivities(0, -1).block());
        verify(httpProxy, times(1))
            .getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class));
    }

    @Test
    void getLargeEstablishmentsActivitiesReturnsActivitiesDefaultPageWhenServerIsDownTest() throws MalformedURLException {
        when(config.getDs_largeestablishments()).thenReturn(urlLargeEstablishments);
        when(httpProxy.getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class)))
            .thenReturn(Mono.error(new RuntimeException()));
        this.returnsActivitiesDefaultPage(largeEstablishmentsService.getLargeEstablishmentsActivities(0, -1).block());
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(LargeEstablishmentsDto[].class));
    }

    private void areOffsetLimitAndCountEqual(GenericResultDto<?> expected, GenericResultDto<?> actual) {
        assertEquals(expected.getOffset(), actual.getOffset());
        assertEquals(expected.getLimit(), actual.getLimit());
        assertEquals(expected.getCount(), actual.getCount());
    }

    private void returnsLargeEstablishmentsDefaultPage(GenericResultDto<LargeEstablishmentsDto> actualResult) {
        GenericResultDto<LargeEstablishmentsDto> expectedResult = new GenericResultDto<>();
        expectedResult.setInfo(0, 0, 0, new LargeEstablishmentsDto[0]);

        this.areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(config, times(1)).getDs_largeestablishments();
    }

    private void returnsActivitiesDefaultPage(GenericResultDto<ActivityInfoDto> actualResult) {
        GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<ActivityInfoDto>();
        expectedResult.setInfo(0, 0, 0, new ActivityInfoDto[0]);

        this.areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(config, times(1)).getDs_largeestablishments();
    }
}