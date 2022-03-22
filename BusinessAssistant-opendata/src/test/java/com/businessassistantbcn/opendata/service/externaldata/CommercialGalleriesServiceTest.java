package com.businessassistantbcn.opendata.service.externaldata;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.CommercialGalleriesDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommercialGalleriesServiceTest {
	

	@MockBean
	private PropertiesConfig config;

	@MockBean
	private HttpProxy httpProxy;

	@Autowired
	@InjectMocks
	private CommercialGalleriesService commercialGalleriesService;

	private static String urlCommercialGalleries;

	private static final String JSON_FILENAME_COMERCIAL_GALLERIES = "json/twoComercialGalleriesForTesting.json";

	private static final String JSON_FILENAME_COMERCIAL_GALLERIES_ACTIVITIES = "json/activitiesFromTwoComercialGalleriesForTesting.json";

	private static ObjectMapper mapper;
	 // ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	private static CommercialGalleriesDto[] twocommercialGalleriesDto;

	private static ActivityInfoDto[] activities;

	 
	@BeforeAll
	static void beforeAll() throws URISyntaxException, IOException {
		urlCommercialGalleries = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/opendatabcn_mercats-centrescomercials_grans-centres-comercials-js.json";
		String commercialGalleriesAsString = Files.readAllLines(Paths.get(CommercialGalleriesServiceTest.class
				.getClassLoader().getResource(JSON_FILENAME_COMERCIAL_GALLERIES).toURI()), StandardCharsets.UTF_8)
				.get(0);

		String commercialGalleriesActivitiesAsString = Files
				.readAllLines(
						Paths.get(CommercialGalleriesServiceTest.class.getClassLoader()
								.getResource(JSON_FILENAME_COMERCIAL_GALLERIES_ACTIVITIES).toURI()),
						StandardCharsets.UTF_8)
				.get(0);
		mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		twocommercialGalleriesDto = mapper.readValue(commercialGalleriesAsString, CommercialGalleriesDto[].class);
		activities = mapper.readValue(commercialGalleriesActivitiesAsString, ActivityInfoDto[].class);

	}

	@Test
	void getPageTest() throws MalformedURLException, JsonProcessingException {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class)))
				.thenReturn(Mono.just(twocommercialGalleriesDto));

		GenericResultDto<CommercialGalleriesDto> expectedResult = new GenericResultDto<CommercialGalleriesDto>();
		expectedResult.setInfo(0, -1, twocommercialGalleriesDto.length, twocommercialGalleriesDto);

		GenericResultDto<CommercialGalleriesDto> actualResult = commercialGalleriesService.getPage(0, -1).block();
		areOffsetLimitAndCountEqual(expectedResult, actualResult);
		assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
				mapper.writeValueAsString(actualResult.getResults()));

		verify(config, times(1)).getDs_commercialgalleries();
		verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
	}
	
    @Test
    void getPageReturnsCommercialGalleriesDefaultPageWhenMalformedURLTest() throws MalformedURLException {
    	when(config.getDs_commercialgalleries()).thenReturn("gibberish");
    	GenericResultDto<CommercialGalleriesDto> expectedResult = new GenericResultDto<CommercialGalleriesDto>();
    	expectedResult.setInfo(0, 0, 0, new CommercialGalleriesDto[0]);
    	
    	GenericResultDto<CommercialGalleriesDto> actualResult = commercialGalleriesService.getPage(0, -1).block();
    	 areOffsetLimitAndCountEqual(expectedResult, actualResult);
         assertArrayEquals(expectedResult.getResults(), actualResult.getResults());
         
         verify(config, times(1)).getDs_commercialgalleries();
         verify(httpProxy, times(0)).getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
    }
    @Test
    void getPageReturnsCommercialGalleriesPageTest() throws MalformedURLException {
    	when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
    	when(httpProxy.getRequestData(any(URL.class), any(Class.class))).thenThrow(RuntimeException.class);
    	
    	GenericResultDto<CommercialGalleriesDto> expectedResult = new GenericResultDto<CommercialGalleriesDto>();
    	expectedResult.setInfo(0, 0, 0, new CommercialGalleriesDto[0]);
    	
    	GenericResultDto<CommercialGalleriesDto> actualResult = commercialGalleriesService.getPage(0, -1).block();
    	 areOffsetLimitAndCountEqual(expectedResult, actualResult);
         assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

         verify(config, times(1)).getDs_commercialgalleries();
         verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
    }

	@Test
	void getCommercialGalleriesActivitiesTest() throws MalformedURLException, JsonProcessingException {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class)))
				.thenReturn(Mono.just(twocommercialGalleriesDto));

		GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<ActivityInfoDto>();
		expectedResult.setInfo(0, -1, activities.length, activities);

		verify(config, times(1)).getDs_commercialgalleries();
		verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getCommercialGalleriesActivitiesReturnsCommercialGalleriesDefaultPageWhenMalformedURLTest()
			throws MalformedURLException {
		when(config.getDs_commercialgalleries()).thenReturn("gibberish");
		GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<ActivityInfoDto>();
		expectedResult.setInfo(0, 0, 0, new ActivityInfoDto[0]);

		GenericResultDto<ActivityInfoDto> actualResult = commercialGalleriesService.commercialGalleriesActivities(0, -1)
				.block();
		areOffsetLimitAndCountEqual(expectedResult, actualResult);
		assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

		verify(config, times(1)).getDs_commercialgalleries();
		verify(httpProxy, times(0)).getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getCommercialGalleriesActivitiesReturnsActivitiesDefaultPageTest() throws MalformedURLException {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class)))
				.thenThrow(RuntimeException.class);

		GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<ActivityInfoDto>();
		expectedResult.setInfo(0, 0, 0, new ActivityInfoDto[0]);
		GenericResultDto<ActivityInfoDto> actualResult = commercialGalleriesService.commercialGalleriesActivities(0, -1)
				.block();
		areOffsetLimitAndCountEqual(expectedResult, actualResult);
		assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

		verify(config, times(1)).getDs_commercialgalleries();
		verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
	}

	private void areOffsetLimitAndCountEqual(GenericResultDto<?> expected, GenericResultDto<?> actual) {
		assertEquals(expected.getOffset(), actual.getOffset());
		assertEquals(expected.getLimit(), actual.getLimit());
		assertEquals(expected.getCount(), actual.getCount());
	}
}
