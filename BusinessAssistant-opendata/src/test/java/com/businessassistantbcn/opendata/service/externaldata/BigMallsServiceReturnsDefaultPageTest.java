package com.businessassistantbcn.opendata.service.externaldata;
import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = {"connection_timeout: 500"})
public class BigMallsServiceReturnsDefaultPageTest {

    @MockBean
    private PropertiesConfig config;

    @MockBean
    private HttpProxy httpProxy;

    @Autowired
    @InjectMocks
    private BigMallsService bigMallsService;

    private static String urlBigMalls;
    private static final String JSON_FILENAME = "json/allBigMallsForTesting.json";
    private static String JSON_TEST_FILE;
    private static ObjectMapper mapper;
    private static BigMallsDto[] allBigMallsDto;

    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {
        urlBigMalls = "http://www.bcn.cat/tercerlloc/files/" +
            "mercats-centrescomercials/opendatabcn_mercats-centrescomercials_grans-centres-comercials-js.json";
        JSON_TEST_FILE = new String(Files.readAllBytes(
            Paths.get(BigMallsServiceReturnsDefaultPageTest.class.getClassLoader().getResource(JSON_FILENAME).toURI())));
        ObjectMapper mapper = new ObjectMapper();
        allBigMallsDto = mapper.readValue(JSON_TEST_FILE, BigMallsDto[].class);
    }

    @Test
    void getPageCircuitBreakerReturnsBigMallsDefaultPageTest() {
        when(config.getDs_bigmalls()).thenReturn(urlBigMalls);
        when(httpProxy.getRequestData(any(URL.class), any(Class.class))).thenReturn(Mono.just(allBigMallsDto));

        GenericResultDto<BigMallsDto> expectedGenericResultDto = new GenericResultDto<BigMallsDto>();
        expectedGenericResultDto.setInfo(0, 0, 0, new BigMallsDto[0]);

        GenericResultDto<BigMallsDto> actualGenericResultDto = bigMallsService.getPage(0, -1).block();

        assertEquals(expectedGenericResultDto.getOffset(), actualGenericResultDto.getOffset());
        assertEquals(expectedGenericResultDto.getLimit(), actualGenericResultDto.getLimit());
        assertEquals(expectedGenericResultDto.getCount(), actualGenericResultDto.getCount());
        assertArrayEquals(expectedGenericResultDto.getResults(), actualGenericResultDto.getResults());

        verify(httpProxy, times(1)).getRequestData(any(URL.class), any(Class.class));
    }

    @Test
    void getBigMallsAllActivitiesCircuitBreakerReturnsActivitiesDefaultPageTest() {
        when(config.getDs_bigmalls()).thenReturn(urlBigMalls);
        when(httpProxy.getRequestData(any(URL.class), any(Class.class))).thenReturn(Mono.just(allBigMallsDto));

        GenericResultDto<ActivityInfoDto> expectedGenericActivityResultDto = new GenericResultDto<ActivityInfoDto>();
        expectedGenericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);

        GenericResultDto<ActivityInfoDto> actualGenericActivityResultDto =
            bigMallsService.getBigMallsAllActivities(0, -1).block();

        assertEquals(expectedGenericActivityResultDto.getOffset(), actualGenericActivityResultDto.getOffset());
        assertEquals(expectedGenericActivityResultDto.getLimit(), actualGenericActivityResultDto.getLimit());
        assertEquals(expectedGenericActivityResultDto.getCount(), actualGenericActivityResultDto.getCount());
        assertArrayEquals(expectedGenericActivityResultDto.getResults(), actualGenericActivityResultDto.getResults());

        verify(httpProxy, times(1)).getRequestData(any(URL.class), any(Class.class));
    }
}