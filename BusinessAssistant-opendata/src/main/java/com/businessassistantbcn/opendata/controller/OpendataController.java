package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.economicactivitiescensus.EconomicActivitiesCensusDto;
import com.businessassistantbcn.opendata.dto.input.SearchDTO;
import com.businessassistantbcn.opendata.dto.input.marketfairs.MarketFairsSearchDto;
import com.businessassistantbcn.opendata.dto.input.municipalmarkets.MunicipalMarketsSearchDTO;
import com.businessassistantbcn.opendata.dto.output.*;
import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import com.businessassistantbcn.opendata.service.config.TestService;
import com.businessassistantbcn.opendata.service.externaldata.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/opendata")
public class OpendataController {

    private static final Logger log = LoggerFactory.getLogger(OpendataController.class);

    @Autowired
    BigMallsService bigMallsService;
    @Autowired
    TestService testService;
    @Autowired
    EconomicActivitiesCensusService economicActivitiesCensusService;
    @Autowired
    CommercialGalleriesService commercialGalleriesService;
    @Autowired
    MarketFairsService marketFairsService;
    @Autowired
    LargeEstablishmentsService largeEstablishmentsService;
    @Autowired
    MunicipalMarketsService municipalMarketsService;

    @GetMapping(value = "/test")
    public String test() {
        log.info("** Saludos desde el logger **");
        return "Hello from BusinessAssistant Barcelona!!!";
    }

    //reactive
    @GetMapping(value = "/test-reactive")
    public Mono<StarWarsVehiclesResultDto> testReactive() {
        return testService.getTestData();
    }

    @GetMapping("/large-establishments")
    public Mono<GenericResultDto<LargeEstablishmentsResponseDto>> largeEstablishments(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit) {
        return largeEstablishmentsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/large-establishments/activities")
    public Mono<GenericResultDto<ActivityInfoDto>> largeEstablishmentsActivities(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit) {
        return largeEstablishmentsService.getLargeEstablishmentsActivities(
                this.getValidOffset(offset), this.getValidLimit(limit)
        );
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments/district/{district}")
    public Mono<GenericResultDto<LargeEstablishmentsResponseDto>> largeEstablishmentsByDistrict(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("district") int district
    ) {
        return largeEstablishmentsService
                .getPageByDistrict(this.getValidOffset(offset), this.getValidLimit(limit), district);
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments/activity/{activity}")
    public Mono<GenericResultDto<LargeEstablishmentsResponseDto>> largeEstablishmentsByActivity(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("activity") String activity) {
        return largeEstablishmentsService
                .getPageByActivity(this.getValidOffset(offset), this.getValidLimit(limit), activity);
    }

    @GetMapping("/large-establishments/search")
    public Mono<GenericResultDto<LargeEstablishmentsResponseDto>> largeEstablishmentsSearch(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @Valid @RequestParam int[] zones,
            @Valid @RequestParam int[] activities) {

        SearchDTO searchParams = new SearchDTO();
        searchParams.setZones(zones);
        searchParams.setActivities(activities);
        return largeEstablishmentsService.getPageBySearch(
                this.getValidOffset(offset), this.getValidLimit(limit), searchParams);
    }

    @GetMapping("/commercial-galleries")
    public Mono<GenericResultDto<CommercialGalleriesResponseDto>> commercialGalleries(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit) {
        return commercialGalleriesService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/commercial-galleries/activities")
    public Mono<GenericResultDto<ActivityInfoDto>> commercialGalleriesAllActivities(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit) {
        return commercialGalleriesService.getCommercialGalleriesActivities(
                this.getValidOffset(offset), this.getValidLimit(limit)
        );
    }

    @GetMapping("/commercial-galleries/district/{district}")
    public Mono<GenericResultDto<CommercialGalleriesResponseDto>> commercialGaleriesByDistrict(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("district") int district) {
        return commercialGalleriesService
                .getPageByDistrict(this.getValidOffset(offset), this.getValidLimit(limit), district);
    }

    @GetMapping("/commercial-galleries/activity/{activity}")
    public Mono<GenericResultDto<CommercialGalleriesResponseDto>> commercialGalleriesByActivity(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("activity") String activity) {
        return commercialGalleriesService
                .getPageByActivity(this.getValidOffset(offset), this.getValidLimit(limit), activity);
    }

    @GetMapping("/commercial-galleries/search")
    public Mono<GenericResultDto<CommercialGalleriesResponseDto>> commercialGalleriesSearch(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @Valid @RequestParam int[] zones,
            @Valid @RequestParam int[] activities) {

        SearchDTO searchParams = new SearchDTO();
        searchParams.setZones(zones);
        searchParams.setActivities(activities);
        return commercialGalleriesService.getPageBySearch(
                this.getValidOffset(offset), this.getValidLimit(limit), searchParams);
    }

    //GET ?offset=0&limit=10
    @GetMapping("/big-malls")
    public Mono<GenericResultDto<BigMallsResponseDto>> bigMalls(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit) {
        return bigMallsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/big-malls/activities")
    public Mono<GenericResultDto<ActivityInfoDto>> bigMallsAllActivities(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit) {
        return bigMallsService.getBigMallsActivities(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/big-malls/activity/{activity}")
    public Mono<GenericResultDto<BigMallsResponseDto>> bigMallsByActivity(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("activity") String activity) {
        return bigMallsService
                .getPageByActivity(this.getValidOffset(offset), this.getValidLimit(limit), activity);
    }

    @GetMapping("/big-malls/district/{district}")
    public Mono<GenericResultDto<BigMallsResponseDto>> bigMallsByDistrict(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("district") int district) {
        return bigMallsService
                .getPageByDistrict(this.getValidOffset(offset), this.getValidLimit(limit), district);
    }

    @GetMapping("/big-malls/search")
    public Mono<GenericResultDto<BigMallsResponseDto>> bigMallsSearch(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @Valid @RequestParam int[] zones,
            @Valid @RequestParam int[] activities) {

        SearchDTO searchParams = new SearchDTO();
        searchParams.setZones(zones);
        searchParams.setActivities(activities);
        return bigMallsService.getPageBySearch(
                this.getValidOffset(offset), this.getValidLimit(limit), searchParams);
    }

    @GetMapping("/municipal-markets")
    public Mono<GenericResultDto<MunicipalMarketsResponseDto>> municipalMarkets(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit) {
        return municipalMarketsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/municipal-markets/district/{district}")
    public Mono<GenericResultDto<MunicipalMarketsResponseDto>> municipalMarketsByDistrict(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("district") int district) {
        return municipalMarketsService.getPageByDistrict(this.getValidOffset(offset), this.getValidLimit(limit), district);
    }

    @GetMapping("/municipal-markets/search")
    public Mono<GenericResultDto<MunicipalMarketsResponseDto>> municipalMarketsSearch(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @Valid @RequestParam int [] zones) {

        MunicipalMarketsSearchDTO searchParams= new MunicipalMarketsSearchDTO();
        searchParams.setZones(zones);
        return municipalMarketsService.getPageBySearch(
                this.getValidOffset(offset), this.getValidLimit(limit), searchParams);
    }

    @GetMapping("/market-fairs")
    public Mono<GenericResultDto<MarketFairsResponseDto>> marketFairs(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit
    ) {
        return marketFairsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/market-fairs/district/{district}")
    public Mono<GenericResultDto<MarketFairsResponseDto>> marketFairsByDistrict(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("district") int district) {
        return marketFairsService.getPageByDistrict(this.getValidOffset(offset), this.getValidLimit(limit), district);
    }

    @GetMapping("/market-fairs/search")
    public Mono<GenericResultDto<MarketFairsResponseDto>> marketFairsSearch(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @Valid @RequestParam int [] zones) {

        MarketFairsSearchDto searchParams=new MarketFairsSearchDto();
        searchParams.setZones(zones);
        return marketFairsService.getPageBySearch(
                this.getValidOffset(offset), this.getValidLimit(limit), searchParams);
    }

    //GET ?offset=0&limit=10
    @GetMapping("/economic-activities-census")
    public Mono<GenericResultDto<EconomicActivitiesCensusDto>> economicActivitiesCensus(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit
    ) {
        return economicActivitiesCensusService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    private int getValidOffset(String offset) {
        if (offset == null || offset.isEmpty()) {
            return 0;
        }
        // NumberUtils.isDigits returns false for negative numbers
        if (!NumberUtils.isDigits(offset)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return Integer.parseInt(offset);
    }

    private int getValidLimit(String limit) {
        if (limit == null || limit.isEmpty() || limit.equals("-1")) {
            return -1;
        }
        // NumberUtils.isDigits returns false for negative numbers
        if (!NumberUtils.isDigits(limit)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return Integer.parseInt(limit);
    }
}
