package com.businessassistantbcn.opendata.service.externaldata;

import java.net.MalformedURLException;
import java.net.URL;

import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.CommercialGalleriesDto;
import com.businessassistantbcn.opendata.dto.marketfairs.MarketFairsDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;

import com.businessassistantbcn.opendata.helper.JsonHelper;

import reactor.core.publisher.Mono;

@Service
public class CommercialGalleriesService {

	private static final Logger log = LoggerFactory.getLogger(CommercialGalleriesService.class);

	@Autowired
	HttpProxy httpProxy;

	@Autowired
	private PropertiesConfig config;

	@Autowired
	private GenericResultDto<CommercialGalleriesDto> genericResultDto;

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	public Mono<GenericResultDto<CommercialGalleriesDto>> getPage(int offset, int limit) throws MalformedURLException {

		return httpProxy.getRequestData(new URL(config.getDs_commercialgalleries()), CommercialGalleriesDto[].class)
				.flatMap(dto -> {
					CommercialGalleriesDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
					genericResultDto.setLimit(limit);
					genericResultDto.setOffset(offset);
					genericResultDto.setResults(filteredDto);
					genericResultDto.setCount(dto.length);
					return Mono.just(genericResultDto);
				});
	}

	public Mono<GenericResultDto<CommercialGalleriesDto>> getPageDefault(int offset, int limit, Throwable exception) {
		genericResultDto.setInfo(0, 0, 0, new CommercialGalleriesDto[0]);
		return Mono.just(genericResultDto);

	}

}
