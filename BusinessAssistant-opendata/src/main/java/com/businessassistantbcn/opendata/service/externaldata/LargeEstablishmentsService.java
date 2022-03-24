package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.LargeEstablishmentsDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.ClassificationDataDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Slf4j
@Service
public class LargeEstablishmentsService {
	
	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private PropertiesConfig config;
	@Autowired
	private GenericResultDto<LargeEstablishmentsDto> genericResultDto;
	@Autowired
	private GenericResultDto<ActivityInfoDto> genericActivityResultDto;
	
	// Get paged results
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPage(int offset, int limit) throws MalformedURLException {
		return getResultDto(offset, limit, dto -> true);
	}
	
	// Get paged results filtered by district
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPageByDistrict(int offset, int limit, int district) throws MalformedURLException {
		return getResultDto(offset, limit, dto ->
				dto.getAddresses().stream().anyMatch(a ->
						Integer.parseInt(a.getDistrict_id()) == district
		));
	}
	
	// Get paged results filtered by activity
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPageByActivity(int offset, int limit, String activityId) throws MalformedURLException {
	
		Predicate<LargeEstablishmentsDto> doFilter = largeEstablishmentsDto -> 
				largeEstablishmentsDto.getClassifications_data()
				.stream()
				.anyMatch(classificationsDataDto -> classificationsDataDto.getId() == Integer.parseInt(activityId));
		
		return getResultDto(offset, limit, doFilter);
	}
	
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	public Mono<GenericResultDto<ActivityInfoDto>> getLargeEstablishmentsAllActivities(int offset, int limit) throws MalformedURLException {
		return this.getLargeEstablishments().flatMap(largeEstablishmentDto -> {
			List<ActivityInfoDto> listFullPathFiltered = this.getListWithoutInvalidFullPaths(largeEstablishmentDto);
			List<ActivityInfoDto> listActivityInfoDto = this.getListWithoutRepeatedNames(listFullPathFiltered);
			ActivityInfoDto[] activityInfoDto =
				listActivityInfoDto.toArray(new ActivityInfoDto[listActivityInfoDto.size()]);

			ActivityInfoDto[] pagedDto = JsonHelper.filterDto(activityInfoDto, offset, limit);
			genericActivityResultDto.setInfo(offset, limit, activityInfoDto.length, pagedDto);
			return Mono.just(genericActivityResultDto);
		});
	}
	
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	private Mono<LargeEstablishmentsDto[]> getLargeEstablishments() throws MalformedURLException {
		URL url = new URL(config.getDs_largeestablishments());
		//URL url = new URL("malformedUrl");
		return httpProxy.getRequestData(url, LargeEstablishmentsDto[].class);
	}
	
	//@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	private Mono<GenericResultDto<LargeEstablishmentsDto>> getResultDto(
			int offset, int limit, Predicate<LargeEstablishmentsDto> dtoFilter) throws MalformedURLException {
			
		Mono<LargeEstablishmentsDto[]> response = getLargeEstablishments();

		return 	response.flatMap(dto -> {
			LargeEstablishmentsDto[] filteredDto = Arrays.stream(dto)
					.filter(dtoFilter)
					.toArray(LargeEstablishmentsDto[]::new);
			
			LargeEstablishmentsDto[] pagedDto = JsonHelper
					.filterDto(filteredDto, offset, limit);
			
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(pagedDto);
			genericResultDto.setCount(filteredDto.length);
			return Mono.just(genericResultDto);
		});
		
	}
	
	private Mono<GenericResultDto<LargeEstablishmentsDto>> getPageDefault(Throwable exception) {
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new LargeEstablishmentsDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}
	
	private List<ActivityInfoDto> getListWithoutInvalidFullPaths(LargeEstablishmentsDto[] largeEstablishmentsDto) {
		return Arrays.stream(largeEstablishmentsDto)
			.flatMap(largeEstablishmentDto -> largeEstablishmentDto.getClassifications_data().stream())
			.filter(classificationsDataDto -> this.isFullPathValid(classificationsDataDto))
			.map(classificationsDataDto -> new ActivityInfoDto(
				classificationsDataDto.getId(),
				this.getValidActivityName(classificationsDataDto))
					
			).sorted(Comparator.comparing(ActivityInfoDto::getActivityName))
			.collect(Collectors.toList());
	}
	
	private List<ActivityInfoDto> getListWithoutRepeatedNames(List<ActivityInfoDto> listNamesUnfilterd) {
		return io.vavr.collection.List.ofAll(listNamesUnfilterd)
			.distinctBy((s1, s2) -> s1.getActivityName().compareToIgnoreCase(s2.getActivityName()))
			.toJavaList();
	}
	private boolean isFullPathValid(ClassificationDataDto dto) {
		return ! (dto.getFullPath() == null || 
			dto.getFullPath().toUpperCase().contains("MARQUES") ||
			dto.getFullPath().toUpperCase().contains("GESTIÓ BI") ||
			dto.getFullPath().toUpperCase().contains("ÚS INTERN"));
	}

	private String getValidActivityName(ClassificationDataDto dto) {
		//If name == null, sort method fails
		return dto.getName() == null ? "" : dto.getName();
	}	
}