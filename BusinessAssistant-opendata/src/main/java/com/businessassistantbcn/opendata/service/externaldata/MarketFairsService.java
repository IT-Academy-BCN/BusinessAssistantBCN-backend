package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.marketfairs.MarketFairsDto;
import com.businessassistantbcn.opendata.dto.output.MarketFairsResponseDto;
import com.businessassistantbcn.opendata.exception.OpendataUnavailableServiceException;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@Service
public class MarketFairsService {

	private static final Logger log = LoggerFactory.getLogger(MarketFairsService.class);

	@Autowired
	private PropertiesConfig config;
	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private GenericResultDto<MarketFairsResponseDto> genericResultDto;
	@Autowired
	private GenericResultDto<ActivityInfoDto> genericActivityResultDto;


	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnMarketFairsDefaultPage")
	public Mono<GenericResultDto<MarketFairsResponseDto>>getPage(int offset, int limit) throws MalformedURLException {
		return httpProxy.getRequestData(new URL(config.getDs_marketfairs()), MarketFairsDto[].class)
			.flatMap(dtos -> {
				MarketFairsDto[] pagedDto = JsonHelper.filterDto(dtos, offset, limit);
				MarketFairsResponseDto[] responseDto = Arrays.stream(pagedDto).map(p -> convertToDto(p)).toArray(MarketFairsResponseDto[]::new);
				genericResultDto.setInfo(offset, limit, responseDto.length, responseDto);

				return Mono.just(genericResultDto);
			})
			.onErrorResume(e -> this.logServerErrorReturnMarketFairsDefaultPage(new OpendataUnavailableServiceException()));
	}

	private MarketFairsResponseDto convertToDto(MarketFairsDto marketFairsDto) {
		MarketFairsResponseDto responseDto = modelMapper.map(marketFairsDto, MarketFairsResponseDto.class);
		responseDto.setWeb(marketFairsDto.getValues().getUrl_value());
		responseDto.setEmail(marketFairsDto.getValues().getEmail_value());
		responseDto.setPhone(marketFairsDto.getValues().getPhone_value());
		responseDto.setActivities(responseDto.mapClassificationDataListToActivityInfoList(marketFairsDto.getClassifications_data()));
		responseDto.setAddresses(responseDto.mapAddressesToCorrectLocation(marketFairsDto.getAddresses(), marketFairsDto.getCoordinates()));
	    return responseDto;
	}

	private Mono<GenericResultDto<MarketFairsResponseDto>> logServerErrorReturnMarketFairsDefaultPage(Throwable exception) {
		log.error("Opendata is down");
		return this.getMarketFairsDefaultPage(exception);
	}

	private Mono<GenericResultDto<MarketFairsResponseDto>> logInternalErrorReturnMarketFairsDefaultPage(Throwable exception) {
		log.error("BusinessAssistant error: "+exception.getMessage());
		return this.getMarketFairsDefaultPage(exception);
	}

	private Mono<GenericResultDto<MarketFairsResponseDto>> getMarketFairsDefaultPage(Throwable exception) {
		genericResultDto.setInfo(0, 0, 0, new MarketFairsResponseDto[0]);
		return Mono.just(genericResultDto);
	}

}