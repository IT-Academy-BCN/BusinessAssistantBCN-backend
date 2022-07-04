package com.businessassistantbcn.gencat.controller;

import com.businessassistantbcn.gencat.dto.output.CcaeResponseDto;
import com.businessassistantbcn.gencat.service.CcaeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.http.HttpResponse;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/gencat")
public class GencatController {

    private static final Logger log = LoggerFactory.getLogger(GencatController.class);

    @Autowired
    CcaeService ccaeService;

    @GetMapping(value="/test")
    public String test() {
        log.info("** Saludos desde el logger **");
        return "Hello from GenCat Controller!!!";
    }

    @GetMapping("/ccae")
    public Mono<?> getCcae() throws MalformedURLException {
        return ccaeService.getResultDTO();
    }


}
