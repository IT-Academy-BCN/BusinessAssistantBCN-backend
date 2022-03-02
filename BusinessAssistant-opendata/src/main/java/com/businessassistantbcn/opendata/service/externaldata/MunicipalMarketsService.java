package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.economicactivitiescensus.EconomicActivitiesCensusDto;
import com.businessassistantbcn.opendata.dto.marketfairs.MarketFairsDto;
import com.businessassistantbcn.opendata.dto.municipalmarkets.MunicipalMarketsDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MunicipalMarketsService {

	private static final Logger log = LoggerFactory.getLogger(MunicipalMarketsService.class);

	@Autowired
	private PropertiesConfig config;
	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private GenericResultDto<MunicipalMarketsDto> genericResultDto;

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getMunicipalMarketsDefaultPage")
	public Mono<GenericResultDto<MunicipalMarketsDto>> getPage(int offset, int limit) throws MalformedURLException {
		return httpProxy
			.getRequestData(new URL(config.getDs_municipalmarkets()), MunicipalMarketsDto[].class)
			.flatMap(dtos -> {
				MunicipalMarketsDto[] pagedDto = JsonHelper.filterDto(dtos, offset, limit);
				genericResultDto.setInfo(offset, limit, dtos.length, pagedDto);
				return Mono.just(genericResultDto);
			});
	}

	public Mono<GenericResultDto<MunicipalMarketsDto>> getMunicipalMarketsDefaultPage(Throwable exception) {
		genericResultDto.setInfo(0, 0, 0, new MunicipalMarketsDto[0]);
		return Mono.just(genericResultDto);
	}
}
