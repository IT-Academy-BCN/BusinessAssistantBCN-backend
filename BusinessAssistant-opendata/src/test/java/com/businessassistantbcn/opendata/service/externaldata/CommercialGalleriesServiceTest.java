package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.commercialgalleries.CommercialGalleriesDto;
import com.businessassistantbcn.opendata.dto.output.CommercialGalleriesResponseDto;
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
import static org.mockito.Mockito.*;

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
	private static CommercialGalleriesDto[] twoCommercialGalleriesDto;
	private static ActivityInfoDto[] activities;
	 
	@BeforeAll
	static void beforeAll() throws URISyntaxException, IOException {
		urlCommercialGalleries = "http://www.bcn.cat/tercerlloc/files/" +
			"mercats-centrescomercials/opendatabcn_mercats-centrescomercials_grans-centres-comercials-js.json";
		String commercialGalleriesAsString = Files.readAllLines(Paths.get(CommercialGalleriesServiceTest.class
			.getClassLoader().getResource(JSON_FILENAME_COMERCIAL_GALLERIES).toURI()), StandardCharsets.UTF_8).get(0);
		String commercialGalleriesActivitiesAsString = Files
			.readAllLines(
				Paths.get(CommercialGalleriesServiceTest.class.getClassLoader()
					.getResource(JSON_FILENAME_COMERCIAL_GALLERIES_ACTIVITIES).toURI()),
				StandardCharsets.UTF_8)
			.get(0);
		mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		twoCommercialGalleriesDto = mapper.readValue(commercialGalleriesAsString, CommercialGalleriesDto[].class);
		activities = mapper.readValue(commercialGalleriesActivitiesAsString, ActivityInfoDto[].class);
	}

	@Test
	void getPageTest() throws MalformedURLException, JsonProcessingException {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class)))
			.thenReturn(Mono.just(twoCommercialGalleriesDto));

		GenericResultDto<CommercialGalleriesDto> expectedResult = new GenericResultDto<CommercialGalleriesDto>();
		expectedResult.setInfo(0, -1, twoCommercialGalleriesDto.length, twoCommercialGalleriesDto);

		GenericResultDto<CommercialGalleriesResponseDto> actualResult =
			commercialGalleriesService.getPage(0, -1).block();
		this.areOffsetLimitAndCountEqual(expectedResult, actualResult);
		assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
			mapper.writeValueAsString(actualResult.getResults()));

		verify(config, times(1)).getDs_commercialgalleries();
		verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getPageReturnsCommercialGalleriesDefaultPageWhenInternalErrorTest() throws MalformedURLException {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class)))
			.thenThrow(RuntimeException.class);
		this.returnsCommercialGalleriesDefaultPage(commercialGalleriesService.getPage(0, -1).block());
		verify(httpProxy, times(1))
			.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getPageReturnsCommercialGalleriesDefaultPageWhenServerIsDownTest() throws MalformedURLException {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class)))
			.thenReturn(Mono.error(new RuntimeException()));
		this.returnsCommercialGalleriesDefaultPage(commercialGalleriesService.getPage(0, -1).block());
		verify(httpProxy, times(1))
			.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getCommercialGalleriesActivitiesTest() throws MalformedURLException, JsonProcessingException {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class)))
			.thenReturn(Mono.just(twoCommercialGalleriesDto));

		GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<ActivityInfoDto>();
		expectedResult.setInfo(0, -1, activities.length, activities);

		GenericResultDto<ActivityInfoDto> actualResult =
			commercialGalleriesService.getCommercialGalleriesActivities(0, -1).block();
		areOffsetLimitAndCountEqual(expectedResult, actualResult);
		assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
			mapper.writeValueAsString(actualResult.getResults()));

		verify(config, times(1)).getDs_commercialgalleries();
		verify(httpProxy, times(1))
			.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getCommercialGalleriesActivitiesReturnsActivitiesDefaultPageWhenInternalErrorTest() throws MalformedURLException {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class))).thenThrow(RuntimeException.class);
		this.returnsActivitiesDefaultPage(commercialGalleriesService.getCommercialGalleriesActivities(0, -1).block());
		verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getCommercialGalleriesActivitiesReturnsActivitiesDefaultPageWhenServerIsDownTest() throws MalformedURLException {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class)))
			.thenReturn(Mono.error(new RuntimeException()));
		this.returnsActivitiesDefaultPage(commercialGalleriesService.getCommercialGalleriesActivities(0, -1).block());
		verify(httpProxy, times(1)).getRequestData(any(URL.class), eq(CommercialGalleriesDto[].class));
	}

	private void areOffsetLimitAndCountEqual(GenericResultDto<?> expected, GenericResultDto<?> actual) {
		assertEquals(expected.getOffset(), actual.getOffset());
		assertEquals(expected.getLimit(), actual.getLimit());
		assertEquals(expected.getCount(), actual.getCount());
	}

	private void returnsCommercialGalleriesDefaultPage(GenericResultDto<CommercialGalleriesResponseDto> actualResult) {
		GenericResultDto<CommercialGalleriesDto> expectedResult = new GenericResultDto<CommercialGalleriesDto>();
		expectedResult.setInfo(0, 0, 0, new CommercialGalleriesDto[0]);

		this.areOffsetLimitAndCountEqual(expectedResult, actualResult);
		assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

		verify(config, times(1)).getDs_commercialgalleries();
	}

	private void returnsActivitiesDefaultPage(GenericResultDto<ActivityInfoDto> actualResult) {
		GenericResultDto<ActivityInfoDto> expectedResult = new GenericResultDto<ActivityInfoDto>();
		expectedResult.setInfo(0, 0, 0, new ActivityInfoDto[0]);

		this.areOffsetLimitAndCountEqual(expectedResult, actualResult);
		assertArrayEquals(expectedResult.getResults(), actualResult.getResults());

		verify(config, times(1)).getDs_commercialgalleries();
	}

}
