package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.LargeEstablishmentsDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Slf4j
@Service
public class LargeEstablishmentsService {
	
	@Autowired
	HttpProxy httpProxy;
	@Autowired
	private PropertiesConfig config;
	@Autowired
	private GenericResultDto<LargeEstablishmentsDto> genericResultDto;
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	@Autowired
	private GenericResultDto<ActivityInfoDto> genericActivityResultDto;		
	
	// Get paged results
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPage(int offset, int limit) {
		return getResultDto(offset, limit, dto -> true);
	}
	
	// Get paged results filtered by district
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPageByDistrict(int offset, int limit, int district) {
		return getResultDto(offset, limit, dto ->
				dto.getAddresses().stream().anyMatch(a ->
						Integer.parseInt(a.getDistrict_id()) == district
		));
	}
	
	// Get paged results filtered by activity
	public Mono<GenericResultDto<LargeEstablishmentsDto>> getPageByActivity(int offset, int limit, String activityId) {
	
		Predicate<LargeEstablishmentsDto> doFilter = largeEstablishmentsDto -> 
				largeEstablishmentsDto.getClassifications_data()
				.stream()
				.anyMatch(classificationsDataDto -> classificationsDataDto.getId() == Integer.parseInt(activityId));
		
		return getResultDto(offset, limit, doFilter);
	}
		
	private Mono<GenericResultDto<LargeEstablishmentsDto>> getResultDto(
			int offset, int limit, Predicate<LargeEstablishmentsDto> dtoFilter) { try {
		
		Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(new URL(config.getDs_largeestablishments()),
				LargeEstablishmentsDto[].class);
		
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
		
		return circuitBreaker.run(() ->	response.flatMap(dto -> {
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
		}), throwable -> getPageDefault());
		
	} catch(MalformedURLException e) {
		log.error("URL bad configured: " + e.getMessage());
		return getPageDefault();
	} }
	
	
	
	public Mono<GenericResultDto<ActivityInfoDto>>getActivities(int offset, int limit) {
		URL url = getUrl();
		if(url==null) {
          return getActivitiesDefaultPage();
		}
		
		Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(url, LargeEstablishmentsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
				
		return circuitBreaker.run( () -> response.flatMap(largeStablismentsDto -> {

			List<ActivityInfoDto> listActivityInfoDto = buildActivityInfoDtoList(largeStablismentsDto);
					
			listActivityInfoDto = getDistinctByNameList(listActivityInfoDto); 

			ActivityInfoDto[] arrActivityInfoDto = listActivityInfoDto.toArray(new ActivityInfoDto[listActivityInfoDto.size()]);			            

			ActivityInfoDto[] pagedDto = JsonHelper.filterDto(arrActivityInfoDto, offset, limit);        
            
            genericActivityResultDto.setInfo(offset, limit, pagedDto.length, pagedDto);
			
			return Mono.just(genericActivityResultDto);
		}), throwable -> { return getActivitiesDefaultPage(); } );
				
	}    

	private List<ActivityInfoDto> buildActivityInfoDtoList(LargeEstablishmentsDto[] largeStablismentsDto) {
	  List<ActivityInfoDto> listActivityInfoDto = 
	          Arrays.stream (largeStablismentsDto)
                    .flatMap(largeStablismentDto -> largeStablismentDto.getClassifications_data().stream())
                    .filter (classificationDataDto -> filterClassificationDataDto(classificationDataDto))
                    .map    (classificationDataDto -> mapToActivityInfoDto(classificationDataDto))
                    .sorted (Comparator.comparing(ActivityInfoDto::getActivityName) )
                    .collect(Collectors.toList());
	  return listActivityInfoDto;
	}
		
	private boolean filterClassificationDataDto(ClassificationDataDto classificationDataDto) {
       if( (classificationDataDto.getFull_path()==null) ||
            ( (!classificationDataDto.getFull_path().toUpperCase().contains("MARQUES")) && 
              (!classificationDataDto.getFull_path().toUpperCase().contains("GESTIÓ BI")) &&
              (!classificationDataDto.getFull_path().toUpperCase().contains("ÚS INTERN")) ) )  {
    	   return true;
       }
       return false;
	}	
	
	private ActivityInfoDto mapToActivityInfoDto(ClassificationDataDto classificationDataDto) {
      return new ActivityInfoDto(classificationDataDto.getId(), 
                               ((classificationDataDto.getName()!=null)?classificationDataDto.getName():"") ); 
    }	
	
	private List<ActivityInfoDto> getDistinctByNameList(List<ActivityInfoDto> listActivityInfoDto)
	{ List<ActivityInfoDto> listDistinctActivityInfoDto = 
	     io.vavr.collection.List.ofAll(listActivityInfoDto)
                                .distinctBy((s1,s2) -> s1.getActivityName().compareToIgnoreCase(s2.getActivityName()))
                                .toJavaList();
      return listDistinctActivityInfoDto;
	}		
	
	private Mono<GenericResultDto<LargeEstablishmentsDto>> getPageDefault() {
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new LargeEstablishmentsDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}
	
	private URL getUrl() {
		URL url;
		try { 
			url = new URL(config.getDs_largeestablishments());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: "+e.getMessage());
			url = null;
		}
		return url;
	}	
	
	private Mono<GenericResultDto<ActivityInfoDto>> getActivitiesDefaultPage() {
		genericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);
		return Mono.just(genericActivityResultDto);
	}	
	
	
	
	
}