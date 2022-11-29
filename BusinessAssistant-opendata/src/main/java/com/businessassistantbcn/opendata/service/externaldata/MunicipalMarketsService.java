package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.municipalmarkets.MunicipalMarketsDto;
import com.businessassistantbcn.opendata.dto.input.municipalmarkets.MunicipalMarketsSearchDTO;
import com.businessassistantbcn.opendata.dto.output.MunicipalMarketsResponseDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.function.Predicate;

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

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnMunicipalMarketsDefaultPage")
	public Mono<GenericResultDto<MunicipalMarketsResponseDto>> getPage(int offset, int limit) {
		return httpProxy.getRequestData(URI.create(config.getDs_municipalmarkets()), MunicipalMarketsDto[].class)
			.flatMap(dtos -> {
				MunicipalMarketsDto[] pagedDto = JsonHelper.filterDto(dtos, offset, limit);

				MunicipalMarketsResponseDto[] responseDto = Arrays.stream(pagedDto).map(this::mapToResponseDto).toArray(MunicipalMarketsResponseDto[]::new);
				genericResultDto.setInfo(offset, limit, dtos.length, responseDto);
				return Mono.just(genericResultDto);
			}).switchIfEmpty(getMunicipalMarketsDefaultPage());
	}

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnMunicipalMarketsDefaultPage")
	public Mono<GenericResultDto<MunicipalMarketsResponseDto>> getPageByDistrict(int offset, int limit, int district) {
		return getResultDto(offset, limit, dto ->
				dto.getAddresses().stream().anyMatch(a ->
						Integer.parseInt(a.getDistrict_id()) == district
				));
	}

	// Get paged results filtered by search parameters (zones and activities)
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnMunicipalMarketsDefaultPage")
	public Mono<GenericResultDto<MunicipalMarketsResponseDto>> getPageBySearch(int offset, int limit, MunicipalMarketsSearchDTO searchParams) {

		Predicate<MunicipalMarketsDto> zoneFilter;
		if (searchParams.getZones().length > 0) {
			zoneFilter = municipalMarketsDto ->
					municipalMarketsDto.getAddresses()
							.stream()
							.anyMatch(address ->
									Arrays.stream(searchParams.getZones())
											.anyMatch(zoneId -> zoneId == Integer.parseInt(address.getDistrict_id())));
		} else {
			zoneFilter = municipalMarketsDto -> true;
		}

		return getResultDto(offset, limit, zoneFilter);
	}

	private Mono<GenericResultDto<MunicipalMarketsResponseDto>> getResultDto(
			int offset, int limit, Predicate<MunicipalMarketsDto> dtoFilter) {
		return httpProxy.getRequestData(URI.create(config.getDs_municipalmarkets()), MunicipalMarketsDto[].class)
				.flatMap(municipalMarketsDto -> {
					MunicipalMarketsDto[] fullDto = Arrays.stream(municipalMarketsDto)
							.toArray(MunicipalMarketsDto[]::new);

					MunicipalMarketsDto[] filteredDto = Arrays.stream(fullDto)
							.filter(dtoFilter)
							.toArray(MunicipalMarketsDto[]::new);

					MunicipalMarketsDto[] pagedDto = JsonHelper.filterDto(filteredDto, offset, limit);

					MunicipalMarketsResponseDto[] responseDto = Arrays.stream(pagedDto).map(this::mapToResponseDto).toArray(MunicipalMarketsResponseDto[]::new);

					genericResultDto.setInfo(offset, limit, fullDto.length, responseDto);
					return Mono.just(genericResultDto);
				}).switchIfEmpty(getMunicipalMarketsDefaultPage());
	}

	private MunicipalMarketsResponseDto mapToResponseDto(MunicipalMarketsDto municipalMarketsDto) {
		MunicipalMarketsResponseDto responseDto = modelMapper.map(municipalMarketsDto, MunicipalMarketsResponseDto.class);
		responseDto.setWeb(municipalMarketsDto.getWeb());
		responseDto.setEmail(municipalMarketsDto.getEmail());
		responseDto.setPhone(municipalMarketsDto.getPhone());
		responseDto.setAddresses(responseDto.mapAddressesToCorrectLocation(municipalMarketsDto.getAddresses(), municipalMarketsDto.getCoordinates()));

		return responseDto;
	}

	@SuppressWarnings("unused")
	private Mono<GenericResultDto<MunicipalMarketsResponseDto>> logServerErrorReturnMunicipalMarketsDefaultPage(Throwable exception) {
		log.error("Opendata is down");
		return this.getMunicipalMarketsDefaultPage();
	}

	@SuppressWarnings("unused")
	private Mono<GenericResultDto<MunicipalMarketsResponseDto>> logInternalErrorReturnMunicipalMarketsDefaultPage(Throwable exception) {
		log.error("BusinessAssistant error: "+exception.getMessage());
		return this.getMunicipalMarketsDefaultPage();
	}

	private Mono<GenericResultDto<MunicipalMarketsResponseDto>> getMunicipalMarketsDefaultPage() {
		genericResultDto.setInfo(0, 0, 0, new MunicipalMarketsResponseDto[0]);
		return Mono.just(genericResultDto);
	}
}
