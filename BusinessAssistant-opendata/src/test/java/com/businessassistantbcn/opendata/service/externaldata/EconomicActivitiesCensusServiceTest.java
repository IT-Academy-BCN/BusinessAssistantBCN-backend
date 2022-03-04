package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.economicactivitiescensus.EconomicActivitiesCensusDto;
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
public class EconomicActivitiesCensusServiceTest {

    @MockBean
    private PropertiesConfig config;

    @MockBean
    private HttpProxy httpProxy;

    @Autowired
    @InjectMocks
    private EconomicActivitiesCensusService economicActivitiesCensusService;

    private static String urlEconomicActivitiesCensus;
    private static final String JSON_FILENAME_ECONOMIC_CENSUS = "json/twoEconomicActivitiesCensusForTesting.json";
    private static ObjectMapper mapper;
    private static EconomicActivitiesCensusDto[] twoEconomicActivitiesCensus;

    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {
        urlEconomicActivitiesCensus = "https://opendata-ajuntament.barcelona.cat/data/dataset/" +
            "671c46e9-5b85-4e63-8c97-088a2b907cd5/resource/7a3d5380-f79a-424e-be62-dd078efcb40a/" +
            "download/2019_censcomercialbcn_class_act.json";

        String economicActivitiesCensusAsString = Files.readAllLines(
            Paths.get(EconomicActivitiesCensusService.class.getClassLoader()
                .getResource(JSON_FILENAME_ECONOMIC_CENSUS).toURI()),
            StandardCharsets.UTF_8
        ).get(0);

        mapper = new ObjectMapper();
        twoEconomicActivitiesCensus =
            mapper.readValue(economicActivitiesCensusAsString, EconomicActivitiesCensusDto[].class);
    }

    @Test
    void getPageTest() throws MalformedURLException, JsonProcessingException {
        when(config.getDs_economicactivitiescensus()).thenReturn(urlEconomicActivitiesCensus);
        when(httpProxy.getRequestData(any(URL.class), eq(EconomicActivitiesCensusDto[].class)))
            .thenReturn(Mono.just(twoEconomicActivitiesCensus));

        GenericResultDto<EconomicActivitiesCensusDto> expectedResult =
            new GenericResultDto<EconomicActivitiesCensusDto>();
        expectedResult.setInfo(0, -1, twoEconomicActivitiesCensus.length, twoEconomicActivitiesCensus);

        GenericResultDto<EconomicActivitiesCensusDto> actualResult =
            economicActivitiesCensusService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
                mapper.writeValueAsString(actualResult.getResults()));

        verify(config, times(1)).getDs_economicactivitiescensus();
        verify(httpProxy, times(1))
            .getRequestData(any(URL.class), eq(EconomicActivitiesCensusDto[].class));
    }

    @Test
    void getPageReturnsEconomicActivitiesCensusDefaultPageWhenMalformedURLTest() throws MalformedURLException {
        when(config.getDs_economicactivitiescensus()).thenReturn("gibberish");
        GenericResultDto<EconomicActivitiesCensusDto> expectedResult =
            new GenericResultDto<EconomicActivitiesCensusDto>();
        expectedResult.setInfo(0, 0, 0, new EconomicActivitiesCensusDto[0]);

        GenericResultDto<EconomicActivitiesCensusDto> actualResult =
            economicActivitiesCensusService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(config, times(1)).getDs_economicactivitiescensus();
        verify(httpProxy, times(0))
            .getRequestData(any(URL.class), eq(EconomicActivitiesCensusDto[].class));
    }

    @Test
    void getPageReturnsEconomicActivitiesCensusDefaultPageTest() throws MalformedURLException {
        when(config.getDs_economicactivitiescensus()).thenReturn(urlEconomicActivitiesCensus);
        when(httpProxy.getRequestData(any(URL.class), eq(EconomicActivitiesCensusDto[].class)))
            .thenThrow(RuntimeException.class);

        GenericResultDto<EconomicActivitiesCensusDto> expectedResult =
            new GenericResultDto<EconomicActivitiesCensusDto>();
        expectedResult.setInfo(0, 0, 0, new EconomicActivitiesCensusDto[0]);

        GenericResultDto<EconomicActivitiesCensusDto> actualResult =
            economicActivitiesCensusService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(config, times(1)).getDs_economicactivitiescensus();
        verify(httpProxy, times(1))
            .getRequestData(any(URL.class), eq(EconomicActivitiesCensusDto[].class));
    }

    private void areOffsetLimitAndCountEqual(GenericResultDto<?> expected, GenericResultDto<?> actual) {
        assertEquals(expected.getOffset(), actual.getOffset());
        assertEquals(expected.getLimit(), actual.getLimit());
        assertEquals(expected.getCount(), actual.getCount());
    }

}
