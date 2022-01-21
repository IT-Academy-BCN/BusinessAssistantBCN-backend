package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import com.businessassistantbcn.opendata.helper.JsonHelper;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class LargeStablishmentsService {

	@Autowired
	HttpClientHelper httpClientHelper;

	@Autowired
	private PropertiesConfig config;

	@Autowired
	private GenericResultDto<LargeStablishmentsDto> genericResultDto;

	public Mono<GenericResultDto<LargeStablishmentsDto>> getPageByDistrict(int offset, int limit, String district) {

		try {
			Mono<LargeStablishmentsDto[]> response = httpClientHelper
					.getRequestData(new URL(config.getDs_largestablishments()), LargeStablishmentsDto[].class);
			return response.flatMap(dto -> {
				try {
					LargeStablishmentsDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
					genericResultDto.setLimit(limit);
					genericResultDto.setOffset(offset);
					genericResultDto.setResults(filteredDto);
					genericResultDto.setCount(dto.length);
					return Mono.just(genericResultDto);
				} catch (Exception e) {
					// Poner Logger
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
				}

			});

		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
	}

	public Mono<GenericResultDto<LargeStablishmentsDto>> getPageByActivity(int offset, int limit, String activity) {

		try {
			Mono<LargeStablishmentsDto[]> response = httpClientHelper
					.getRequestData(new URL(config.getDs_largestablishments()), LargeStablishmentsDto[].class);
			return response.flatMap(dto -> {
				try {
					LargeStablishmentsDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
					genericResultDto.setLimit(limit);
					genericResultDto.setOffset(offset);
					genericResultDto.setResults(filteredDto);
					genericResultDto.setCount(dto.length);
					return Mono.just(genericResultDto);
				} catch (Exception e) {
					// Poner Logger
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
				}

			});

		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}
	}

	public Mono<GenericResultDto<LargeStablishmentsDto>> getLargeStablishmentsAll()

	{

		try {

			Mono<LargeStablishmentsDto[]> response = httpClientHelper
					.getRequestData(new URL(config.getDs_largestablishments()), LargeStablishmentsDto[].class);

			return response.flatMap(dto -> {
				genericResultDto.setResults(dto);
				genericResultDto.setCount(dto.length);
				return Mono.just(genericResultDto);

			});

		} catch (MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
		}

	}
}
