package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.input.SearchDTO;
import com.businessassistantbcn.opendata.dto.output.MunicipalMarketsResponseDto;
import com.businessassistantbcn.opendata.service.config.TestService;
import com.businessassistantbcn.opendata.service.externaldata.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.util.Map;

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

    //TODO remove once all endpoints are paginated
    private final boolean PAGINATION_ENABLED = true;

    @GetMapping(value = "/test")
    public String test() {
        log.info("** Saludos desde el logger **");
        return "Hello from BusinessAssistant Barcelona!!!";
    }

    //reactive
    @GetMapping(value = "/test-reactive")
    public Mono<?> testReactive() {
        try {
            return testService.getTestData();
        } catch (MalformedURLException mue) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }

    @GetMapping("/large-establishments")
    public Mono<?> largeEstablishments(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return largeEstablishmentsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/large-establishments/activities")
    public Mono<?> largeEstablishmentsActivities(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return largeEstablishmentsService.getLargeEstablishmentsActivities(
                this.getValidOffset(offset), this.getValidLimit(limit)
        );
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments/district/{district}")
    public Mono<?> largeEstablishmentsByDistrict(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("district") int district,
            @RequestParam Map<String, String> map
    ) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return largeEstablishmentsService
                .getPageByDistrict(this.getValidOffset(offset), this.getValidLimit(limit), district);
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments/activity/{activity}")
    public Mono<?> largeEstablishmentsByActivity(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("activity") String activity,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return largeEstablishmentsService
                .getPageByActivity(this.getValidOffset(offset), this.getValidLimit(limit), activity);
    }

    @GetMapping("/large-establishments/search")
    public Mono<?> largeEstablishmentsSearch(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map,
            @RequestBody SearchDTO searchParams) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return largeEstablishmentsService.getPageBySearch(
                this.getValidOffset(offset), this.getValidLimit(limit), searchParams);
    }

    @GetMapping("/commercial-galleries")
    public Mono<?> commercialGalleries(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return commercialGalleriesService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/commercial-galleries/activities")
    public Mono<?> commercialGalleriesAllActivities(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return commercialGalleriesService.getCommercialGalleriesActivities(
                this.getValidOffset(offset), this.getValidLimit(limit)
        );
    }

    @GetMapping("/commercial-galleries/district/{district}")
    public Mono<?> commercialGaleriesByDistrict(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("district") int district,
            @RequestParam Map<String, String> map
    ) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return commercialGalleriesService
                .getPageByDistrict(this.getValidOffset(offset), this.getValidLimit(limit), district);
    }

    @GetMapping("/commercial-galleries/activity/{activity}")
    public Mono<?> commercialGalleriesByActivity(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("activity") String activity,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return commercialGalleriesService
                .getPageByActivity(this.getValidOffset(offset), this.getValidLimit(limit), activity);
    }

    @GetMapping("/commercial-galleries/search")
    public Mono<?> commercialGalleriesSearch(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map,
            @RequestBody SearchDTO searchParams) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return commercialGalleriesService.getPageBySearch(
                this.getValidOffset(offset), this.getValidLimit(limit), searchParams);
    }

    //GET ?offset=0&limit=10
    @GetMapping("/big-malls")
    public Mono<?> bigMalls(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return bigMallsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/big-malls/activities")
    public Mono<?> bigMallsAllActivities(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return bigMallsService.getBigMallsActivities(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/big-malls/activity/{activity}")
    public Mono<?> bigMallsByActivity(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("activity") String activity,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return bigMallsService
                .getPageByActivity(this.getValidOffset(offset), this.getValidLimit(limit), activity);
    }

    @GetMapping("/big-malls/district/{district}")
    public Mono<?> bigMallsByDistrict(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("district") int district,
            @RequestParam Map<String, String> map
    ) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return bigMallsService
                .getPageByDistrict(this.getValidOffset(offset), this.getValidLimit(limit), district);
    }

    @GetMapping("/big-malls/search")
    public Mono<?> bigMallsSearch(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map,
            @RequestBody SearchDTO searchParams) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return bigMallsService.getPageBySearch(
                this.getValidOffset(offset), this.getValidLimit(limit), searchParams);
    }

    @GetMapping("/municipal-markets")
    public Mono<?> municipalMarkets(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return municipalMarketsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/municipal-markets/district/{district}")
    public Mono<GenericResultDto<MunicipalMarketsResponseDto>> municipalMarketsByDistrict(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @PathVariable("district") int district,
            @RequestParam Map<String, String> map) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
            return municipalMarketsService.getPageByDistrict(this.getValidOffset(offset), this.getValidLimit(limit), district);
    }

    @GetMapping("/market-fairs")
    public Mono<?> marketFairs(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map
    ) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return marketFairsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    //GET ?offset=0&limit=10
    @GetMapping("/economic-activities-census")
    public Mono<?> economicActivitiesCensus(
            @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit,
            @RequestParam Map<String, String> map
    ) throws MalformedURLException {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return economicActivitiesCensusService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    private void validateRequestParameters(Map<String, String> map, boolean paginationEnabled) {
        if (!paginationEnabled && !map.keySet().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        for (String key : map.keySet()) {
            if (!key.equals("offset") && !key.equals("limit")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
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
