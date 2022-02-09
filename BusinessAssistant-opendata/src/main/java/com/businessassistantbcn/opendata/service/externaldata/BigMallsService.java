package com.businessassistantbcn.opendata.service.externaldata;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.bigmalls.ClassificationDataDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;

import com.businessassistantbcn.opendata.helper.JsonHelper;


import reactor.core.publisher.Mono;

@Service
public class BigMallsService {

	private static final Logger log = LoggerFactory.getLogger(BigMallsService.class);

	@Autowired
	private PropertiesConfig config;
	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private GenericResultDto<BigMallsDto> genericResultDto;
	@Autowired
	private GenericResultDto<ActivityInfoDto> genericActivityResultDto;
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;

	public Mono<GenericResultDto<BigMallsDto>>getPage(int offset, int limit) {
		URL url = this.getUrl();
		if (url == null) {
			return getBigMallsDefaultPage();
		}
		Mono<BigMallsDto[]> response = httpProxy.getRequestData(url, BigMallsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

		return circuitBreaker.run(() -> response.flatMap(bigMallsDto -> {
			BigMallsDto[] pagedDto = JsonHelper.filterDto(bigMallsDto, offset, limit);
			genericResultDto.setInfo(offset, limit, bigMallsDto.length, pagedDto);
			return Mono.just(genericResultDto);

		}), throwable -> getBigMallsDefaultPage()
		);
	}

	private Mono<GenericResultDto<BigMallsDto>> getBigMallsDefaultPage() {
		genericResultDto.setInfo(0, 0, 0, new BigMallsDto[0]);
		return Mono.just(genericResultDto);
	}

	public Mono<GenericResultDto<ActivityInfoDto>> bigMallsAllActivities(int offset, int limit) {
		URL url = this.getUrl();
		if (url == null) {
			return getActivitiesDefaultPage();
		}
		Mono<BigMallsDto[]> response = httpProxy.getRequestData(url, BigMallsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

		return circuitBreaker.run( () -> response.flatMap(bigMallsDto -> {

			List<ActivityInfoDto> listFullPathFiltered = this.getListWithoutInvalidFullPaths(bigMallsDto);
			List<ActivityInfoDto> listActivityInfoDto = this.getListWithoutRepeatedNames(listFullPathFiltered);
			ActivityInfoDto[] activityInfoDto =
				listActivityInfoDto.toArray(new ActivityInfoDto[listActivityInfoDto.size()]);

			ActivityInfoDto[] pagedDto = JsonHelper.filterDto(activityInfoDto, offset, limit);
			genericActivityResultDto.setInfo(offset, limit, activityInfoDto.length, pagedDto);
			return Mono.just(genericActivityResultDto);

		}), throwable -> getActivitiesDefaultPage()
		);
	}

	private Mono<GenericResultDto<ActivityInfoDto>> getActivitiesDefaultPage() {
		genericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);
		return Mono.just(genericActivityResultDto);
	}

	private URL getUrl() {
		URL url;
		try {
			url = new URL(config.getDs_bigmalls());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			url = null;
		}
		return url;
	}

	private List<ActivityInfoDto> getListWithoutInvalidFullPaths(BigMallsDto[] bigMallsDto) {
		return Arrays.stream(bigMallsDto)
			.flatMap(bigMallDto -> bigMallDto.getClassifications_data().stream())
			.filter(classificationsDataDto -> this.isFullPathValid(classificationsDataDto))
			.map(classificationsDataDto -> new ActivityInfoDto(
				classificationsDataDto.getId(),
				this.getValidActivityName(classificationsDataDto))
			).sorted(Comparator.comparing(ActivityInfoDto::getActivityName))
			.collect(Collectors.toList());
	}

	private List<ActivityInfoDto> getListWithoutRepeatedNames(List<ActivityInfoDto> listNamesUnfilterd) {
		return io.vavr.collection.List.ofAll(listNamesUnfilterd)
			.distinctBy((s1, s2) -> s1.getActivityName().compareToIgnoreCase(s2.getActivityName()))
			.toJavaList();
	}

	private boolean isFullPathValid(ClassificationDataDto dto) {
		if (dto.getFullPath() == null ||
			dto.getFullPath().toUpperCase().contains("MARQUES") ||
			dto.getFullPath().toUpperCase().contains("GESTIÓ BI") ||
			dto.getFullPath().toUpperCase().contains("ÚS INTERN")
		) {
			return false;
		}
		return true;
	}

	private String getValidActivityName(ClassificationDataDto dto) {
		//If name == null, sort method fails
		return dto.getName() == null ? "" : dto.getName();
	}

	public GenericResultDto<BigMallsDto> getBigMallsByActivityDto(int[] activities, int offset, int limit) {
		//lambda filter
		return null;
	}

	public GenericResultDto<BigMallsDto> getBigMallsByDistrictDto(int[] districts, int offset, int limit) {
		//lambda filter
		return null;
	}

	public String getBigMallsByActivity(int[] activities, int offset, int limit) {
		//JsonPath search
		/* OJO a formato de salida:
		{
		"count": 1217,
		"elements": [
		{
		"id": 3716,
		"name": "Paola",
		"surnames": "dos Reis Figueira",
		...
		*/
		return null;
	}

	public String getBigMallsByDistrict(int[] districts, int offset, int limit) {
		//JsonPath search
		/* OJO a formato de salida:
		{
		"count": 1217,
		"elements": [
		{
		"id": 3716,
		"name": "Paola",
		"surnames": "dos Reis Figueira",
		...
		*/

		return null;
	}

}

