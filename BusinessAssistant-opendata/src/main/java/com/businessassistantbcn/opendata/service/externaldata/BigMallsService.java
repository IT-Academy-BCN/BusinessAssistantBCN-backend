package com.businessassistantbcn.opendata.service.externaldata;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
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

		URL url;

		try {
			url = new URL(config.getDs_bigmalls());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			return getPageDefault();
		}

		Mono<BigMallsDto[]> response = httpProxy.getRequestData(url, BigMallsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

		return circuitBreaker.run(() -> response.flatMap(dto -> {
			BigMallsDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(filteredDto);
			genericResultDto.setCount(dto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());
	}


	private Mono<GenericResultDto<BigMallsDto>> getPageDefault(){
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new BigMallsDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}

	public Mono<GenericResultDto<ActivityInfoDto>> bigMallsAllActivities(int offset, int limit) {
		URL url;
		try {
			url = new URL(config.getDs_bigmalls());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: " + e.getMessage());
			genericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);
			return Mono.just(genericActivityResultDto);
		}

		Mono<BigMallsDto[]> response = httpProxy.getRequestData(url, BigMallsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

		return circuitBreaker.run( () -> response.flatMap(bigMallsDto -> {
			List<ActivityInfoDto> listactivityInfoDto = new ArrayList<>();
			listactivityInfoDto = io.vavr.collection.List.ofAll(
				Arrays.stream(bigMallsDto)
					.flatMap(bigMallDto -> bigMallDto.getClassifications_data().stream())
					.filter(classificationsDataDto ->
						(classificationsDataDto.getFullPath() == null) ||
							(
								(!classificationsDataDto.getFullPath().toUpperCase().contains("MARQUES")) &&
								(!classificationsDataDto.getFullPath().toUpperCase().contains("GESTIÓ BI")) &&
								(!classificationsDataDto.getFullPath().toUpperCase().contains("ÚS INTERN"))
							)
					)
					.map(classificationsDataDto -> {
						return new ActivityInfoDto(
								classificationsDataDto.getId(),
								((classificationsDataDto.getName() == null) ? "" : classificationsDataDto.getName())
						);
					})
					.sorted(Comparator.comparing(ActivityInfoDto::getActivityName))
					.collect(Collectors.toList()))
			.distinctBy((s1, s2) -> s1.getActivityName().compareToIgnoreCase(s2.getActivityName()))
			.toJavaList();

			ActivityInfoDto[] activityInfoDto =
				listactivityInfoDto.toArray(new ActivityInfoDto[listactivityInfoDto.size()]);

			ActivityInfoDto[] pagedDto = JsonHelper.filterDto(activityInfoDto, offset, limit);
			genericActivityResultDto.setInfo(offset, limit, activityInfoDto.length, pagedDto);
			return Mono.just(genericActivityResultDto);

		}), throwable -> {
			genericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);
			return Mono.just(genericActivityResultDto);
		});
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

