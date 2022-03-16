package com.businessassistantbcn.opendata.service.externaldata;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.CommercialGalleriesDto;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;

import com.businessassistantbcn.opendata.helper.JsonHelper;

import reactor.core.publisher.Mono;

@Service
public class CommercialGalleriesService {

	private static final Logger log = LoggerFactory.getLogger(CommercialGalleriesService.class);

	@Autowired
	HttpProxy httpProxy;

	@Autowired
	private PropertiesConfig config;

	@Autowired
	private GenericResultDto<CommercialGalleriesDto> genericResultDto;
	
	@Autowired
	private GenericResultDto<ActivityInfoDto> genericActivityResultDto;
	

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefault")
	public Mono<GenericResultDto<CommercialGalleriesDto>> getPage(int offset, int limit) throws MalformedURLException {
		return httpProxy.getRequestData(new URL(config.getDs_commercialgalleries()), CommercialGalleriesDto[].class)
			.flatMap(dtos -> {
				CommercialGalleriesDto[] filteredDto = JsonHelper.filterDto(dtos, offset, limit);
				genericResultDto.setInfo(offset, limit, dtos.length, filteredDto);
				return Mono.just(genericResultDto);
			});
	}

	public Mono<GenericResultDto<CommercialGalleriesDto>> getPageDefault(Throwable exception) {
		genericResultDto.setInfo(0, 0, 0, new CommercialGalleriesDto[0]);
		return Mono.just(genericResultDto);
	}
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getActivitiesDefaultPage")
	public Mono<GenericResultDto<ActivityInfoDto>> CommercialGalleriesActivities(int offset, int limit) throws MalformedURLException {
		return httpProxy.getRequestData(new URL(config.getDs_commercialgalleries()), CommercialGalleriesDto[].class)
			.flatMap(commercialGalleriesDto -> {
				List<ActivityInfoDto> listFullPathFiltered = this.getListWithoutInvalidFullPaths(commercialGalleriesDto);
				List<ActivityInfoDto> listActivityInfoDto = this.getListWithoutRepeatedNames(listFullPathFiltered);
				ActivityInfoDto[] activityInfoDto =
					listActivityInfoDto.toArray(new ActivityInfoDto[listActivityInfoDto.size()]);

				ActivityInfoDto[] pagedDto = JsonHelper.filterDto(activityInfoDto, offset, limit);
				genericActivityResultDto.setInfo(offset, limit, activityInfoDto.length, pagedDto);
				return Mono.just(genericActivityResultDto);
			});
	}

	public Mono<GenericResultDto<ActivityInfoDto>> getActivitiesDefaultPage(Throwable exception) {
		genericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);
		return Mono.just(genericActivityResultDto);
	}

	private List<ActivityInfoDto> getListWithoutInvalidFullPaths(CommercialGalleriesDto[] commercialGalleriesDto) {
		return Arrays.stream(commercialGalleriesDto)
			.flatMap(commercialGalleryDto -> commercialGalleryDto.getClassifications_data().stream())
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

	public GenericResultDto<CommercialGalleriesDto> getCommercialGalleriesDtoByActivityDto(int[] activities, int offset, int limit) {
		//lambda filter
		return null;
	}

	public GenericResultDto<CommercialGalleriesDto> getCommercialGalleriesDtoByDistrictDto(int[] districts, int offset, int limit) {
		//lambda filter
		return null;
	}

	public String getCommercialGalleriesByActivity(int[] activities, int offset, int limit) {
		//JsonPath search
		/* OJO a formato de salida:
		{
		"count": 1217,
		"elements": [
		{
		"id": 3716,
		"name": "Paola",
		"surnames": "dos Reis Figueira",
		...
		*/
		return null;
	}
}
