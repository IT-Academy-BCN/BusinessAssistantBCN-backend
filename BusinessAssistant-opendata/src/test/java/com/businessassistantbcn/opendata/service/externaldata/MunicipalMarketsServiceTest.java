package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.municipalmarkets.MunicipalMarketsDto;
import com.businessassistantbcn.opendata.dto.output.MunicipalMarketsResponseDto;
import com.businessassistantbcn.opendata.dto.output.data.AddressInfoDto;
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
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@PropertySource("classpath:resilience4j-test.properties")
class MunicipalMarketsServiceTest {

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
    private static MunicipalMarketsResponseDto[] responseDto;

    @BeforeAll
    static void beforeAll() throws URISyntaxException, IOException {
        urlMunicipalMarkets = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/" +
            "opendatabcn_mercats-centrescomercials_mercats-municipals-js.json";

        String municipalMarketsAsString = Files.readAllLines(
            Paths.get(MunicipalMarketsService.class.getClassLoader()
                    .getResource(JSON_FILENAME_ECONOMIC_MUNICIPAL_MARKETS).toURI()),
            StandardCharsets.UTF_8
        ).get(0);

        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        twoMunicipalMarkets =
                mapper.readValue(municipalMarketsAsString, MunicipalMarketsDto[].class);
        
        responseDto = new MunicipalMarketsResponseDto[2];
        MunicipalMarketsResponseDto responseDto1 = new MunicipalMarketsResponseDto();
        List<AddressInfoDto> addressInfoDto1 = new ArrayList<>();
        AddressInfoDto addressInfoDto11 = new AddressInfoDto();
        addressInfoDto11.setStreet_name(twoMunicipalMarkets[0].getAddresses().get(0).getAddress_name());
        addressInfoDto1.add(addressInfoDto11);
        responseDto1.setName(twoMunicipalMarkets[0].getName());
        responseDto1.setWeb(twoMunicipalMarkets[0].getWeb());
        responseDto1.setEmail(twoMunicipalMarkets[0].getEmail());
        responseDto1.setAddresses(addressInfoDto1);
        responseDto[0] = responseDto1;

        MunicipalMarketsResponseDto responseDto2 = new MunicipalMarketsResponseDto();
        List<AddressInfoDto> addressInfoDto2 = new ArrayList<>();
        AddressInfoDto addressInfoDto21 = new AddressInfoDto();
        addressInfoDto21.setStreet_name(twoMunicipalMarkets[1].getAddresses().get(0).getAddress_name());
        addressInfoDto2.add(addressInfoDto21);
        responseDto2.setName(twoMunicipalMarkets[1].getName());
        responseDto2.setWeb(twoMunicipalMarkets[1].getWeb());
        responseDto2.setEmail(twoMunicipalMarkets[1].getEmail());
        responseDto2.setAddresses(addressInfoDto2);
        responseDto[1] =responseDto2;
    }

    @Test
    void getPageTest() throws MalformedURLException {
        when(config.getDs_municipalmarkets()).thenReturn(urlMunicipalMarkets);
        when(httpProxy.getRequestData(any(URL.class), eq(MunicipalMarketsDto[].class)))
            .thenReturn(Mono.just(twoMunicipalMarkets));

        GenericResultDto<MunicipalMarketsResponseDto> expectedResult = new GenericResultDto<MunicipalMarketsResponseDto>();
        expectedResult.setInfo(0, -1, twoMunicipalMarkets.length, responseDto);

        GenericResultDto<MunicipalMarketsResponseDto> actualResult = municipalMarketsService.getPage(0, -1).block();
        areOffsetLimitAndCountEqual(expectedResult, actualResult);

        assertEquals(expectedResult.getResults().length, actualResult.getResults().length);


        verify(config, times(1)).getDs_municipalmarkets();
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(MunicipalMarketsDto[].class));
    }

    @Test
    void getPageReturnsMunicipalMarketsDefaultPageWhenInternalErrorTest() throws MalformedURLException {
        when(config.getDs_municipalmarkets()).thenReturn(urlMunicipalMarkets);
        when(httpProxy.getRequestData(any(URL.class), eq(MunicipalMarketsDto[].class))).thenReturn(Mono.error(new RuntimeException()));
        this.returnsMunicipalMarketDefaultPage(municipalMarketsService.getPage(0, -1).block());
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(MunicipalMarketsDto[].class));
    }

    @Test
    void getPageReturnsMunicipalMarketsDefaultPageWhenServerIsDownTest() throws MalformedURLException {
        when(config.getDs_municipalmarkets()).thenReturn(urlMunicipalMarkets);
        when(httpProxy.getRequestData(any(URL.class), eq(MunicipalMarketsDto[].class)))
                .thenReturn(Mono.error(new RuntimeException()));
        this.returnsMunicipalMarketDefaultPage(municipalMarketsService.getPage(0, -1).block());
        verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(MunicipalMarketsDto[].class));
    }

    private void areOffsetLimitAndCountEqual(GenericResultDto<?> expected, GenericResultDto<?> actual) {
        assertEquals(expected.getOffset(), actual.getOffset());
        assertEquals(expected.getLimit(), actual.getLimit());
        assertEquals(expected.getCount(), actual.getCount());
    }

    private void returnsMunicipalMarketDefaultPage(GenericResultDto<MunicipalMarketsResponseDto> actualResult) {
        GenericResultDto<MunicipalMarketsResponseDto> expectedResult = new GenericResultDto<MunicipalMarketsResponseDto>();
        expectedResult.setInfo(0, 0, 0, new MunicipalMarketsResponseDto[0]);

        areOffsetLimitAndCountEqual(expectedResult, actualResult);
        assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

        verify(config, times(1)).getDs_municipalmarkets();
    }

}
