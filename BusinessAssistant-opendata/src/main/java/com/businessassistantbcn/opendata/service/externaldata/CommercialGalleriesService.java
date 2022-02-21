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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;	

	public Mono<GenericResultDto<CommercialGalleriesDto>> getPage(int offset, int limit) {
	    URL url;
		try {
			url = new URL(config.getDs_commercialgalleries());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			return getPageDefault();
		}		
		
		Mono<CommercialGalleriesDto[]> response = httpProxy.getRequestData(url, CommercialGalleriesDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
			
		return circuitBreaker.run(() -> response.flatMap(dto -> {
			CommercialGalleriesDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(filteredDto);
			genericResultDto.setCount(dto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());		
		
	}
		
	private Mono<GenericResultDto<CommercialGalleriesDto>> getPageDefault(){
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new CommercialGalleriesDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}		

	public Mono<GenericResultDto<ActivityInfoDto>> commercialGalleriesActivities(int offset, int limit) {
		URL url = this.getUrl();
		if (url == null) {
			return getActivitiesDefaultPage();
		}
		Mono<CommercialGalleriesDto[]> response = httpProxy.getRequestData(url, CommercialGalleriesDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

		return circuitBreaker.run( () -> response.flatMap(commercialGalleriesDto -> {

			List<ActivityInfoDto> listFullPathFiltered = this.getListWithoutInvalidFullPaths(commercialGalleriesDto);
			List<ActivityInfoDto> listActivityInfoDto = this.getListWithoutRepeatedNames(listFullPathFiltered);
			ActivityInfoDto[] activityInfoDto =
				listActivityInfoDto.toArray(new ActivityInfoDto[listActivityInfoDto.size()]);

			ActivityInfoDto[] pagedDto = JsonHelper.filterDto(activityInfoDto, offset, limit);
			genericActivityResultDto.setInfo(offset, limit, activityInfoDto.length, pagedDto);
			return Mono.just(genericActivityResultDto);

		}), throwable -> getActivitiesDefaultPage()
		);
	}

	private Mono<GenericResultDto<ActivityInfoDto>> getActivitiesDefaultPage() {
		genericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);
		return Mono.just(genericActivityResultDto);
	}

	private URL getUrl() {
		URL url;
		try {
			url = new URL(config.getDs_commercialgalleries());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			url = null;
		}
		return url;
	}
	private List<ActivityInfoDto> getListWithoutInvalidFullPaths(CommercialGalleriesDto[] commercialGalleriesDto) {
		return Arrays.stream(commercialGalleriesDto)
			.flatMap(commercialGalleryDto -> commercialGalleryDto.getClassifications_data().stream())
			.filter(classificationsDataDto -> this.isFullPathValid(classificationsDataDto))
			.map(classificationsDataDto -> new ActivityInfoDto(
				(long) classificationsDataDto.getId(),
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

		return ! (dto.getFull_path() == null ||
			dto.getFull_path().toUpperCase().contains("MARQUES") ||
			dto.getFull_path().toUpperCase().contains("GESTIÓ BI") ||
			dto.getFull_path().toUpperCase().contains("ÚS INTERN"));
	}

	private String getValidActivityName(ClassificationDataDto dto) {
		//If name == null, sort method fails
		return dto.getName() == null ? "" : dto.getName();
	}
}
