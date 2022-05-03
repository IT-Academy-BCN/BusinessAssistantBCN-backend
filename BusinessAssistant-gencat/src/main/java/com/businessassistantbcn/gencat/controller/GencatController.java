package com.businessassistantbcn.gencat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping(value = "/businessassistantbcn/api/v1/gencat")
public class GencatController {

    @GetMapping(value="/test")
    public String test() {
        log.info("** Saludos desde el logger **");
        return "Hello from GenCat Controller!!!";
    }
}
