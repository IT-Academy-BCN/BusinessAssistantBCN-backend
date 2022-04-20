package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.CommercialGalleriesDto;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommercialGalleriesService {

	private static final Logger log = LoggerFactory.getLogger(CommercialGalleriesService.class);

	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private PropertiesConfig config;
	@Autowired
	private GenericResultDto<CommercialGalleriesDto> genericResultDto;
	@Autowired
	private GenericResultDto<ActivityInfoDto> genericActivityResultDto;

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnCommercialGalleriesDefaultPage")
	public Mono<GenericResultDto<CommercialGalleriesDto>> getPage(int offset, int limit) throws MalformedURLException {
		return httpProxy.getRequestData(new URL(config.getDs_commercialgalleries()), CommercialGalleriesDto[].class)
			.flatMap(dtos -> {
				CommercialGalleriesDto[] filteredDto = JsonHelper.filterDto(dtos, offset, limit);
				genericResultDto.setInfo(offset, limit, dtos.length, filteredDto);
				return Mono.just(genericResultDto);
			})
			.onErrorResume(
				e -> this.logServerErrorReturnCommercialGalleriesDefaultPage(new OpendataUnavailableServiceException())
			);
	}

	private Mono<GenericResultDto<CommercialGalleriesDto>> logServerErrorReturnCommercialGalleriesDefaultPage(
		Throwable exception
	) {
		log.error("Opendata is down");
		return this.getCommercialGalleriesDefaultPage(exception);
	}

	private Mono<GenericResultDto<CommercialGalleriesDto>> logInternalErrorReturnCommercialGalleriesDefaultPage(
		Throwable exception
	) {
		log.error("BusinessAssistant error: "+exception.getMessage());
		return this.getCommercialGalleriesDefaultPage(exception);
	}

	private Mono<GenericResultDto<CommercialGalleriesDto>> getCommercialGalleriesDefaultPage(Throwable exception) {
		genericResultDto.setInfo(0, 0, 0, new CommercialGalleriesDto[0]);
		return Mono.just(genericResultDto);
	}

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnActivitiesDefaultPage")
	public Mono<GenericResultDto<ActivityInfoDto>> getCommercialGalleriesActivities(int offset, int limit)
		throws MalformedURLException {
		return httpProxy.getRequestData(new URL(config.getDs_commercialgalleries()), CommercialGalleriesDto[].class)
			.flatMap(dtos -> {
				List<ActivityInfoDto> listFullPathFiltered = this.getListWithoutInvalidFullPaths(dtos);
				List<ActivityInfoDto> listActivityInfoDto = this.getListWithoutRepeatedNames(listFullPathFiltered);
				ActivityInfoDto[] activityInfoDto =
					listActivityInfoDto.toArray(new ActivityInfoDto[listActivityInfoDto.size()]);

				ActivityInfoDto[] pagedDto = JsonHelper.filterDto(activityInfoDto, offset, limit);
				genericActivityResultDto.setInfo(offset, limit, activityInfoDto.length, pagedDto);
				return Mono.just(genericActivityResultDto);
			})
			.onErrorResume(e -> this.logServerErrorReturnActivitiesDefaultPage(new OpendataUnavailableServiceException()));
	}

	public Mono<GenericResultDto<ActivityInfoDto>> getActivitiesDefaultPage(Throwable exception) {
		genericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);
		return Mono.just(genericActivityResultDto);
	}

	private List<ActivityInfoDto> getListWithoutInvalidFullPaths(CommercialGalleriesDto[] dtos) {
		return Arrays.stream(dtos)
			.flatMap(bigMallDto -> bigMallDto.getClassifications_data().stream())
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
