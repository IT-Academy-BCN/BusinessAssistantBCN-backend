package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import java.util.Arrays;
import java.util.function.Predicate;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class LargeStablishmentsService {
	
	@Autowired
	HttpProxy httpProxy;
	@Autowired
	private PropertiesConfig config;
	@Autowired
	private GenericResultDto<LargeStablishmentsDto> genericResultDto;
	
	// Get paged results
	public Mono<GenericResultDto<LargeStablishmentsDto>> getPage(int offset, int limit) {
		return getResultDto(offset, limit, dto -> true);
	}
	
	// Get paged results filtered by district
	public Mono<GenericResultDto<LargeStablishmentsDto>> getPageByDistrict(int offset, int limit, int district) {
		return getResultDto(offset, limit, dto ->
				dto.getAddresses().stream().anyMatch(a ->
						Integer.parseInt(a.getDistrict_id()) == district
		));
	}
	
	// Get paged results filtered by activity
	public Mono<GenericResultDto<LargeStablishmentsDto>> getPageByActivity(int offset, int limit, int activity) {
		return null; // *** FILTRO PENDIENTE ***
	}
	
	private Mono<GenericResultDto<LargeStablishmentsDto>> getResultDto(
			int offset, int limit, Predicate<LargeStablishmentsDto> dtoFilter) { try {
		
		Mono<LargeStablishmentsDto[]> response = httpProxy.getRequestData(new URL(config.getDs_largestablishments()),
				LargeStablishmentsDto[].class);
		
		return response.flatMap(dto -> {
			LargeStablishmentsDto[] filteredDto = Arrays.stream(dto)
					.filter(dtoFilter)
					.toArray(LargeStablishmentsDto[]::new);
			
			LargeStablishmentsDto[] pagedDto = JsonHelper
					.pageDto(filteredDto, offset, limit);
			
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(pagedDto);
			genericResultDto.setCount(filteredDto.length);
			return Mono.just(genericResultDto);
		});
		
	} catch(MalformedURLException e) {
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", e);
	} }
	
}