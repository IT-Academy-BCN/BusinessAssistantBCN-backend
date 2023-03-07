package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.SearchDTO;
import com.businessassistantbcn.opendata.dto.input.largeestablishments.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.input.largeestablishments.LargeEstablishmentsDto;
import com.businessassistantbcn.opendata.dto.output.LargeEstablishmentsResponseDto;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.proxy.HttpProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
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
    public Mono<GenericResultDto<LargeEstablishmentsResponseDto>> getPage(int offset, int limit) {
        return getResultDto(offset, limit, dto -> true);
    }

    // Get paged results filtered by district
    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnLargeEstablishmentsDefaultPage")
    public Mono<GenericResultDto<LargeEstablishmentsResponseDto>> getPageByDistrict(int offset, int limit, int district) {
        return getResultDto(offset, limit, dto ->
                dto.getAddresses().stream()
                        .anyMatch(a -> Integer.parseInt(a.getDistrict_id()) == district
                        ));
    }

    // Get paged results filtered by activity
    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnLargeEstablishmentsDefaultPage")
    public Mono<GenericResultDto<LargeEstablishmentsResponseDto>> getPageByActivity(int offset, int limit, String activityId) {

        Predicate<LargeEstablishmentsDto> dtoFilter = largeEstablishmentsDto ->
                largeEstablishmentsDto.getClassifications_data()
                        .stream()
                        .anyMatch(classificationsDataDto -> classificationsDataDto.getId() == Integer.parseInt(activityId));

        return getResultDto(offset, limit, dtoFilter);
    }

    // Get paged results filtered by search parameters (zones and activities)
    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnLargeEstablishmentsDefaultPage")
    public Mono<GenericResultDto<LargeEstablishmentsResponseDto>> getPageBySearch(int offset, int limit, SearchDTO searchParams) {

        Predicate<LargeEstablishmentsDto> activityFilter;
        if (searchParams.getActivities().length > 0) {
            activityFilter = largeEstablishmentsDto ->
                    largeEstablishmentsDto.getClassifications_data()
                            .stream()
                            .anyMatch(classificationsDataDto ->
                                    Arrays.stream(searchParams.getActivities())
                                            .anyMatch(activityId -> activityId == classificationsDataDto.getId()));
        } else {
            activityFilter = largeEstablishmentsDto -> true;
        }
        Predicate<LargeEstablishmentsDto> zoneFilter;
        if (searchParams.getZones().length > 0) {
            zoneFilter = largeEstablishmentsDto ->
                    largeEstablishmentsDto.getAddresses()
                            .stream()
                            .anyMatch(address ->
                                    Arrays.stream(searchParams.getZones())
                                            .anyMatch(zoneId -> zoneId == Integer.parseInt(address.getDistrict_id())));
        } else {
            zoneFilter = largeEstablishmentsDto -> true;
        }

        return getResultDto(offset, limit, activityFilter.and(zoneFilter));
    }

    private Mono<GenericResultDto<LargeEstablishmentsResponseDto>> getResultDto(
            int offset, int limit, Predicate<LargeEstablishmentsDto> dtoFilter) {
        return httpProxy.getRequestData(URI.create(config.getDs_largeestablishments()), LargeEstablishmentsDto[].class)
                .flatMap(largeEstablishmentsDto -> {
                    LargeEstablishmentsDto[] fullDto = Arrays.stream(largeEstablishmentsDto)
                            .map(this::removeClassificationDataWithMarquesAndUsInternInFullPath)
                            .toArray(LargeEstablishmentsDto[]::new);

                    LargeEstablishmentsDto[] filteredDto = Arrays.stream(fullDto)
                            .filter(dtoFilter)
                            .toArray(LargeEstablishmentsDto[]::new);

                    LargeEstablishmentsDto[] pagedDto = JsonHelper.filterDto(filteredDto, offset, limit);

                    LargeEstablishmentsResponseDto[] responseDto = Arrays.stream(pagedDto).map(this::convertToDto).toArray(LargeEstablishmentsResponseDto[]::new);

                    genericResultDto.setInfo(offset, limit, responseDto.length, responseDto);
                    return Mono.just(genericResultDto);
                }).switchIfEmpty(getLargeEstablishmentsDefaultPage());
    }

    private LargeEstablishmentsDto removeClassificationDataWithMarquesAndUsInternInFullPath(LargeEstablishmentsDto largeEstablishmentDto) {
        List<ClassificationDataDto> classData = largeEstablishmentDto.getClassifications_data().stream()
                .filter(d -> !d.getFullPath().toUpperCase().contains("MARQUES"))
                .filter(d -> !d.getFullPath().toUpperCase().contains("ÚS INTERN"))
                .collect(Collectors.toList());
        largeEstablishmentDto.setClassifications_data(classData);
        return largeEstablishmentDto;
    }

    private LargeEstablishmentsResponseDto convertToDto(LargeEstablishmentsDto largeEstablishmentsDto) {
        LargeEstablishmentsResponseDto responseDto = modelMapper.map(largeEstablishmentsDto, LargeEstablishmentsResponseDto.class);
        responseDto.setWeb(largeEstablishmentsDto.getValues().getUrl_value());
        responseDto.setEmail(largeEstablishmentsDto.getValues().getEmail_value());
        responseDto.setPhone(largeEstablishmentsDto.getValues().getPhone_value());
        responseDto.setActivities(responseDto.mapClassificationDataListToActivityInfoList(largeEstablishmentsDto.getClassifications_data()));
        responseDto.setAddresses(responseDto.mapAddressesToCorrectLocation(largeEstablishmentsDto.getAddresses(), largeEstablishmentsDto.getCoordinates()));
        return responseDto;
    }

    @SuppressWarnings("unused")
    private Mono<GenericResultDto<LargeEstablishmentsResponseDto>> logServerErrorReturnLargeEstablishmentsDefaultPage(Throwable exception) {
        log.error("Opendata is down");
        return this.getLargeEstablishmentsDefaultPage();
    }

    @SuppressWarnings("unused")
    private Mono<GenericResultDto<LargeEstablishmentsResponseDto>> logInternalErrorReturnLargeEstablishmentsDefaultPage(Throwable exception) {
        log.error("BusinessAssistant error: " + exception.getMessage());
        return this.getLargeEstablishmentsDefaultPage();
    }

    private Mono<GenericResultDto<LargeEstablishmentsResponseDto>> getLargeEstablishmentsDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new LargeEstablishmentsResponseDto[0]);
        return Mono.just(genericResultDto);
    }

    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnActivitiesDefaultPage")
    public Mono<GenericResultDto<ActivityInfoDto>> getLargeEstablishmentsActivities(int offset, int limit) {
        return httpProxy.getRequestData(URI.create(config.getDs_largeestablishments()), LargeEstablishmentsDto[].class)
                .flatMap(largeEstablishmentDto -> {
                    List<ActivityInfoDto> listFullPathFiltered = this.getListWithoutInvalidFullPaths(largeEstablishmentDto);
                    List<ActivityInfoDto> listActivityInfoDto = this.getListWithoutRepeatedNames(listFullPathFiltered);
                    ActivityInfoDto[] activityInfoDto =
                            listActivityInfoDto.toArray(new ActivityInfoDto[listActivityInfoDto.size()]);

                    ActivityInfoDto[] pagedDto = JsonHelper.filterDto(activityInfoDto, offset, limit);
                    genericActivityResultDto.setInfo(offset, limit, pagedDto.length, pagedDto);
                    return Mono.just(genericActivityResultDto);
                }).switchIfEmpty(getActivitiesDefaultPage());
    }

    private Mono<GenericResultDto<ActivityInfoDto>> getActivitiesDefaultPage() {
        genericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);
        return Mono.just(genericActivityResultDto);
    }

    private List<ActivityInfoDto> getListWithoutInvalidFullPaths(LargeEstablishmentsDto[] largeEstablishmentsDto) {
        return Arrays.stream(largeEstablishmentsDto)
                .flatMap(largeEstablishmentDto -> largeEstablishmentDto.getClassifications_data().stream())
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
        return !(dto.getFullPath() == null ||
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
        log.error("BusinessAssistant error: " + exception.getMessage());
        return this.getActivitiesDefaultPage();
    }
}