package com.businessassistantbcn.opendata.service.externaldata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.LargeEstablishmentsDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LargeEstablishmentsServiceTest {


    @MockBean
    private PropertiesConfig propertiesConfigMock;

    @MockBean
    private HttpProxy httpProxyMock;

    @Autowired
    @InjectMocks
    private LargeEstablishmentsService underTestClass;

    private final String URL_LARGE_EST = "http://www.bcn.cat/"
            + "tercerlloc/files/mercats-centrescomercials/"
            + "opendatabcn_mercats-centrescomercials_grans-establiments-js.json";

    private String largeEstablishmentsDtoToString;
    private ObjectMapper objectMapper;
    private LargeEstablishmentsDto[] arrayDtos;

    @BeforeEach
    void init() throws IOException, URISyntaxException {
        largeEstablishmentsDtoToString = new String(Files.readAllBytes(
                Paths.get(LargeEstablishmentsServiceTest.class.getClassLoader().getResource("json/largeEstablishments.json").toURI())));
        objectMapper = new ObjectMapper();
        arrayDtos = objectMapper
                .readValue(largeEstablishmentsDtoToString, LargeEstablishmentsDto[].class);
    }

    @Test
    void getPageTest() throws JsonMappingException, JsonProcessingException, IOException {
        int limit = 10;
        Mockito
                .when(propertiesConfigMock.getDs_largeestablishments())
                .thenReturn(URL_LARGE_EST);

        Mockito
                .when(httpProxyMock.getRequestData(Mockito.any(URL.class), Mockito.eq(LargeEstablishmentsDto[].class)))
                .thenReturn(Mono.just(new LargeEstablishmentsDto[limit]));

        GenericResultDto<LargeEstablishmentsDto> genericResultDto = underTestClass.getPage(0, limit).block();

        Assertions.assertEquals(limit, genericResultDto.getCount());
        Mockito.verify(propertiesConfigMock).getDs_largeestablishments();
        Mockito.verify(httpProxyMock).getRequestData(Mockito.any(URL.class), Mockito.eq(LargeEstablishmentsDto[].class));

    }
    @Test
    void getPageByDistrictTest() throws JsonMappingException, JsonProcessingException, MalformedURLException {
        int district = 10;
        Mockito
                .when(propertiesConfigMock.getDs_largeestablishments())
                .thenReturn(URL_LARGE_EST);
        Mockito
                .when(httpProxyMock.getRequestData(Mockito.any(URL.class), Mockito.eq(LargeEstablishmentsDto[].class)))
                .thenReturn(Mono.just(arrayDtos));
        GenericResultDto<LargeEstablishmentsDto> genericResultDto = underTestClass.getPageByDistrict(0, -1, district).block();

        Assertions.assertEquals(6, genericResultDto.getCount());
        Mockito.verify(propertiesConfigMock).getDs_largeestablishments();
        Mockito.verify(httpProxyMock).getRequestData(Mockito.any(URL.class), Mockito.eq(LargeEstablishmentsDto[].class));

    }
    @Test
    void getPageByActivityTest() throws MalformedURLException {
        String activityId = "32804746";
        Mockito
                .when(propertiesConfigMock.getDs_largeestablishments())
                .thenReturn(URL_LARGE_EST);
        Mockito
                .when(httpProxyMock.getRequestData(Mockito.any(URL.class), Mockito.eq(LargeEstablishmentsDto[].class)))
                .thenReturn(Mono.just(arrayDtos));

        GenericResultDto<LargeEstablishmentsDto> genericResultDto = underTestClass.getPageByActivity(0, -1, activityId).block();

        Assertions.assertEquals(1, genericResultDto.getCount());
        Mockito.verify(propertiesConfigMock).getDs_largeestablishments();
        Mockito.verify(httpProxyMock).getRequestData(Mockito.any(URL.class), Mockito.eq(LargeEstablishmentsDto[].class));

    }
    @Test
    void getLargeEstablishmentsAllActivitiesTest() throws MalformedURLException {
        int expectedActivities = 100;

        Mockito
                .when(propertiesConfigMock.getDs_largeestablishments())
                .thenReturn(URL_LARGE_EST);
        Mockito
                .when(httpProxyMock.getRequestData(Mockito.any(URL.class), Mockito.eq(LargeEstablishmentsDto[].class)))
                .thenReturn(Mono.just(arrayDtos));

        GenericResultDto<ActivityInfoDto> genericResultDto = underTestClass.getLargeEstablishmentsAllActivities(0, -1).block();

        int actualActivities = genericResultDto.getCount();

        Assertions.assertEquals(expectedActivities, actualActivities);
        Mockito.verify(propertiesConfigMock).getDs_largeestablishments();
        Mockito.verify(httpProxyMock).getRequestData(Mockito.any(URL.class), Mockito.eq(LargeEstablishmentsDto[].class));
    }

    @Test
    void shouldReturnPageDefaultWhenMalformedURL() throws MalformedURLException {
        Mockito
                .when(propertiesConfigMock.getDs_largeestablishments())
                .thenReturn("malformedUrl");

        GenericResultDto<LargeEstablishmentsDto> genericResultDto = underTestClass.getPage(0, -1).block();

        Assertions.assertEquals(0, genericResultDto.getCount());
        Assertions.assertEquals(0, genericResultDto.getOffset());
        Assertions.assertEquals(0, genericResultDto.getLimit());
        Assertions.assertEquals(0, genericResultDto.getResults().length);

        Mockito.verify(propertiesConfigMock).getDs_largeestablishments();
    }

} 
