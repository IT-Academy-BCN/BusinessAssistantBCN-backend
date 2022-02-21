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
public class BigMallsServiceTest {

    @MockBean
    private PropertiesConfig config;

    @MockBean
    private HttpProxy httpProxy;

    @Autowired
    @InjectMocks
    private BigMallsService bigMallsService;

    private static String urlBigMalls;
    private static final String JSON_FILENAME_BIG_MALLS = "json/allBigMallsForTesting.json";
    private static String JSON_TEST_FILE_BIG_MALLS;
    private static ObjectMapper mapper;
    private static BigMallsDto[] allBigMallsDto;

    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {
        urlBigMalls = "http://www.bcn.cat/tercerlloc/files/" +
            "mercats-centrescomercials/opendatabcn_mercats-centrescomercials_grans-centres-comercials-js.json";

        JSON_TEST_FILE_BIG_MALLS = new String(Files.readAllBytes(
            Paths.get(BigMallsServiceTest.class.getClassLoader().getResource(JSON_FILENAME_BIG_MALLS).toURI())));

        ObjectMapper mapper = new ObjectMapper();
        allBigMallsDto = mapper.readValue(JSON_TEST_FILE_BIG_MALLS, BigMallsDto[].class);
    }

    @Test
    void getPagetest() {
        when(config.getDs_bigmalls()).thenReturn(urlBigMalls);
        when(httpProxy.getRequestData(any(URL.class), any(Class.class))).thenReturn(Mono.just(allBigMallsDto));

        GenericResultDto<BigMallsDto> actualResult = bigMallsService.getPage(0, -1).block();

//        assertEquals(allBigMallsDto.length, actualResult.getCount());
//        assertArrayEquals(allBigMallsDto, actualResult.getResults());

        verify(httpProxy, times(1)).getRequestData(any(URL.class), any(Class.class));
    }

    @Test
    void getPageThrowsMalformedURLExceptionTest() {
        when(config.getDs_bigmalls()).thenReturn("gibberish");
        bigMallsService.getPage(0, -1);
        verify(httpProxy, times(0)).getRequestData(any(URL.class), any(Class.class));
    }

    @Test
    void getBigMallsAllActivitiesThrowsMalformedURLExceptionTest() {
        when(config.getDs_bigmalls()).thenReturn("gibberish");
        bigMallsService.getBigMallsAllActivities(0, -1);
        verify(httpProxy, times(0)).getRequestData(any(URL.class), any(Class.class));
    }

}