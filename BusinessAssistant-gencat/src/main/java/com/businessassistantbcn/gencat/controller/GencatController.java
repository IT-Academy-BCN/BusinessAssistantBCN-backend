package com.businessassistantbcn.gencat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/businessassistantbcn/api/v1/gencat")
public class GencatController {

    private static final Logger log = LoggerFactory.getLogger(GencatController.class);

    @GetMapping(value="/test")
    public String test() {
        log.info("** Saludos desde el logger **");
        return "Hello from GenCat Controller!!!";
    }
}
