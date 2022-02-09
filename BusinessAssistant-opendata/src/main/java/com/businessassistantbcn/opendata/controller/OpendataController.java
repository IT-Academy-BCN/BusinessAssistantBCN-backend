package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.businessassistantbcn.opendata.service.config.TestService;
import com.businessassistantbcn.opendata.service.externaldata.*;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.net.MalformedURLException;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

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
    CommercialGalleriesService commercialGaleriesService;
    @Autowired
    MarketFairsService marketFairsService;
    @Autowired
    LargeEstablishmentsService largeEstablishmentsService;
    @Autowired
    MunicipalMarketsService municipalMarketsService;

    //TODO remove once all endpoints are paginated
    private final boolean PAGINATION_ENABLED = true;

    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test() {
        log.info("** Saludos desde el logger **");
        return "Hello from BusinessAssistant Barcelona!!!";
    }

    //reactive
    @GetMapping(value="/test-reactive")
    @ApiOperation("Get test")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Mono.class),
        @ApiResponse(code = 503, message = "Resource Not Found", response = String.class)
    })
    public Mono<?> testReactive(){
        try {
            return testService.getTestData();
        } catch (MalformedURLException mue) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }

    @GetMapping("/large-establishments")
    @ApiOperation("Get large establishments SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable")})
    public Mono<?> largeEstablishments(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit,
        @RequestParam Map<String, String> map
    ) {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return largeEstablishmentsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/commercial-galleries")
    @ApiOperation("Get commercial galleries SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable")
    })
    public Mono<?> commercialGalleries(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit,
        @RequestParam Map<String, String> map
    ) {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return commercialGaleriesService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments/district/{district}")
    @ApiOperation("Get large establishments SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    public Mono<?> largeEstablishmentsByDistrict(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit,
        @PathVariable("district") int district,
        @RequestParam Map<String, String> map
    ) {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return largeEstablishmentsService
            .getPageByDistrict(this.getValidOffset(offset), this.getValidLimit(limit), district);
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments/activity/{activity}")
    @ApiOperation("Get large establishments by activity SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    public Mono<?> largeEstablishmentsByActivity(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit,
        @PathVariable("activity") String activity,
        @RequestParam Map<String, String> map
    ) {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return largeEstablishmentsService
            .getPageByActivity(this.getValidOffset(offset), this.getValidLimit(limit), activity);
    }

    //GET ?offset=0&limit=10
    @GetMapping("/big-malls")
    @ApiOperation("Get big malls SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable")})
    public Mono<?> bigMalls(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit
    ){
        return bigMallsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    @GetMapping("/municipal-markets")
    @ApiOperation("Get municipal markets SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable")})
    public Mono<?> municipalMarkets(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit
    ){
            return municipalMarketsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    //GET ?offset=0&limit=10
    @GetMapping("/municipal-markets/district/{district}")
    @ApiOperation("Get large establishments SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Offset or Limit cannot be null"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    public String municipalMarketsByDistrict( //provisional return. (Mono<?>)
    		@ApiParam(value = "Offset", name= "Offset")
            @RequestParam(required = false) String offset,
            @ApiParam(value = "Limit", name= "Limit")
            @RequestParam(required = false)  String limit,
            @PathVariable("district") int district){ // It's int why BusinessAssistantBCN-frontend/blob/develop/src/app/models/common/zone.model.ts   it's  number
    	
    	 offset = offset == null ? "0": offset;
         limit = limit == null ? "-1": limit;
         
         try{
        	 //CARE! waiting service
        	 //return municipalMarketsService.getPageByDistrict(Integer.parseInt(offset), Integer.parseInt(limit), district);
        	 return "CARE! waiting service municipalMarketsService.getPageByDistrict";
         }catch (Exception mue){
             throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
         }
    }
    
	@GetMapping("/market-fairs")
	@ApiOperation("Get market fairs SET 0 LIMIT 10")
	@ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
	})
	public Mono<?> marketFairs(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit,
        @RequestParam Map<String, String> map
    ) {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return marketFairsService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
	}

    //GET ?offset=0&limit=10
    @GetMapping("/economic-activities-census")
    @ApiOperation("Get markets fairs SET 0 LIMIT 10")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    public Mono<?> economicActivitiesCensus(
        @ApiParam(value = "Offset", name= "Offset")
        @RequestParam(required = false) String offset,
        @ApiParam(value = "Limit", name= "Limit")
        @RequestParam(required = false)  String limit,
        @RequestParam Map<String, String> map
    ) {
        this.validateRequestParameters(map, this.PAGINATION_ENABLED);
        return economicActivitiesCensusService.getPage(this.getValidOffset(offset), this.getValidLimit(limit));
    }

    private void validateRequestParameters(Map<String, String> map, boolean paginationEnabled)
    {
        if (!paginationEnabled && !map.keySet().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        for (String key : map.keySet()) {
            if (!key.equals("offset") && !key.equals("limit")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }

    private int getValidOffset(String offset)
    {
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
