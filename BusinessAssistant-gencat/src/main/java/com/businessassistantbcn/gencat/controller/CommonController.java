package com.businessassistantbcn.gencat.controller;

import com.businessassistantbcn.gencat.service.config.DataConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/common")
public class CommonController {

    @Autowired
    DataConfigService typeDataService;

    @GetMapping("/types-data")
    public Mono<?> getTypesData(){
        return typeDataService.getTypes();
    }
}
