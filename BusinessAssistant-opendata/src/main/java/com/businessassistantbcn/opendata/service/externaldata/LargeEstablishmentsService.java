package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.largeestablishments.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.input.largeestablishments.LargeEstablishmentsDto;
import com.businessassistantbcn.opendata.dto.output.LargeEstablishmentsResponseDto;
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
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class LargeEstablishmentsService {
	private static final Logger log = LoggerFactory.getLogger(LargeEstablishmentsService.class);

	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private PropertiesConfig config;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private GenericResultDto<LargeEstablishmentsResponseDto> genericResultDto;
	@Autowired
	private GenericResultDto<ActivityInfoDto> genericActivityResultDto;

	// Get paged results
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnLargeEstablishmentsDefaultPage")
	public Mono<GenericResultDto<LargeEstablishmentsResponseDto>> getPage(int offset, int limit) throws MalformedURLException {
		return getResultDto(offset, limit, dto -> true);
	}
	
	// Get paged results filtered by district
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnLargeEstablishmentsDefaultPage")
	public Mono<GenericResultDto<LargeEstablishmentsResponseDto>> getPageByDistrict(int offset, int limit, int district)
		throws MalformedURLException {
		return getResultDto(offset, limit, dto ->
			dto.getAddresses().stream().anyMatch(a ->
				Integer.parseInt(a.getDistrict_id()) == district
		));
	}
	
	// Get paged results filtered by activity
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnLargeEstablishmentsDefaultPage")
	public Mono<GenericResultDto<LargeEstablishmentsResponseDto>> getPageByActivity(int offset, int limit, String activityId)
		throws MalformedURLException {
	
		Predicate<LargeEstablishmentsDto> dtoFilter = largeEstablishmentsDto -> 
			largeEstablishmentsDto.getClassifications_data()
			.stream()
			.anyMatch(classificationsDataDto -> classificationsDataDto.getId() == Integer.parseInt(activityId));
		
		return getResultDto(offset, limit, dtoFilter);
	}

	private Mono<GenericResultDto<LargeEstablishmentsResponseDto>> getResultDto(
		int offset, int limit, Predicate<LargeEstablishmentsDto> dtoFilter) throws MalformedURLException {
		return 	httpProxy.getRequestData(new URL(config.getDs_largeestablishments()), LargeEstablishmentsDto[].class)
			.flatMap(largeEstablishmentsDto -> {
			LargeEstablishmentsDto[] filteredDto = Arrays.stream(largeEstablishmentsDto)
				.filter(dtoFilter)
				.map(d -> this.removeClassificationDataWithMarquesAndUsInternInFullPath(d))
				.toArray(LargeEstablishmentsDto[]::new);
			
			LargeEstablishmentsDto[] pagedDto = JsonHelper
				.filterDto(filteredDto, offset, limit);

			LargeEstablishmentsResponseDto[] responseDto = Arrays.stream(pagedDto).map(p -> mapToResponseDto(p)).toArray(LargeEstablishmentsResponseDto[]::new);
			genericResultDto.setInfo(offset, limit, responseDto.length, responseDto);
			return Mono.just(genericResultDto);
		})
		.onErrorResume(e -> this.getLargeEstablishmentsDefaultPage(new OpendataUnavailableServiceException()));
	}
	
	private LargeEstablishmentsDto removeClassificationDataWithMarquesAndUsInternInFullPath(LargeEstablishmentsDto largeEstablishmentDto) {
		List<ClassificationDataDto> classData = largeEstablishmentDto.getClassifications_data().stream()
				.filter(d -> !d.getFullPath().toUpperCase().contains("MARQUES"))
				.filter(d -> !d.getFullPath().toUpperCase().contains("ÚS INTERN"))
				.collect(Collectors.toList());
		largeEstablishmentDto.setClassifications_data(classData);
		return largeEstablishmentDto;
	}
	
	private LargeEstablishmentsResponseDto mapToResponseDto(LargeEstablishmentsDto largeEstablishmentsDto) {
		LargeEstablishmentsResponseDto responseDto = modelMapper.map(largeEstablishmentsDto, LargeEstablishmentsResponseDto.class);
		responseDto.setValue(largeEstablishmentsDto.getValues());
		responseDto.setActivities(responseDto.mapClassificationDataListToActivityInfoList(largeEstablishmentsDto.getClassifications_data()));
	    return responseDto;
	}

	private Mono<GenericResultDto<LargeEstablishmentsResponseDto>> logServerErrorReturnLargeEstablishmentsDefaultPage(Throwable exception) {
		log.error("Opendata is down");
		return this.getLargeEstablishmentsDefaultPage(exception);
	}

	private Mono<GenericResultDto<LargeEstablishmentsResponseDto>> logInternalErrorReturnLargeEstablishmentsDefaultPage(Throwable exception) {
		log.error("BusinessAssistant error: "+exception.getMessage());
		return this.getLargeEstablishmentsDefaultPage(exception);
	}

	private Mono<GenericResultDto<LargeEstablishmentsResponseDto>> getLargeEstablishmentsDefaultPage(Throwable exception) {
		genericResultDto.setInfo(0, 0, 0, new LargeEstablishmentsResponseDto[0]);
		return Mono.just(genericResultDto);
	}

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnActivitiesDefaultPage")
	public Mono<GenericResultDto<ActivityInfoDto>> getLargeEstablishmentsActivities(int offset, int limit)
		throws MalformedURLException {
		return httpProxy.getRequestData(new URL(config.getDs_largeestablishments()), LargeEstablishmentsDto[].class)
			.flatMap(largeEstablishmentDto -> {
			List<ActivityInfoDto> listFullPathFiltered = this.getListWithoutInvalidFullPaths(largeEstablishmentDto);
			List<ActivityInfoDto> listActivityInfoDto = this.getListWithoutRepeatedNames(listFullPathFiltered);
			ActivityInfoDto[] activityInfoDto =
				listActivityInfoDto.toArray(new ActivityInfoDto[listActivityInfoDto.size()]);

			ActivityInfoDto[] pagedDto = JsonHelper.filterDto(activityInfoDto, offset, limit);
			genericActivityResultDto.setInfo(offset, limit, activityInfoDto.length, pagedDto);
			return Mono.just(genericActivityResultDto);
		})
		.onErrorResume(e -> this.logServerErrorReturnActivitiesDefaultPage(new OpendataUnavailableServiceException()));
	}

	private Mono<GenericResultDto<ActivityInfoDto>> getActivitiesDefaultPage(Throwable exception) {
		genericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);
		return Mono.just(genericActivityResultDto);
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

	private Mono<GenericResultDto<ActivityInfoDto>> logServerErrorReturnActivitiesDefaultPage(Throwable exception) {
		log.error("Opendata is down");
		return this.getActivitiesDefaultPage(exception);
	}

	private Mono<GenericResultDto<ActivityInfoDto>> logInternalErrorReturnActivitiesDefaultPage(Throwable exception) {
		log.error("BusinessAssistant error: "+exception.getMessage());
		return this.getActivitiesDefaultPage(exception);
	}
}