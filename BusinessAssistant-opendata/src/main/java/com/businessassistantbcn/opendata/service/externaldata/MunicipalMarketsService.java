package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.municipalmarkets.MunicipalMarketsDto;
import com.businessassistantbcn.opendata.dto.output.MunicipalMarketsResponseDto;
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
public class MunicipalMarketsService {

	private static final Logger log = LoggerFactory.getLogger(MunicipalMarketsService.class);

	@Autowired
	private PropertiesConfig config;
	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private GenericResultDto<MunicipalMarketsResponseDto> genericResultDto;
	@Autowired
	private GenericResultDto<ActivityInfoDto> genericActivityResultDto;

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnMunicipalMarketsDefaultPage")
	public Mono<GenericResultDto<MunicipalMarketsResponseDto>> getPage(int offset, int limit) throws MalformedURLException {
		return httpProxy.getRequestData(new URL(config.getDs_municipalmarkets()), MunicipalMarketsDto[].class)
			.flatMap(dtos -> {
				MunicipalMarketsDto[] pagedDto = JsonHelper.filterDto(dtos, offset, limit);


				MunicipalMarketsResponseDto[] responseDto = Arrays.stream(pagedDto).map(p -> mapToResponseDto(p)).toArray(MunicipalMarketsResponseDto[]::new);
				genericResultDto.setInfo(offset, limit, dtos.length, responseDto);
				return Mono.just(genericResultDto);
			});
	}

	private MunicipalMarketsResponseDto mapToResponseDto(MunicipalMarketsDto municipalMarketsDto) {
		MunicipalMarketsResponseDto responseDto = modelMapper.map(municipalMarketsDto, MunicipalMarketsResponseDto.class);
		responseDto.setWeb(municipalMarketsDto.getWeb());
		responseDto.setEmail(municipalMarketsDto.getEmail());
		responseDto.setPhone(municipalMarketsDto.getPhone());
		responseDto.setAddresses(responseDto.mapAddressesToCorrectLocation(municipalMarketsDto.getAddresses(), municipalMarketsDto.getCoordinates()));

		return responseDto;
	}

	private Mono<GenericResultDto<MunicipalMarketsResponseDto>> logServerErrorReturnMunicipalMarketsDefaultPage(Throwable exception) {
		log.error("Opendata is down");
		return this.getMunicipalMarketsDefaultPage();
	}

	private Mono<GenericResultDto<MunicipalMarketsResponseDto>> logInternalErrorReturnMunicipalMarketsDefaultPage(Throwable exception) {
		log.error("BusinessAssistant error: "+exception.getMessage());
		return this.getMunicipalMarketsDefaultPage();
	}

	private Mono<GenericResultDto<MunicipalMarketsResponseDto>> getMunicipalMarketsDefaultPage() {
		genericResultDto.setInfo(0, 0, 0, new MunicipalMarketsResponseDto[0]);
		return Mono.just(genericResultDto);
	}
}
