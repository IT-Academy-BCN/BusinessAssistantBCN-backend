package com.businessassistantbcn.opendata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.*;

@RestController
@RequestMapping(value = "/v1/api/opendata")
public class OpendataController {


    @Autowired
    public OpendataController(){
    }

    @GetMapping(value="/test")
    @ApiOperation("Get test")
    @ApiResponse(code = 200, message = "OK")
    public String test() 
    {
        return "Hello from BusinessAssistant Barcelona!!!";
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
