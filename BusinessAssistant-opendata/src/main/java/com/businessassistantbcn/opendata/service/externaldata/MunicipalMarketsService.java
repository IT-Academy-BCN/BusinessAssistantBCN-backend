package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.municipalmarkets.MunicipalMarketsDto;
import com.businessassistantbcn.opendata.dto.output.MunicipalMarketsResponseDto;
import com.businessassistantbcn.opendata.exception.OpendataUnavailableServiceException;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@Slf4j
@Service
public class MunicipalMarketsService {

	//private static final Logger log = LoggerFactory.getLogger(MunicipalMarketsService.class);

	@Autowired
	private PropertiesConfig config;
	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private GenericResultDto<MunicipalMarketsResponseDto> genericResultDto;

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnMunicipalMarketsDefaultPage")
	public Mono<GenericResultDto<MunicipalMarketsResponseDto>> getPage(int offset, int limit) throws MalformedURLException {
		return httpProxy
			.getRequestData(new URL(config.getDs_municipalmarkets()), MunicipalMarketsDto[].class)
			.flatMap(dtos -> {
				MunicipalMarketsDto[] pagedDto = JsonHelper.filterDto(dtos, offset, limit);
				
				MunicipalMarketsResponseDto[] responseDto = Arrays.stream(pagedDto).map(p -> mapToResponseDto(p)).toArray(MunicipalMarketsResponseDto[]::new);
				genericResultDto.setInfo(offset, limit, dtos.length, responseDto);
				return Mono.just(genericResultDto);
			})
			.onErrorResume(e -> this.logServerErrorReturnMunicipalMarketsDefaultPage(new OpendataUnavailableServiceException()));
	}

	private MunicipalMarketsResponseDto mapToResponseDto(MunicipalMarketsDto municipalMarketsDto) {
		MunicipalMarketsResponseDto responseDto = modelMapper.map(municipalMarketsDto, MunicipalMarketsResponseDto.class);
		responseDto.setActivity(responseDto.mapClassificationDataDtoToActivityDto(municipalMarketsDto.getClassificationsData()));
		return responseDto;
	}

	private Mono<GenericResultDto<MunicipalMarketsResponseDto>> logServerErrorReturnMunicipalMarketsDefaultPage(Throwable exception) {
		log.error("Opendata is down");
		return this.getMunicipalMarketsDefaultPage(exception);
	}

	private Mono<GenericResultDto<MunicipalMarketsResponseDto>> logInternalErrorReturnMunicipalMarketsDefaultPage(Throwable exception) {
		log.error("BusinessAssistant error: "+exception.getMessage());
		return this.getMunicipalMarketsDefaultPage(exception);
	}

	private Mono<GenericResultDto<MunicipalMarketsResponseDto>> getMunicipalMarketsDefaultPage(Throwable exception) {
		genericResultDto.setInfo(0, 0, 0, new MunicipalMarketsResponseDto[0]);
		return Mono.just(genericResultDto);
	}
}
