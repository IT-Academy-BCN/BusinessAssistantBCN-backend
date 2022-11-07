package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.SearchDTO;
import com.businessassistantbcn.opendata.dto.input.commercialgalleries.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.input.commercialgalleries.CommercialGalleriesDto;
import com.businessassistantbcn.opendata.dto.output.CommercialGalleriesResponseDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CommercialGalleriesService {

	private static final Logger log = LoggerFactory.getLogger(CommercialGalleriesService.class);

	@Autowired
	private HttpProxy httpProxy;
	@Autowired
	private PropertiesConfig config;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private GenericResultDto<CommercialGalleriesResponseDto> genericResultDto;
	@Autowired
	private GenericResultDto<ActivityInfoDto> genericActivityResultDto;

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnCommercialGalleriesDefaultPage")
	public Mono<GenericResultDto<CommercialGalleriesResponseDto>> getPage(int offset, int limit) throws MalformedURLException {
		
		return httpProxy.getRequestData(new URL(config.getDs_commercialgalleries()), CommercialGalleriesDto[].class)
			.flatMap(dtos -> {
				
				CommercialGalleriesDto[] filteredDto = Arrays.stream(dtos)
						.map(this::removeClassificationDataWithUsInternInFullPath)
						.toArray(CommercialGalleriesDto[]::new);
				CommercialGalleriesDto[] pagedDto = JsonHelper.filterDto(filteredDto, offset, limit);
				
				CommercialGalleriesResponseDto[] responseDto = Arrays.stream(pagedDto).map(this::mapToResponseDto).toArray(CommercialGalleriesResponseDto[]::new);
				
				genericResultDto.setInfo(offset, limit, filteredDto.length, responseDto);
				return Mono.just(genericResultDto);
			});
	}

	private CommercialGalleriesDto removeClassificationDataWithUsInternInFullPath(CommercialGalleriesDto commercialGalleriesDto) {
		List<ClassificationDataDto> classData = commercialGalleriesDto.getClassifications_data().stream()
				.filter(d -> !d.getFullPath().toUpperCase().contains("ÚS INTERN")).collect(Collectors.toList());
		commercialGalleriesDto.setClassifications_data(classData);
		return commercialGalleriesDto;
	}
	
	private CommercialGalleriesResponseDto mapToResponseDto(CommercialGalleriesDto commercialGalleriesDto) {
		CommercialGalleriesResponseDto responseDto = modelMapper.map(commercialGalleriesDto, CommercialGalleriesResponseDto.class);
		responseDto.setWeb(commercialGalleriesDto.getValues().getUrl_value());
		responseDto.setEmail(commercialGalleriesDto.getValues().getEmail_value());
		responseDto.setPhone(commercialGalleriesDto.getValues().getPhone_value());
		responseDto.setActivities(responseDto.mapClassificationDataListToActivityInfoList(commercialGalleriesDto.getClassifications_data()));
	    responseDto.setAddresses(responseDto.mapAddressesToCorrectLocation(commercialGalleriesDto.getAddresses(), commercialGalleriesDto.getCoordinates()));
		return responseDto;
	}

	@SuppressWarnings("unused")
	private Mono<GenericResultDto<CommercialGalleriesResponseDto>> logServerErrorReturnCommercialGalleriesDefaultPage(
		Throwable exception
	) {
		log.error("Opendata is down");
		return this.getCommercialGalleriesDefaultPage();
	}

	@SuppressWarnings("unused")
	private Mono<GenericResultDto<CommercialGalleriesResponseDto>> logInternalErrorReturnCommercialGalleriesDefaultPage(
		Throwable exception
	) {
		log.error("BusinessAssistant error: "+exception.getMessage());
		return this.getCommercialGalleriesDefaultPage();
	}

	private Mono<GenericResultDto<CommercialGalleriesResponseDto>> getCommercialGalleriesDefaultPage() {
		genericResultDto.setInfo(0, 0, 0, new CommercialGalleriesResponseDto[0]);
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
			});
	}

	public Mono<GenericResultDto<ActivityInfoDto>> getActivitiesDefaultPage() {
		genericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);
		return Mono.just(genericActivityResultDto);
	}

	private List<ActivityInfoDto> getListWithoutInvalidFullPaths(CommercialGalleriesDto[] dtos) {
		return Arrays.stream(dtos)
			.flatMap(commercialGalleryDto -> commercialGalleryDto.getClassifications_data().stream())
			.filter(this::isFullPathValid)
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

	@SuppressWarnings("unused")
	private Mono<GenericResultDto<ActivityInfoDto>> logServerErrorReturnActivitiesDefaultPage(Throwable exception) {
		log.error("Opendata is down");
		return this.getActivitiesDefaultPage();
	}

	@SuppressWarnings("unused")
	private Mono<GenericResultDto<ActivityInfoDto>> logInternalErrorReturnActivitiesDefaultPage(Throwable exception) {
		log.error("BusinessAssistant error: "+exception.getMessage());
		return this.getActivitiesDefaultPage();
	}

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logServerErrorReturnActivitiesDefaultPage")
	public Mono<GenericResultDto<CommercialGalleriesResponseDto>> getPageByActivity(int offset, int limit, String activityId)
			throws MalformedURLException {

		Predicate<CommercialGalleriesDto> dtoFilter = commercialGalleriesDto ->
				commercialGalleriesDto.getClassifications_data()
						.stream()
						.anyMatch(classificationsDataDto -> classificationsDataDto.getId() == Integer.parseInt(activityId));

		return getResultDto(offset, limit, dtoFilter);
	}

	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logServerErrorReturnActivitiesDefaultPage")
	public Mono<GenericResultDto<CommercialGalleriesResponseDto>> getPageByDistrict(int offset, int limit, int district)
			throws MalformedURLException {
		return getResultDto(offset, limit, dto ->
				dto.getAddresses().stream().anyMatch(a ->
						Integer.parseInt(a.getDistrict_id()) == district
				));
	}

	// Get paged results filtered by search parameters (zones and activities)
	@CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnCommercialGalleriesDefaultPage")
	public Mono<GenericResultDto<CommercialGalleriesResponseDto>> getPageBySearch(int offset, int limit, SearchDTO searchParams)
			throws MalformedURLException {

		Predicate<CommercialGalleriesDto> activityFilter;
		if (searchParams.getActivities().length > 0) {
			activityFilter = commercialGalleriesDto ->
					commercialGalleriesDto.getClassifications_data()
							.stream()
							.anyMatch(classificationsDataDto ->
									Arrays.stream(searchParams.getActivities())
											.anyMatch(activityId -> activityId == classificationsDataDto.getId()));
		} else {
			activityFilter = commercialGalleriesDto -> true;
		}

		Predicate<CommercialGalleriesDto> zoneFilter;
		if (searchParams.getZones().length > 0) {
			zoneFilter = commercialGalleriesDto ->
					commercialGalleriesDto.getAddresses()
							.stream()
							.anyMatch(address ->
									Arrays.stream(searchParams.getZones())
											.anyMatch(zoneId -> zoneId == Integer.parseInt(address.getDistrict_id())));
		} else {
			zoneFilter = commercialGalleriesDto -> true;
		}

		return getResultDto(offset, limit, activityFilter.and(zoneFilter));
	}

	private Mono<GenericResultDto<CommercialGalleriesResponseDto>> getResultDto(
			int offset, int limit, Predicate<CommercialGalleriesDto> dtoFilter) throws MalformedURLException {
		return 	httpProxy.getRequestData(new URL(config.getDs_commercialgalleries()), CommercialGalleriesDto[].class)
				.flatMap(commercialGalleriesDtos -> {
					CommercialGalleriesDto[] fullDto = Arrays.stream(commercialGalleriesDtos)
							.map(this::removeClassificationDataWithUsInternInFullPath)
							.toArray(CommercialGalleriesDto[]::new);

					CommercialGalleriesDto[] filteredDto = Arrays.stream(fullDto)
							.filter(dtoFilter)
							.toArray(CommercialGalleriesDto[]::new);

					CommercialGalleriesDto[] pagedDto = JsonHelper.filterDto(filteredDto, offset, limit);

					CommercialGalleriesResponseDto[] responseDto = Arrays.stream(pagedDto).map(this::mapToResponseDto).toArray(CommercialGalleriesResponseDto[]::new);

					genericResultDto.setInfo(offset, limit, fullDto.length, responseDto);
					return Mono.just(genericResultDto);
				});
	}
}
