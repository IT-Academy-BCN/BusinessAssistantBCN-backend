package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.SearchDTO;
import com.businessassistantbcn.opendata.dto.input.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.output.BigMallsResponseDto;
import com.businessassistantbcn.opendata.dto.output.data.AddressInfoDto;
import com.businessassistantbcn.opendata.dto.output.data.CoordinateInfoDto;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BigMallsServiceTest {

    @MockBean
    private PropertiesConfig config;

    @MockBean
    private HttpProxy httpProxy;

    @Autowired
    @InjectMocks
    private BigMallsService bigMallsService;

    private static String URL_BIG_MALLS;
    private static final String JSON_FILENAME_BIG_MALLS = "json/twoBigMallsForTesting.json";
    private static final String JSON_FILENAME_BIG_MALLS_ACTIVITIES = "json/activitiesFromTwoBigMallsForTesting.json";
    private static ObjectMapper mapper;
    private static BigMallsDto[] twoBigMallsDto;
    private static BigMallsResponseDto[] responseDto;
    private static ActivityInfoDto[] activities;

    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {

        URL_BIG_MALLS = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/" +
                        "opendatabcn_mercats-centrescomercials_grans-centres-comercials-js.json";

        String bigMallsAsString = Files.readAllLines(
                Paths.get(BigMallsServiceTest.class.getClassLoader().getResource(JSON_FILENAME_BIG_MALLS).toURI()),
                StandardCharsets.UTF_8
        ).get(0);
        String bigMallsActivitiesAsString = Files.readAllLines(
                Paths.get(BigMallsServiceTest.class.getClassLoader()
                        .getResource(JSON_FILENAME_BIG_MALLS_ACTIVITIES).toURI()),
                StandardCharsets.UTF_8
        ).get(0);

        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        twoBigMallsDto = mapper.readValue(bigMallsAsString, BigMallsDto[].class);
        activities = mapper.readValue(bigMallsActivitiesAsString, ActivityInfoDto[].class);

        responseDto = new BigMallsResponseDto[2];
        BigMallsResponseDto responseDto1 = new BigMallsResponseDto();
        List<AddressInfoDto> addressInfoDto1 = new ArrayList<>();
        AddressInfoDto addressInfoDto11 = new AddressInfoDto();
        addressInfoDto11.setStreet_name(twoBigMallsDto[0].getAddresses().get(0).getAddress_name());
        addressInfoDto11.setStreet_number(twoBigMallsDto[0].getAddresses().get(0).getStreet_number_1());
        addressInfoDto11.setZip_code(twoBigMallsDto[0].getAddresses().get(0).getZip_code());
        addressInfoDto11.setDistrict_id(twoBigMallsDto[0].getAddresses().get(0).getDistrict_id());
        addressInfoDto11.setTown(twoBigMallsDto[0].getAddresses().get(0).getTown());
        CoordinateInfoDto coords1 = new CoordinateInfoDto();
        coords1.setX(twoBigMallsDto[0].getCoordinates().getX());
        coords1.setY(twoBigMallsDto[0].getCoordinates().getY());
        addressInfoDto11.setLocation(coords1);
        addressInfoDto1.add(addressInfoDto11);
        responseDto1.setName(twoBigMallsDto[0].getName());
        responseDto1.setWeb(twoBigMallsDto[0].getValues().getUrl_value());
        responseDto1.setEmail(twoBigMallsDto[0].getValues().getEmail_value());
        responseDto1.setPhone(twoBigMallsDto[0].getValues().getPhone_value());
        responseDto1.setActivities(responseDto1.mapClassificationDataListToActivityInfoList(twoBigMallsDto[0].getClassifications_data()));
        responseDto1.setAddresses(addressInfoDto1);
        responseDto[0] = responseDto1;

        BigMallsResponseDto responseDto2 = new BigMallsResponseDto();
        List<AddressInfoDto> addressInfoDto2 = new ArrayList<>();
        AddressInfoDto addressInfoDto21 = new AddressInfoDto();
        addressInfoDto21.setStreet_name(twoBigMallsDto[1].getAddresses().get(0).getAddress_name());
        addressInfoDto21.setStreet_number(twoBigMallsDto[1].getAddresses().get(0).getStreet_number_1());
        addressInfoDto21.setZip_code(twoBigMallsDto[1].getAddresses().get(0).getZip_code());
        addressInfoDto21.setDistrict_id(twoBigMallsDto[1].getAddresses().get(0).getDistrict_id());
        addressInfoDto21.setTown(twoBigMallsDto[1].getAddresses().get(0).getTown());
        CoordinateInfoDto coords2 = new CoordinateInfoDto();
        coords2.setX(twoBigMallsDto[1].getCoordinates().getX());
        coords2.setY(twoBigMallsDto[1].getCoordinates().getY());
        addressInfoDto21.setLocation(coords2);
        addressInfoDto2.add(addressInfoDto21);
        responseDto2.setName(twoBigMallsDto[1].getName());
        responseDto2.setWeb(twoBigMallsDto[1].getValues().getUrl_value());
        responseDto2.setEmail(twoBigMallsDto[1].getValues().getEmail_value());
        responseDto2.setPhone(twoBigMallsDto[1].getValues().getPhone_value());
        responseDto2.setActivities(responseDto2.mapClassificationDataListToActivityInfoList(twoBigMallsDto[1].getClassifications_data()));
        responseDto2.setAddresses(addressInfoDto2);
        responseDto[1] =responseDto2;
    }

    @Test
    void getPageTest() throws JsonProcessingException {
        when(config.getDs_bigmalls()).thenReturn(URL_BIG_MALLS);
        when(httpProxy.getRequestData(any(URI.class), eq(BigMallsDto[].class))).thenReturn(Mono.just(twoBigMallsDto));

        GenericResultDto<BigMallsResponseDto> expectedResult = new GenericResultDto<BigMallsResponseDto>();
        expectedResult.setInfo(0, -1, twoBigMallsDto.length, responseDto);

        GenericResultDto<BigMallsResponseDto> actualResult = bigMallsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
                mapper.writeValueAsString(actualResult.getResults()));

        verify(config, times(1)).getDs_bigmalls();
        verify(httpProxy, times(1)).getRequestData(any(URI.class), eq(BigMallsDto[].class));
    }

    @Test
    void getPageReturnsBigMallsDefaultPageWhenInternalErrorTest() {
        when(config.getDs_bigmalls()).thenReturn(URL_BIG_MALLS);
        when(httpProxy.getRequestData(any(URI.class), eq(BigMallsDto[].class))).thenReturn(Mono.error(new RuntimeException()));
        this.returnsBigMallsDefaultPage(bigMallsService.getPage(0, -1).block());
        verify(httpProxy, times(1)).getRequestData(any(URI.class), eq(BigMallsDto[].class));
    }

    @Test
    void getPageReturnsBigMallsDefaultPageWhenServerIsDownTest() {
        when(config.getDs_bigmalls()).thenReturn(URL_BIG_MALLS);
        when(httpProxy.getRequestData(any(URI.class), eq(BigMallsDto[].class)))
                .thenReturn(Mono.error(new RuntimeException()));
        this.returnsBigMallsDefaultPage(bigMallsService.getPage(0, -1).block());
        verify(httpProxy, times(1)).getRequestData(any(URI.class), eq(BigMallsDto[].class));
    }

    @Test
    void getBigMallsActivitiesTest() throws JsonProcessingException {
        when(config.getDs_bigmalls()).thenReturn(URL_BIG_MALLS);
        when(httpProxy.getRequestData(any(URI.class), eq(BigMallsDto[].class))).thenReturn(Mono.just(twoBigMallsDto));

        GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<ActivityInfoDto>();
        expectedResult.setInfo(0, -1, activities.length, activities);

        GenericResultDto<ActivityInfoDto> actualResult = bigMallsService.getBigMallsActivities(0, -1).block();
        this.areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
                mapper.writeValueAsString(actualResult.getResults()));

        verify(config, times(1)).getDs_bigmalls();
        verify(httpProxy, times(1)).getRequestData(any(URI.class), eq(BigMallsDto[].class));
    }

    @Test
    void getBigMallsActivitiesReturnsActivitiesDefaultPageWhenInternalErrorTest() {
        when(config.getDs_bigmalls()).thenReturn(URL_BIG_MALLS);
        when(httpProxy.getRequestData(any(URI.class), eq(BigMallsDto[].class))).thenReturn(Mono.error(new RuntimeException()));
        this.returnsActivitiesDefaultPage(bigMallsService.getBigMallsActivities(0, -1).block());
        verify(httpProxy, times(1)).getRequestData(any(URI.class), eq(BigMallsDto[].class));
    }

    @Test
    void getBigMallsActivitiesReturnsActivitiesDefaultPageWhenServerIsDownTest() {
        when(config.getDs_bigmalls()).thenReturn(URL_BIG_MALLS);
        when(httpProxy.getRequestData(any(URI.class), eq(BigMallsDto[].class)))
                .thenReturn(Mono.error(new RuntimeException()));
        this.returnsActivitiesDefaultPage(bigMallsService.getBigMallsActivities(0, -1).block());
        verify(httpProxy, times(1)).getRequestData(any(URI.class), eq(BigMallsDto[].class));
    }

    private void areOffsetLimitAndCountEqual(GenericResultDto<?> expected, GenericResultDto<?> actual) {
        assertEquals(expected.getOffset(), actual.getOffset());
        assertEquals(expected.getLimit(), actual.getLimit());
        assertEquals(expected.getCount(), actual.getCount());
    }

    private void returnsBigMallsDefaultPage(GenericResultDto<BigMallsResponseDto> actualResult) {
        GenericResultDto<BigMallsDto> expectedResult = new GenericResultDto<>();
        expectedResult.setInfo(0, 0, 0, new BigMallsDto[0]);

        this.areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(config, times(1)).getDs_bigmalls();
    }

    private void returnsActivitiesDefaultPage(GenericResultDto<ActivityInfoDto> actualResult) {
        GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<>();
        expectedResult.setInfo(0, 0, 0, new ActivityInfoDto[0]);

        this.areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(config, times(1)).getDs_bigmalls();
    }

    @Test
    void getPageByActivity() {
        when(config.getDs_bigmalls()).thenReturn(URL_BIG_MALLS);
        when(httpProxy.getRequestData(any(URI.class), eq(BigMallsDto[].class)))
                .thenReturn(Mono.just(twoBigMallsDto));

        GenericResultDto<BigMallsResponseDto> actualResult =
                bigMallsService.getPageByActivity(0, -1, "43326348").block();

        assertEquals(0, actualResult.getOffset());
        assertEquals(-1, actualResult.getLimit());
        assertEquals(43326348, Arrays.stream(actualResult.getResults()).toList()
                .get(0).getActivities().get(0).getActivityId());
    }

    @Test
    void getPageByDistrictTest() {
        when(config.getDs_bigmalls()).thenReturn(URL_BIG_MALLS);
        when(httpProxy.getRequestData(any(URI.class), eq(BigMallsDto[].class)))
                .thenReturn(Mono.just(twoBigMallsDto));

        GenericResultDto<BigMallsResponseDto> actualResult =
                bigMallsService.getPageByDistrict(0, -1, 2).block();

        assertEquals(0, actualResult.getOffset());
        assertEquals(-1, actualResult.getLimit());
        assertEquals("02", Arrays.stream(actualResult.getResults()).toList()
                .get(0).getAddresses().get(0).getDistrict_id());
    }

    @Test
    void getPageBySearchTest() {
        when(config.getDs_bigmalls()).thenReturn(URL_BIG_MALLS);
        when(httpProxy.getRequestData(any(URI.class), eq(BigMallsDto[].class)))
                .thenReturn(Mono.just(twoBigMallsDto));

        SearchDTO searchParams = new SearchDTO(new int[]{2}, new int[]{43326349});

        GenericResultDto<BigMallsResponseDto> actualResult =
                bigMallsService.getPageBySearch(0, -1, searchParams).block();

        assertEquals(0, actualResult.getOffset());
        assertEquals(-1, actualResult.getLimit());

        assertEquals("02", Arrays.stream(actualResult.getResults()).toList()
                .get(0).getAddresses().get(0).getDistrict_id());
        assertEquals(43326349, Arrays.stream(actualResult.getResults()).toList()
                .get(0).getActivities().get(0).getActivityId());
    }
}