package com.businessassistantbcn.opendata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/api/common")
public class CommonController {


    @GetMapping(value="/zones")
    public Mono<?> getZones() {

        return null;
    }
}
