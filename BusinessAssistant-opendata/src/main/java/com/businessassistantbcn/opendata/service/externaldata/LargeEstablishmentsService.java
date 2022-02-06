package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.largeestablishments.LargeEstablishmentsDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;

import com.businessassistantbcn.opendata.proxy.HttpProxy;

import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


@Service
public class LargeEstablishmentsService {

	@Autowired
	HttpProxy httpProxy;
	
	@Autowired
	private PropertiesConfig config;
	
	@Autowired
	private GenericResultDto<LargeEstablishmentsDto> genericResultDto;
	
	@Autowired
	private GenericResultDto<ActivityInfoDto> genericActivityResultDto;
	
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Mono<GenericResultDto<LargeEstablishmentsDto>>getPageByDistrict(int offset, int limit, String district) {
		

		URL url;


		try {
			url = new URL(config.getDs_largeestablishments());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: " + e.getMessage());
			return getPageDefault();
		}

		Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(url, LargeEstablishmentsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

		return circuitBreaker.run(() -> response.flatMap(largeStablismentsDto -> {

			LargeEstablishmentsDto[] largeStablismentsDtoByDistrict = Arrays.stream(largeStablismentsDto)
					.filter(dto -> dto.getAddresses().stream().anyMatch(address -> true))
					.toArray(LargeEstablishmentsDto[]::new);

			// String key = "district_id";
			// LargeStablishmentsDto[] dtoByDsitrictId = getArrayDtoByKeyAddress(dto, key,
			// district);

			LargeEstablishmentsDto[] pagedDto = JsonHelper.filterDto(largeStablismentsDtoByDistrict, offset, limit);
			
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(pagedDto);
			genericResultDto.setCount(largeStablismentsDtoByDistrict.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());
	}
	
	public Mono<GenericResultDto<LargeEstablishmentsDto>>getPageByActivity(int offset, int limit, String activity) {
		
		log.info("Activity id: " + activity);
		
		URL url;

		try {
			url = new URL(config.getDs_largeestablishments());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: " + e.getMessage());
			return getPageDefault();
		}

		Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(url, LargeEstablishmentsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
		
		return circuitBreaker.run(() -> response.flatMap(largeEstablismentsDto -> {
			LargeEstablishmentsDto[] pagedDto = JsonHelper.filterDto(largeEstablismentsDto,offset,limit);
			
			genericResultDto.setLimit(limit);
			genericResultDto.setOffset(offset);
			genericResultDto.setResults(pagedDto);
			genericResultDto.setCount(largeEstablismentsDto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());
			
		}
		

	
    public Mono<GenericResultDto<LargeEstablishmentsDto>> getLargeEstablishmentsAll()

	{
    	
    	URL url;
		try {
			url = new URL(config.getDs_largeestablishments());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: " + e.getMessage());
			return getPageDefault();
		}
		
		Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(url, LargeEstablishmentsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
		
		return circuitBreaker.run(() -> response.flatMap(dto -> {

			genericResultDto.setResults(dto);
			genericResultDto.setCount(dto.length);
			return Mono.just(genericResultDto);
		}), throwable -> getPageDefault());
	}
	

    
	public Mono<GenericResultDto<ActivityInfoDto>>getActivities(int offset, int limit) {
		URL url;
		try {
			url = new URL(config.getDs_largeestablishments());
		} catch (MalformedURLException e) {
			log.error("URL bad configured: " + e.getMessage());
			return getActivityInfoDefaultPage();
		}
		
		Mono<LargeEstablishmentsDto[]> response = httpProxy.getRequestData(url, LargeEstablishmentsDto[].class);
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

		return circuitBreaker.run( () -> response.flatMap(largeStablismentsDto -> {
            ActivityInfoDto[] arrActivityInfoDto = 
                  Arrays.stream(largeStablismentsDto)
                        .flatMap( o -> o.getClassifications_data().stream() )
                        .filter( o -> (o.getFull_path()==null)||((o.getFull_path()!=null)&&(!o.getFull_path().toUpperCase().contains("MARQUES"))) )
                        .map( o -> { return new ActivityInfoDto(o.getId(), ((o.getName()!=null)?o.getName():"") ); } )
                        .sorted( (o1, o2) -> o1.getActivityName().compareTo(o2.getActivityName()) )
                        .distinct()
                        .toArray(ActivityInfoDto[]::new);
            ActivityInfoDto[] pagedDto = JsonHelper.filterDto(arrActivityInfoDto, offset, limit);
            
			genericActivityResultDto.setLimit(limit);
			genericActivityResultDto.setOffset(offset);
			genericActivityResultDto.setResults(pagedDto);
			genericActivityResultDto.setCount(pagedDto.length);
			return Mono.just(genericActivityResultDto);
		}), throwable -> getActivityInfoDefaultPage());
				
	}    
    
    
    
    private Mono<GenericResultDto<LargeEstablishmentsDto>> getPageDefault() {
		genericResultDto.setLimit(0);
		genericResultDto.setOffset(0);
		genericResultDto.setResults(new LargeEstablishmentsDto[0]);
		genericResultDto.setCount(0);
		return Mono.just(genericResultDto);
	}
    
    private Mono<GenericResultDto<ActivityInfoDto>> getActivityInfoDefaultPage() {
    	genericActivityResultDto.setLimit(0);
    	genericActivityResultDto.setOffset(0);
    	genericActivityResultDto.setResults(new ActivityInfoDto[0]);
    	genericActivityResultDto.setCount(0);
		return Mono.just(genericActivityResultDto);
	}
}
