package com.businessassistantbcn.opendata.service.externaldata;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.marketfairs.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.input.marketfairs.MarketFairsDto;
import com.businessassistantbcn.opendata.dto.input.marketfairs.MarketFairsSearchDto;
import com.businessassistantbcn.opendata.dto.output.MarketFairsResponseDto;
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
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MarketFairsService {

    private static final Logger log = LoggerFactory.getLogger(MarketFairsService.class);

    @Autowired
    private PropertiesConfig config;
    @Autowired
    private HttpProxy httpProxy;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GenericResultDto<MarketFairsResponseDto> genericResultDto;

    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnMarketFairsDefaultPage")
    public Mono<GenericResultDto<MarketFairsResponseDto>> getPage(int offset, int limit) {
        return httpProxy.getRequestData(URI.create(config.getDs_marketfairs()), MarketFairsDto[].class)
                .flatMap(dtos -> {
                    MarketFairsDto[] filterDto = Arrays.stream(dtos)
                            .map(this::removeClassificationDataWithUsInternInFullPath)
                            .toArray(MarketFairsDto[]::new);

                    MarketFairsDto[] pagedDto = JsonHelper.filterDto(filterDto, offset, limit);
                    MarketFairsResponseDto[] responseDto = Arrays.stream(pagedDto).map(this::convertToDto).toArray(MarketFairsResponseDto[]::new);
                    genericResultDto.setInfo(offset, limit, filterDto.length, responseDto);

                    return Mono.just(genericResultDto);
                }).switchIfEmpty(getMarketFairsDefaultPage());
    }

    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnMarketFairsDefaultPage")
    public Mono<GenericResultDto<MarketFairsResponseDto>> getPageByDistrict(int offset, int limit, int district) {
        return getResultDto(offset, limit, dto ->
                dto.getAddresses().stream().anyMatch(a ->
                        Integer.parseInt(a.getDistrict_id()) == district
                ));
    }

    // Get paged results filtered by search parameters (zones and activities)
    @CircuitBreaker(name = "circuitBreaker", fallbackMethod = "logInternalErrorReturnMarketFairsDefaultPage")
    public Mono<GenericResultDto<MarketFairsResponseDto>> getPageBySearch(int offset, int limit, MarketFairsSearchDto searchParams) {

        Predicate<MarketFairsDto> zoneFilter;
        if (searchParams.getZones().length > 0) {
            zoneFilter = marketFairsDto ->
                    marketFairsDto.getAddresses()
                            .stream()
                            .anyMatch(address ->
                                    Arrays.stream(searchParams.getZones())
                                            .anyMatch(zoneId -> zoneId == Integer.parseInt(address.getDistrict_id())));
        } else {
            zoneFilter = marketFairsDto -> true;
        }

        return getResultDto(offset, limit, zoneFilter);
    }

    private Mono<GenericResultDto<MarketFairsResponseDto>> getResultDto(
            int offset, int limit, Predicate<MarketFairsDto> dtoFilter) {
        return httpProxy.getRequestData(URI.create(config.getDs_marketfairs()), MarketFairsDto[].class)
                .flatMap(marketFairsDto -> {
                    MarketFairsDto[] fullDto = Arrays.stream(marketFairsDto)
                            .toArray(MarketFairsDto[]::new);

                    MarketFairsDto[] filteredDto = Arrays.stream(fullDto)
                            .filter(dtoFilter)
                            .toArray(MarketFairsDto[]::new);

                    MarketFairsDto[] pagedDto = JsonHelper.filterDto(filteredDto, offset, limit);

                    MarketFairsResponseDto[] responseDto = Arrays.stream(pagedDto).map(this::convertToDto).toArray(MarketFairsResponseDto[]::new);

                    genericResultDto.setInfo(offset, limit, fullDto.length, responseDto);
                    return Mono.just(genericResultDto);
                }).switchIfEmpty(getMarketFairsDefaultPage());
    }

    private MarketFairsDto removeClassificationDataWithUsInternInFullPath(MarketFairsDto marketFairsDto) {
        List<ClassificationDataDto> cassData = marketFairsDto.getClassifications_data().stream()
                .filter(d -> !d.getName().toUpperCase().contains("ÚS INTERN"))
                .collect(Collectors.toList());
        marketFairsDto.setClassifications_data(cassData);
        return marketFairsDto;
    }

    private MarketFairsResponseDto convertToDto(MarketFairsDto marketFairsDto) {

        MarketFairsResponseDto responseDto = modelMapper.map(marketFairsDto, MarketFairsResponseDto.class);
        responseDto.setWeb(marketFairsDto.getValues().getUrl_value());
        responseDto.setEmail(marketFairsDto.getValues().getEmail_value());
        responseDto.setPhone(marketFairsDto.getValues().getPhone_value());
        responseDto.setActivities(responseDto.mapClassificationDataListToActivityInfoList(marketFairsDto.getClassifications_data()));
        responseDto.setAddresses(responseDto.mapAddressesToCorrectLocation(marketFairsDto.getAddresses(), marketFairsDto.getCoordinates()));
        return responseDto;
    }

    @SuppressWarnings("unused")
    private Mono<GenericResultDto<MarketFairsResponseDto>> logServerErrorReturnMarketFairsDefaultPage(Throwable exception) {
        log.error("Opendata is down");
        return this.getMarketFairsDefaultPage();
    }

    @SuppressWarnings("unused")
    private Mono<GenericResultDto<MarketFairsResponseDto>> logInternalErrorReturnMarketFairsDefaultPage(Throwable exception) {
        log.error("BusinessAssistant error: " + exception.getMessage());
        return this.getMarketFairsDefaultPage();
    }

    private Mono<GenericResultDto<MarketFairsResponseDto>> getMarketFairsDefaultPage() {
        genericResultDto.setInfo(0, 0, 0, new MarketFairsResponseDto[0]);
        return Mono.just(genericResultDto);
    }
}
