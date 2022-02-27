package com.businessassistantbcn.opendata.service.externaldata;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
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
				.flatMap(dto -> {
					CommercialGalleriesDto[] filteredDto = JsonHelper.filterDto(dto, offset, limit);
					genericResultDto.setLimit(limit);
					genericResultDto.setOffset(offset);
					genericResultDto.setResults(filteredDto);
					genericResultDto.setCount(dto.length);
					return Mono.just(genericResultDto);
				});
	}

	public Mono<GenericResultDto<CommercialGalleriesDto>> getPageDefault(int offset, int limit, Throwable exception) {
		genericResultDto.setInfo(0, 0, 0, new CommercialGalleriesDto[0]);
		return Mono.just(genericResultDto);

	}
	
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "getPageDefaultActivity")
	public Mono<GenericResultDto<ActivityInfoDto>> getCommercialGalleriesByActivity(int offset, int limit) throws MalformedURLException
	{
	return httpProxy.getRequestData(new URL(config.getDs_commercialgalleries()), CommercialGalleriesDto[].class)
			.flatMap(comercialgalleriesDto -> {
				List<ActivityInfoDto> listactivityInfoDto = new ArrayList<>();
				listactivityInfoDto = io.vavr.collection.List.ofAll(
					Arrays.stream(comercialgalleriesDto)
						.flatMap(comercialGalleryDto -> comercialGalleryDto.getClassifications_data().stream())
						.filter(classificationsDataDto ->
							(classificationsDataDto.getFull_path() == null) ||
								(
									(!classificationsDataDto.getFull_path().toUpperCase().contains("MARQUES")) &&
									(!classificationsDataDto.getFull_path().toUpperCase().contains("GESTIÓ BI")) &&
									(!classificationsDataDto.getFull_path().toUpperCase().contains("ÚS INTERN"))
								)
						)
						.map(classificationsDataDto -> {
							return new ActivityInfoDto(
									(long)classificationsDataDto.getId(),
									((classificationsDataDto.getName() == null) ? "" : classificationsDataDto.getName())
							);
						})
						.sorted(Comparator.comparing(ActivityInfoDto::getActivityName))
						.collect(Collectors.toList()))
				.distinctBy((s1, s2) -> s1.getActivityName().compareToIgnoreCase(s2.getActivityName()))
				.toJavaList();

				ActivityInfoDto[] activityInfoDto =
					listactivityInfoDto.toArray(new ActivityInfoDto[listactivityInfoDto.size()]);

				ActivityInfoDto[] pagedDto = JsonHelper.filterDto(activityInfoDto, offset, limit);
				genericActivityResultDto.setInfo(offset, limit, activityInfoDto.length, pagedDto);
				return Mono.just(genericActivityResultDto);
			});
	}
	
	private Mono<GenericResultDto<ActivityInfoDto>> getPageDefaultActivity(){
		genericActivityResultDto.setLimit(0);
		genericActivityResultDto.setOffset(0);
		genericActivityResultDto.setResults(new ActivityInfoDto[0]);
		genericActivityResultDto.setCount(0);
		return Mono.just(genericActivityResultDto);
	}		

}
