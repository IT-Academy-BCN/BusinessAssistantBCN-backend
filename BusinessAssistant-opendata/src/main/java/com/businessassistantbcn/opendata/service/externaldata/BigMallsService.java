package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.SearchRequestDto;
import com.businessassistantbcn.opendata.dto.input.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.input.bigmalls.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.output.BigMallsResponseDto;
import com.businessassistantbcn.opendata.exception.OpendataUnavailableServiceException;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.netty.handler.timeout.ReadTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BigMallsService {

	private static final Logger log = LoggerFactory.getLogger(BigMallsService.class);

	@Autowired
	private PropertiesConfig config;
	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private GenericResultDto<BigMallsResponseDto> genericResultDto;
	@Autowired
	private GenericResultDto<ActivityInfoDto> genericActivityResultDto;

//	@CircuitBreaker(name = "opendataService", fallbackMethod = "logInternalErrorReturnBigMallsDefaultPage")
//	public Mono<GenericResultDto<BigMallsResponseDto>>getPage(int offset, int limit) throws MalformedURLException {
//		return httpProxy.getRequestData(new URL(config.getDs_bigmalls()), BigMallsDto[].class)
//			.flatMap(dtos -> {
//				BigMallsDto[] filteredDto = Arrays.stream(dtos)
//						.map(d -> this.removeClassificationDataWithUsInternInFullPath(d))
//						.toArray(BigMallsDto[]::new);
//				BigMallsDto[] pagedDto = JsonHelper.filterDto(filteredDto, offset, limit);
//				
//				BigMallsResponseDto[] responseDto = Arrays.stream(pagedDto).map(p -> mapToResponseDto(p)).toArray(BigMallsResponseDto[]::new);
//				
//				genericResultDto.setInfo(offset, limit, responseDto.length, responseDto);
//				return Mono.just(genericResultDto);
//			})
//			.onErrorResume(e -> this.logServerErrorReturnBigMallsDefaultPage(new OpendataUnavailableServiceException()));
//	}
	
//	@CircuitBreaker(name = "opendataService") //, fallbackMethod = "logInternalErrorReturnBigMallsDefaultPage")
//	public Mono<String>getPage(int offset, int limit){
//		Mono<String> response = null;
//		try {
//			response = httpProxy.getRequestData(new URL("http://httpbin.org/status/500"), String.class);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return response;
//	}
	
//*******************************************
	
	//funciona perfectamente si te conectas directamente a la api externa
	//cuando ésta está desconectada salta el fallbackmethod ok
	@CircuitBreaker(name = "opendataService",fallbackMethod = "fallbackMethod_NoProxy")
    public Mono<String> testCircuitBreaker_Sin_Proxy() {
        String url = "http://localhost:9191/orders/test-circuitbreaker";
        RestTemplate response = new RestTemplate();
        return Mono.just(response.getForObject(url, String.class));
    }
	
	public Mono<String> fallbackMethod_NoProxy(Exception e){
		return Mono.just("Este es el fallback method SIN PROXY!");
	}
	
	//PARA PROBAR EL TIMELIMITER 
	//en la api del puerto 9191 puse un Thread.sleep(3000) (3s>2s del timelimter)
	//como esa api devuelve un String, el timelimiter da error pq necesita un return de tipo reactive o completableFuture
	@CircuitBreaker(name = "opendataService",fallbackMethod = "fallbackMethod_NoProxy_Delay")
	@TimeLimiter(name = "opendataService",fallbackMethod = "fallbackMethod_NoProxy_Delay")
    public Mono<String> testCircuitBreaker_Sin_Proxy_Con_Delay() {
        String url = "http://localhost:9191/orders/test-circuitbreaker-delay";
        RestTemplate response = new RestTemplate();
        return Mono.just(response.getForObject(url, String.class));
    }
	
	public Mono<String> fallbackMethod_NoProxy_Delay(Exception e){
		return Mono.just("Este es el fallback method SIN PROXY y con DELAY!");
	}
	
	//Cuando la api externa está desconectada da una excepción que el circuitbreaker no recoge, 
	//por eso en opendata se estaba controlando esto sin el circuitbreaker (usando .onErrorResume)
	@CircuitBreaker(name = "opendataService",fallbackMethod = "fallbackMethod_Proxy")
    public Mono<String> testCircuitBreaker_Proxy() throws MalformedURLException{
//		Mono<String> response = null;
//		try {
//			response = httpProxy.getRequestData(new URL("http://localhost:9191/orders/test-circuitbreaker-proxy"), String.class);
//		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return response;
		return httpProxy.getRequestData(new URL("http://localhost:9191/orders/test-circuitbreaker-proxy"), String.class);
				//.onErrorResume(e -> this.fallbackMethod_Proxy(e));
    }
	
	private Mono<String> fallbackMethod_Proxy(Throwable e) {
		return Mono.just("Este es el fallback method CON PROXY cuando se controla el error, aún no ha saltado el circuitbreaker-creo!");
	}

	public Mono<String> fallbackMethod_Proxy(Exception e){
		return Mono.just("Este es el fallback method CON PROXY!");
	}
	
	//cuando se conecta a la api externa y ésta tarda en responder (usando un Thread.sleep) NO FUNCIONA CIRCUIT BREAKER
	//en vez de el fallbackMethod devuelve un 500 y en la consola da esta info:
	//2022-06-01 10:48:01.689 ERROR 10548 --- [nio-8762-exec-3] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] threw exception
		//io.netty.handler.timeout.ReadTimeoutException: null
	@CircuitBreaker(name = "opendataService",fallbackMethod = "fallbackMethod_Proxy_Delay")
    public Mono<String> testCircuitBreaker_Proxy_y_delay() throws MalformedURLException {
		Mono<String> response = null;
		response = httpProxy.getRequestData(new URL("http://localhost:9191/orders/test-circuitbreaker-proxy-y-delay"), String.class);
		return response;
    }
	
	public Mono<String> fallbackMethod_Proxy_Delay(Exception e){
		return Mono.just("Este es el fallback method CON PROXY y Delay superior al timeout!!!\n Ha saltado el circuit breaker, lo que tenía que ocurrir :)");
	}
//*****************************************
	
	private BigMallsDto removeClassificationDataWithUsInternInFullPath(BigMallsDto bigMallsDto) {
		List<ClassificationDataDto> classData = bigMallsDto.getClassifications_data().stream()
				.filter(d -> !d.getFullPath().toUpperCase().contains("ÚS INTERN")).collect(Collectors.toList());
		bigMallsDto.setClassifications_data(classData);
		return bigMallsDto;
	}
	
	private BigMallsResponseDto mapToResponseDto(BigMallsDto bigMallsDto) {
		BigMallsResponseDto responseDto = modelMapper.map(bigMallsDto, BigMallsResponseDto.class);
		responseDto.setValue(bigMallsDto.getValues());
		responseDto.setActivities(responseDto.mapClassificationDataListToActivityInfoList(bigMallsDto.getClassifications_data()));
	    return responseDto;
	}

	private Mono<GenericResultDto<BigMallsResponseDto>> logServerErrorReturnBigMallsDefaultPage(Exception exception) {
		log.error("Opendata is down");
		return this.getBigMallsDefaultPage(exception);
	}

	private Mono<String> logInternalErrorReturnBigMallsDefaultPage(Exception exception) {
		log.error("BusinessAssistant error: "+exception.getMessage());
		return Mono.just(exception.getMessage());
	}

	private Mono<GenericResultDto<BigMallsResponseDto>> getBigMallsDefaultPage(Exception exception) {
		genericResultDto.setInfo(0, 0, 0, new BigMallsResponseDto[0]);
		return Mono.just(genericResultDto);
	}

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnActivitiesDefaultPage")
	public Mono<GenericResultDto<ActivityInfoDto>> getBigMallsActivities(int offset, int limit) throws MalformedURLException {
		return httpProxy.getRequestData(new URL(config.getDs_bigmalls()), BigMallsDto[].class)
			.flatMap(bigMallsDto -> {
				List<ActivityInfoDto> listFullPathFiltered = this.getListWithoutInvalidFullPaths(bigMallsDto);
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

	private List<ActivityInfoDto> getListWithoutInvalidFullPaths(BigMallsDto[] bigMallsDto) {
		return Arrays.stream(bigMallsDto)
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

	public GenericResultDto<BigMallsDto> getBigMallsByActivityDto(int[] activities, int offset, int limit) {
		//lambda filter
		return null;
	}

	public GenericResultDto<BigMallsDto> getBigMallsByDistrictDto(int[] districts, int offset, int limit) {
		//lambda filter
		return null;
	}

	public String getBigMallsByActivity(int[] activities, int offset, int limit) {
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

	public String getBigMallsByDistrict(int[] districts, int offset, int limit) {
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

