package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.config.LoggerConfig;
import com.businessassistantbcn.opendata.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import io.swagger.annotations.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/api/opendata")
public class OpendataController {

    @Autowired
    BigMallsService bigMallsService;
    @Autowired
    TestService testService;
    @Autowired
    EconomicActivitiesCensusService economicActivitiesCensusService;
    @Autowired
    CommercialGalleriesService commercialGaleriesService;

    @Autowired
    LargeStablishmentsService largeStablishmentsService;

    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test() {
        LoggerConfig.log.info("** Saludos desde el logger **");
        return "Hello from BusinessAssistant Barcelona!!!";
    }

    //reactive
    @GetMapping(value="/test-reactive")
    @ApiOperation("Get test")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Mono.class),
            @ApiResponse(code = 503, message = "Resource Not Found", response = String.class)})
    public <T> Mono<T> testReactive(){
        try{
            return testService.getTestData();
        }catch(MalformedURLException mue) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments")
    @ApiOperation("Get large establishments SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    public <T> Mono<T> largeEstablishments()
    {
        try{
            return (Mono<T>) largeStablishmentsService.getLargeStablishmentsAll();
        }catch (Exception mue){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }


    //GET ?offset=0&limit=10
    @GetMapping("/commercial-galleries")
    @ApiOperation("Get commercial galleries SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    public <T> Mono<T> commercialGaleries()
    {

        try{
            return (Mono<T>) commercialGaleriesService.getCommercialGaleriesAll();
        }catch (Exception mue){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }

    }




    //GET ?offset=0&limit=10
    @GetMapping("/big-malls")
    @ApiOperation("Get big malls SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Offset or Limit cannot be null"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    public <T> Mono<T> bigMalls(
            @ApiParam(value = "Offset", name= "Offset")
            @RequestParam(required = false) String offset,
            @ApiParam(value = "Limit", name= "Limit")
            @RequestParam(required = false)  String limit){

        offset = offset == null ? "0": offset;
        limit = limit == null ? "-1": limit;

        if(offset.compareTo("")==0 || limit.compareTo("")==0 || offset.compareTo("null")==0 || limit.compareTo("null")==0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Offset or Limit cannot be null");
        }
        try{
            return (Mono<T>) bigMallsService.getPage(Integer.parseInt(offset), Integer.parseInt(limit));
        }catch (Exception mue){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }

    //GET ?offset=0&limit=10
    @GetMapping("/municipal-markets")
    @ApiOperation("Get municipal markets SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    public String municipalMarkets()
    {
        return "municipal-markets";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/markets-fairs")
    @ApiOperation("Get markets fairs SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    public String marketsFairs()
    {
        return "markets-fairs";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-stablishments/activity")
    @ApiOperation("Get large stablishment activity SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    public String largeEstablishmentsActivity()
    {
        return "Large-Stabilshments-Activity";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/economic-activities-census/{offset}/{limit}")
    @ApiOperation("Get markets fairs SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    public <T>Mono<T> economicActivitiesCensus(@PathVariable int offset,
                                               @PathVariable int limit)
    {
        try {
            return (Mono<T>) economicActivitiesCensusService.getAllData(offset,limit);
        }catch (Exception mue){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
        }
    }
}
