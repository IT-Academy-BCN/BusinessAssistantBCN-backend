package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.*;

import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsResponseDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/api/opendata")
public class OpendataController {

    private RestTemplate restTemplate;
    private final String urlTemplate = "https://opendata-ajuntament.barcelona.cat/data/api/3/action/package_search";

    public OpendataController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    HttpClientHelper helper;

    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test(){
        helper.getTestRequest();
        return "Hello from BusinessAssistant Barcelona!!!";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments")
    @ApiOperation("Get large establishments SET 0 LIMIT 10")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    public PageImpl largeEstablishments(@RequestParam int offset, @RequestParam(required = false, defaultValue = "0") int limit) {
        ResponseEntity<LargeStablishmentsResponseDto> largeStablishmentsResponseEntity = restTemplate.getForEntity(urlTemplate, LargeStablishmentsResponseDto.class);
        LargeStablishmentsResponseDto body = largeStablishmentsResponseEntity.getBody();

        List<LargeStablishmentsResponseDto.Results> results = body.getResult().getResults();
        limit = limit == 0 ? results.size() : limit;
        PageRequest pageRequest = PageRequest.of(offset, limit);
        long start = pageRequest.getOffset();
        long endLimit = start + pageRequest.getPageSize();
        long end = endLimit > results.size() ? results.size() : endLimit;

        return new PageImpl(results.subList((int) start, (int) end), pageRequest, results.size());
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
    public String bigMalls()
    {
        return "big-malls";
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


}