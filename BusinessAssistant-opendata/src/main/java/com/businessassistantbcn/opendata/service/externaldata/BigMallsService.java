package com.businessassistantbcn.opendata.service.externaldata;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.LargeEstablishmentsDto;
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
	private CircuitBreakerFactory circuitBreakerFactory;

	public Mono<GenericResultDto<BigMallsDto>>getPage(int offset, int limit) {

		URL url;

		try {
			url = new URL(config.getDs_bigmalls());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			return getBigMallsPageDefault();
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
		}), throwable -> getBigMallsPageDefault());
	}

	private Mono<GenericResultDto<BigMallsDto>> getBigMallsPageDefault(){
		GenericResultDto<BigMallsDto> genericBigMallResultDto = new GenericResultDto<BigMallsDto>();
		genericBigMallResultDto.setLimit(0);
		genericBigMallResultDto.setOffset(0);
		genericBigMallResultDto.setResults(new BigMallsDto[0]);
		genericBigMallResultDto.setCount(0);
		return Mono.just(genericBigMallResultDto);
	}

	public Mono<GenericResultDto<ActivityInfoDto>> bigMallsAllActivities(int offset, int limit) {
		URL url;
		try {
			url = new URL(config.getDs_bigmalls());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: " + e.getMessage());
			return Mono.just(new GenericResultDto<ActivityInfoDto>(0, 0, 0, new ActivityInfoDto[0]));
		}

		Mono<BigMallsDto[]> response = httpProxy.getRequestData(url, BigMallsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

		return circuitBreaker.run( () -> response.flatMap(bigMallsDto -> {
			ActivityInfoDto[] arrActivityInfoDto =
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
							((classificationsDataDto.getName()!=null) ? classificationsDataDto.getName() : "")
						);
					})
					.sorted(Comparator.comparing(ActivityInfoDto::getActivityName))
					.distinct()
					.toArray(ActivityInfoDto[]::new);

			ActivityInfoDto[] pagedDto = JsonHelper.filterDto(arrActivityInfoDto, offset, limit);
			return Mono.just(new GenericResultDto<ActivityInfoDto>(pagedDto.length, offset, limit, pagedDto));

		}), throwable -> Mono.just(new GenericResultDto<ActivityInfoDto>(0, 0, 0, new ActivityInfoDto[0])));
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

