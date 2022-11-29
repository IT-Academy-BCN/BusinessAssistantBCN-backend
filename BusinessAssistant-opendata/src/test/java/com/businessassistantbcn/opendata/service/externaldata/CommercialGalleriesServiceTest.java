package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.SearchDTO;
import com.businessassistantbcn.opendata.dto.input.commercialgalleries.CommercialGalleriesDto;
import com.businessassistantbcn.opendata.dto.output.CommercialGalleriesResponseDto;
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
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:resilience4j-test.properties")
class CommercialGalleriesServiceTest {
	

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
	private static CommercialGalleriesResponseDto[] responseDto;
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
		
		responseDto = new CommercialGalleriesResponseDto[2];
        CommercialGalleriesResponseDto responseDto1 = new CommercialGalleriesResponseDto();
		List<AddressInfoDto> addressInfoDto1 = new ArrayList<>();
		AddressInfoDto addressInfoDto11 = new AddressInfoDto();
		addressInfoDto11.setStreet_name(twoCommercialGalleriesDto[0].getAddresses().get(0).getAddress_name());
		addressInfoDto11.setStreet_number(twoCommercialGalleriesDto[0].getAddresses().get(0).getStreet_number_1());
		addressInfoDto11.setZip_code(twoCommercialGalleriesDto[0].getAddresses().get(0).getZip_code());
		addressInfoDto11.setDistrict_id(twoCommercialGalleriesDto[0].getAddresses().get(0).getDistrict_id());
		addressInfoDto11.setTown(twoCommercialGalleriesDto[0].getAddresses().get(0).getTown());
		CoordinateInfoDto coords1 = new CoordinateInfoDto();
		coords1.setX(twoCommercialGalleriesDto[0].getCoordinates().getX());
		coords1.setY(twoCommercialGalleriesDto[0].getCoordinates().getY());
		addressInfoDto11.setLocation(coords1);
		addressInfoDto1.add(addressInfoDto11);
        responseDto1.setName(twoCommercialGalleriesDto[0].getName());
		responseDto1.setWeb(twoCommercialGalleriesDto[0].getValues().getUrl_value());
		responseDto1.setEmail(twoCommercialGalleriesDto[0].getValues().getEmail_value());
		responseDto1.setPhone(twoCommercialGalleriesDto[0].getValues().getPhone_value());
        responseDto1.setActivities(responseDto1.mapClassificationDataListToActivityInfoList(twoCommercialGalleriesDto[0].getClassifications_data()));
        responseDto1.setAddresses(addressInfoDto1);
        responseDto[0] = responseDto1;

        CommercialGalleriesResponseDto responseDto2 = new CommercialGalleriesResponseDto();
		List<AddressInfoDto> addressInfoDto2 = new ArrayList<>();
		AddressInfoDto addressInfoDto21 = new AddressInfoDto();
		addressInfoDto21.setStreet_name(twoCommercialGalleriesDto[1].getAddresses().get(0).getAddress_name());
		addressInfoDto21.setStreet_number(twoCommercialGalleriesDto[1].getAddresses().get(0).getStreet_number_1());
		addressInfoDto21.setZip_code(twoCommercialGalleriesDto[1].getAddresses().get(0).getZip_code());
		addressInfoDto21.setDistrict_id(twoCommercialGalleriesDto[1].getAddresses().get(0).getDistrict_id());
		addressInfoDto21.setTown(twoCommercialGalleriesDto[1].getAddresses().get(0).getTown());
		CoordinateInfoDto coords2 = new CoordinateInfoDto();
		coords2.setX(twoCommercialGalleriesDto[1].getCoordinates().getX());
		coords2.setY(twoCommercialGalleriesDto[1].getCoordinates().getY());
		addressInfoDto21.setLocation(coords2);
		addressInfoDto2.add(addressInfoDto21);
        responseDto2.setName(twoCommercialGalleriesDto[1].getName());
		responseDto2.setWeb(twoCommercialGalleriesDto[1].getValues().getUrl_value());
		responseDto2.setEmail(twoCommercialGalleriesDto[1].getValues().getEmail_value());
		responseDto2.setPhone(twoCommercialGalleriesDto[1].getValues().getPhone_value());
        responseDto2.setActivities(responseDto2.mapClassificationDataListToActivityInfoList(twoCommercialGalleriesDto[1].getClassifications_data()));
        responseDto2.setAddresses(addressInfoDto2);
        responseDto[1] =responseDto2;
	}

	@Test
	void getPageTest() throws JsonProcessingException {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class)))
			.thenReturn(Mono.just(twoCommercialGalleriesDto));

		GenericResultDto<CommercialGalleriesResponseDto> expectedResult = new GenericResultDto<CommercialGalleriesResponseDto>();
		expectedResult.setInfo(0, -1, twoCommercialGalleriesDto.length, responseDto);

		GenericResultDto<CommercialGalleriesResponseDto> actualResult =
			commercialGalleriesService.getPage(0, -1).block();
		this.areOffsetLimitAndCountEqual(expectedResult, actualResult);
		assertEquals(mapper.writeValueAsString(expectedResult.getResults()),
			mapper.writeValueAsString(actualResult.getResults()));

		verify(config, times(1)).getDs_commercialgalleries();
		verify(httpProxy, times(1)).getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getPageReturnsCommercialGalleriesDefaultPageWhenInternalErrorTest() {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class))).thenReturn(Mono.error(new RuntimeException()));
		this.returnsCommercialGalleriesDefaultPage(commercialGalleriesService.getPage(0, -1).block());
		verify(httpProxy, times(1))
			.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getPageReturnsCommercialGalleriesDefaultPageWhenServerIsDownTest() {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class)))
			.thenReturn(Mono.error(new Exception()));
		this.returnsCommercialGalleriesDefaultPage(commercialGalleriesService.getPage(0, -1).block());
		verify(httpProxy, times(1))
			.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getCommercialGalleriesActivitiesTest() throws JsonProcessingException {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class)))
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
			.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getCommercialGalleriesActivitiesReturnsActivitiesDefaultPageWhenInternalErrorTest() {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class))).thenReturn(Mono.error(new RuntimeException()));
		this.returnsActivitiesDefaultPage(commercialGalleriesService.getCommercialGalleriesActivities(0, -1).block());
		verify(httpProxy, times(1)).getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class));
	}

	@Test
	void getCommercialGalleriesActivitiesReturnsActivitiesDefaultPageWhenServerIsDownTest() {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class)))
			.thenReturn(Mono.error(new RuntimeException()));
		this.returnsActivitiesDefaultPage(commercialGalleriesService.getCommercialGalleriesActivities(0, -1).block());
		verify(httpProxy, times(1)).getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class));
	}

	private void areOffsetLimitAndCountEqual(GenericResultDto<?> expected, GenericResultDto<?> actual) {
		assertEquals(expected.getOffset(), actual.getOffset());
		assertEquals(expected.getLimit(), actual.getLimit());
		assertEquals(expected.getCount(), actual.getCount());
	}

	private void returnsCommercialGalleriesDefaultPage(GenericResultDto<CommercialGalleriesResponseDto> actualResult) {
		GenericResultDto<CommercialGalleriesResponseDto> expectedResult = new GenericResultDto<CommercialGalleriesResponseDto>();
		expectedResult.setInfo(0, 0, 0, new CommercialGalleriesResponseDto[0]);

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

	@Test
	void getPageByActivityTest() {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class)))
				.thenReturn(Mono.just(twoCommercialGalleriesDto));

		GenericResultDto<CommercialGalleriesResponseDto> actualResult =
				commercialGalleriesService.getPageByActivity(0, -1, "1008025").block();

		assertEquals(0, actualResult.getOffset());
		assertEquals(-1, actualResult.getLimit());
		assertEquals(1008025, Arrays.stream(actualResult.getResults()).toList()
				.get(0).getActivities().get(0).getActivityId());
	}

	@Test
	void getPageByDistrictTest() {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class)))
				.thenReturn(Mono.just(twoCommercialGalleriesDto));

		GenericResultDto<CommercialGalleriesResponseDto> actualResult =
				commercialGalleriesService.getPageByDistrict(0, -1, 2).block();

		assertEquals(0, actualResult.getOffset());
		assertEquals(-1, actualResult.getLimit());
		assertEquals("02", Arrays.stream(actualResult.getResults()).toList()
				.get(0).getAddresses().get(0).getDistrict_id());
	}

	@Test
	void getPageBySearchTest() {
		when(config.getDs_commercialgalleries()).thenReturn(urlCommercialGalleries);
		when(httpProxy.getRequestData(any(URI.class), eq(CommercialGalleriesDto[].class)))
				.thenReturn(Mono.just(twoCommercialGalleriesDto));

		SearchDTO searchParams = new SearchDTO(new int[]{2}, new int[]{});

		GenericResultDto<CommercialGalleriesResponseDto> actualResult =
				commercialGalleriesService.getPageBySearch(0, -1, searchParams).block();

		assertEquals(0, actualResult.getOffset());
		assertEquals(-1, actualResult.getLimit());
		assertEquals("02", Arrays.stream(actualResult.getResults()).toList()
				.get(0).getAddresses().get(0).getDistrict_id());
		assertEquals(1008025, Arrays.stream(actualResult.getResults()).toList()
				.get(0).getActivities().get(0).getActivityId());
	}
}
