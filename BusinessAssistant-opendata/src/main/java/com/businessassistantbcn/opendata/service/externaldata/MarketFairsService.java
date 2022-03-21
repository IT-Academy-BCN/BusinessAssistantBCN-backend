package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.marketfairs.MarketFairsDto;
import com.businessassistantbcn.opendata.exception.OpendataUnavailableServiceException;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MarketFairsService {

	private static final Logger log = LoggerFactory.getLogger(MarketFairsService.class);

	@Autowired
	private PropertiesConfig config;
	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private GenericResultDto<MarketFairsDto> genericResultDto;


	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnMarketFairsDefaultPage")
	public Mono<GenericResultDto<MarketFairsDto>>getPage(int offset, int limit) throws MalformedURLException {
		return httpProxy.getRequestData(new URL(config.getDs_marketfairs()), MarketFairsDto[].class)
			.flatMap(dtos -> {
				MarketFairsDto[] pagedDto = JsonHelper.filterDto(dtos, offset, limit);
				genericResultDto.setInfo(offset, limit, dtos.length, pagedDto);
				return Mono.just(genericResultDto);
			})
			.onErrorResume(e -> this.logServerErrorReturnMarketFairsDefaultPage(new OpendataUnavailableServiceException()));
	}

	private Mono<GenericResultDto<MarketFairsDto>> logServerErrorReturnMarketFairsDefaultPage(Throwable exception) {
		log.error("Opendata is down");
		return this.getMarketFairsDefaultPage(exception);
	}

	private Mono<GenericResultDto<MarketFairsDto>> logInternalErrorReturnMarketFairsDefaultPage(Throwable exception) {
		log.error("BusinessAssistant error");
		return this.getMarketFairsDefaultPage(exception);
	}

	public Mono<GenericResultDto<MarketFairsDto>> getMarketFairsDefaultPage(Throwable exception) {
		genericResultDto.setInfo(0, 0, 0, new MarketFairsDto[0]);
		return Mono.just(genericResultDto);
	}

}
