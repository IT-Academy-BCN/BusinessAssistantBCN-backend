package com.businessassistantbcn.opendata.controller;

import com.businessassistantbcn.opendata.service.config.DataConfigService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/common")
public class CommonController {

    @Autowired
    DataConfigService bcnZonesService;

    @GetMapping("/bcn-zones")
    @ApiOperation("Get bcn Zones")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public Mono<?> bcnZones(){
        return bcnZonesService.getBcnZones();
    }
}
