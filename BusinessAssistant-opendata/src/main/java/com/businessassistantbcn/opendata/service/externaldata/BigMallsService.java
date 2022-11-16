package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.SearchDTO;
import com.businessassistantbcn.opendata.dto.input.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.input.bigmalls.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.output.BigMallsResponseDto;
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

    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnBigMallsDefaultPage")
    public Mono<GenericResultDto<BigMallsResponseDto>> getPage(int offset, int limit) {
        return httpProxy.getRequestData(URI.create(config.getDs_bigmalls()), BigMallsDto[].class)
                .flatMap(dtos -> {
                    BigMallsDto[] filteredDto = Arrays.stream(dtos)
                            .map(this::removeClassificationDataWithUsInternInFullPath)
                            .toArray(BigMallsDto[]::new);

                    BigMallsDto[] pagedDto = JsonHelper.filterDto(filteredDto, offset, limit);

                    BigMallsResponseDto[] responseDto = Arrays.stream(pagedDto).map(this::mapToResponseDto).toArray(BigMallsResponseDto[]::new);

                    genericResultDto.setInfo(offset, limit, filteredDto.length, responseDto);
                    return Mono.just(genericResultDto);
                }).switchIfEmpty(getBigMallsDefaultPage());
    }

    private BigMallsDto removeClassificationDataWithUsInternInFullPath(BigMallsDto bigMallsDto) {
        List<ClassificationDataDto> classData = bigMallsDto.getClassifications_data().stream()
                .filter(d -> !d.getFullPath().toUpperCase().contains("ÚS INTERN")).collect(Collectors.toList());
        bigMallsDto.setClassifications_data(classData);
        return bigMallsDto;
    }

    private BigMallsResponseDto mapToResponseDto(BigMallsDto bigMallsDto) {
        BigMallsResponseDto responseDto = modelMapper.map(bigMallsDto, BigMallsResponseDto.class);
        responseDto.setWeb(bigMallsDto.getValues().getUrl_value());
        responseDto.setEmail(bigMallsDto.getValues().getEmail_value());
        responseDto.setPhone(bigMallsDto.getValues().getPhone_value());
        responseDto.setActivities(responseDto.mapClassificationDataListToActivityInfoList(bigMallsDto.getClassifications_data()));
        responseDto.setAddresses(responseDto.mapAddressesToCorrectLocation(bigMallsDto.getAddresses(), bigMallsDto.getCoordinates()));
        return responseDto;
    }

    @SuppressWarnings("unused")
    private Mono<GenericResultDto<BigMallsResponseDto>> logServerErrorReturnBigMallsDefaultPage(Throwable exception) {
        log.error("Opendata is down");
        return this.getBigMallsDefaultPage();
    }

    @SuppressWarnings("unused")
    private Mono<GenericResultDto<BigMallsResponseDto>> logInternalErrorReturnBigMallsDefaultPage(Throwable exception) {
        log.error("BusinessAssistant error: " + exception.getMessage());
        return this.getBigMallsDefaultPage();
    }

    private Mono<GenericResultDto<BigMallsResponseDto>> getBigMallsDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new BigMallsResponseDto[0]);
        return Mono.just(genericResultDto);
    }

    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnActivitiesDefaultPage")
    public Mono<GenericResultDto<ActivityInfoDto>> getBigMallsActivities(int offset, int limit) {
        return httpProxy.getRequestData(URI.create(config.getDs_bigmalls()), BigMallsDto[].class)
                .flatMap(bigMallsDto -> {
                    List<ActivityInfoDto> listFullPathFiltered = this.getListWithoutInvalidFullPaths(bigMallsDto);
                    List<ActivityInfoDto> listActivityInfoDto = this.getListWithoutRepeatedNames(listFullPathFiltered);
                    ActivityInfoDto[] activityInfoDto =
                            listActivityInfoDto.toArray(new ActivityInfoDto[listActivityInfoDto.size()]);

                    ActivityInfoDto[] pagedDto = JsonHelper.filterDto(activityInfoDto, offset, limit);
                    genericActivityResultDto.setInfo(offset, limit, activityInfoDto.length, pagedDto);
                    return Mono.just(genericActivityResultDto);
                }).switchIfEmpty(getActivitiesDefaultPage());
    }

    private Mono<GenericResultDto<ActivityInfoDto>> getActivitiesDefaultPage() {
        genericActivityResultDto.setInfo(0, 0, 0, new ActivityInfoDto[0]);
        return Mono.just(genericActivityResultDto);
    }

    private List<ActivityInfoDto> getListWithoutInvalidFullPaths(BigMallsDto[] bigMallsDto) {
        return Arrays.stream(bigMallsDto)
                .flatMap(bigMallDto -> bigMallDto.getClassifications_data().stream())
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

    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logServerErrorReturnActivitiesDefaultPage")
    public Mono<GenericResultDto<BigMallsResponseDto>> getPageByActivity(int offset, int limit, String activityId) {

        Predicate<BigMallsDto> dtoFilter = bigMallsDto ->
                bigMallsDto.getClassifications_data()
                        .stream()
                        .anyMatch(classificationsDataDto -> classificationsDataDto.getId() == Integer.parseInt(activityId));

        return getResultDto(offset, limit, dtoFilter);
    }

    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logServerErrorReturnActivitiesDefaultPage")
    public Mono<GenericResultDto<BigMallsResponseDto>> getPageByDistrict(int offset, int limit, int district) {
        return getResultDto(offset, limit, dto ->
                dto.getAddresses().stream().anyMatch(a ->
                        Integer.parseInt(a.getDistrict_id()) == district
                ));
    }

    // Get paged results filtered by search parameters (zones and activities)
    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnBigMallsDefaultPage")
    public Mono<GenericResultDto<BigMallsResponseDto>> getPageBySearch(int offset, int limit, SearchDTO searchParams) {

        Predicate<BigMallsDto> activityFilter;
        if (searchParams.getActivities().length > 0) {
            activityFilter = bigMallsDto ->
                    bigMallsDto.getClassifications_data()
                            .stream()
                            .anyMatch(classificationsDataDto ->
                                    Arrays.stream(searchParams.getActivities())
                                            .anyMatch(activityId -> activityId == classificationsDataDto.getId()));
        } else {
            activityFilter = bigMallsDto -> true;
        }

        Predicate<BigMallsDto> zoneFilter;
        if (searchParams.getZones().length > 0) {
            zoneFilter = bigMallsDto ->
                    bigMallsDto.getAddresses()
                            .stream()
                            .anyMatch(address ->
                                    Arrays.stream(searchParams.getZones())
                                            .anyMatch(zoneId -> zoneId == Integer.parseInt(address.getDistrict_id())));
        } else {
            zoneFilter = bigMallsDto -> true;
        }

        return getResultDto(offset, limit, activityFilter.and(zoneFilter));
    }

    private Mono<GenericResultDto<BigMallsResponseDto>> getResultDto(
            int offset, int limit, Predicate<BigMallsDto> dtoFilter) {
        return httpProxy.getRequestData(URI.create(config.getDs_bigmalls()), BigMallsDto[].class)
                .flatMap(bigMallsDto -> {
                    BigMallsDto[] fullDto = Arrays.stream(bigMallsDto)
                            .map(this::removeClassificationDataWithUsInternInFullPath)
                            .toArray(BigMallsDto[]::new);

                    BigMallsDto[] filteredDto = Arrays.stream(fullDto)
                            .filter(dtoFilter)
                            .toArray(BigMallsDto[]::new);

                    BigMallsDto[] pagedDto = JsonHelper.filterDto(filteredDto, offset, limit);

                    BigMallsResponseDto[] responseDto = Arrays.stream(pagedDto).map(this::mapToResponseDto).toArray(BigMallsResponseDto[]::new);

                    genericResultDto.setInfo(offset, limit, fullDto.length, responseDto);
                    return Mono.just(genericResultDto);
                }).switchIfEmpty(getBigMallsDefaultPage());
    }
}

