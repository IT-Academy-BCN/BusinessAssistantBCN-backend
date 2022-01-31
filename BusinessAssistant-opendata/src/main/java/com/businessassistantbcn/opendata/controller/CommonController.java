package com.businessassistantbcn.opendata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.businessassistantbcn.opendata.service.config.DataConfigService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/api/common")


public class CommonController {
	
	@Autowired
	private DataConfigService dataConfigService;
	
    @GetMapping(value="/zones")
    @ApiOperation("Get BCN zones")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
    })
    public Mono<?> getZones() {
    	 try{
             return dataConfigService.getBcnZones();
         }catch (Exception mue){
             throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Resource not found", mue);
         }
    }
}
