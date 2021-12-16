package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.dto.test.StarWarsVehiclesResultDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import com.businessassistantbcn.opendata.service.BigMallsService;
import com.businessassistantbcn.opendata.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import io.swagger.annotations.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;

@RestController
@RequestMapping(value = "/v1/api/opendata")
public class OpendataController {

    @Autowired
    BigMallsService bigMallsService;
    @Autowired
    TestService testService;

    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test(){
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
    public String largeEstablishments()
    {
        return "large-establishments";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/commercial-galeries")
    @ApiOperation("Get commercial galeries SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    public String commercialGaleries()
    {
        return "commercial-galeries";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/big-malls")
    @ApiOperation("Get big malls SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    public <T> Mono<T> bigMalls()
    {
        try{
            return bigMallsService.getBigmallsData();
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
    @GetMapping("/large-stablishments/activity?select=fields.activitat")
    @ApiOperation("Get large stablishment activity SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found :("),
    })
    public String largeEstablishmentsActivity()
    {
        return "large-stabilshments-activity";
    }

}
