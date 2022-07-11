package com.businessassistantbcn.gencat.controller;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/gencat")
public class GencatController {

    private static final Logger log = LoggerFactory.getLogger(GencatController.class);

    @Autowired
    private HttpProxy httpProxy;

    @Autowired
    private PropertiesConfig config;

    private URL url;

    @GetMapping(value = "/test")
    public String test() {
        log.info("** Saludos desde el logger **");
        return "Hello from GenCat Controller!!!";
    }

    //se debe implementar cuando se hayan definido los dto y service layer
    @GetMapping("/ccae")
    public Mono<List<Object>> getAllClassificationOfEconomicActivities() throws MalformedURLException {
        url = new URL(config.getDs_economicActivities());
        Object[] economicActivities = httpProxy.getRequestData(url, Object[].class).block();
        return Mono.just((Arrays.asList(economicActivities)));
    }

    //se debe implementar
    @GetMapping("/ccae/{ccae_id}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Mono getEconomicActivityById(@PathVariable("ccae_id") String ccaeId) {
        return Mono.just(HttpStatus.NOT_IMPLEMENTED);
    }

    //se debe implementar
    @GetMapping("/ccae/type/{type}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Mono getEconomicActivityByType(@PathVariable("type") String type) {
        return Mono.just(HttpStatus.NOT_IMPLEMENTED);
    }

    //se debe implementar
    @GetMapping("/ccae/types")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Mono getEconomicActivitiesTypes() {
        return Mono.just(HttpStatus.NOT_IMPLEMENTED);
    }

}
