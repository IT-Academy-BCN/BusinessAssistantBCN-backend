package com.businessassistantbcn.gencat.controller;

import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.io.CcaeDto;
import com.businessassistantbcn.gencat.service.CcaeService;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;

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
    public Mono<GenericResultDto<CcaeDto>> getAllCcae(@RequestParam(required = false) String offset,
                                                      @RequestParam(required = false) String limit) throws MalformedURLException {
        return ccaeService.getPage(getValidOffset(offset), getValidLimit(limit));
    }

    //se debe implementar
    @GetMapping("/ccae/{ccae_id}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Mono getEconomicActivityById(@PathVariable("ccae_id") String ccaeId) {
        return Mono.just(HttpStatus.NOT_IMPLEMENTED);
    }

    //se debe implementar
    @GetMapping("/ccae/type/{type_id}")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Mono getEconomicActivityByType(@PathVariable("type_id") String type) {
        return Mono.just(HttpStatus.NOT_IMPLEMENTED);
    }

    //Per implementar
    @GetMapping("/ccae/types")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Mono getAllCcaeTypes() {
        return Mono.just(HttpStatus.NOT_IMPLEMENTED);
    }


    private int getValidOffset(String offset)
    {
        if (offset == null || offset.isEmpty()) {
            return 0;
        }
        // NumberUtils.isDigits returns false for negative numbers
        if (!NumberUtils.isDigits(offset)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return Integer.parseInt(offset);
    }

    private int getValidLimit(String limit) {
        if (limit == null || limit.isEmpty() || limit.equals("-1")) {
            return -1;
        }
        // NumberUtils.isDigits returns false for negative numbers
        if (!NumberUtils.isDigits(limit)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return Integer.parseInt(limit);
    }
}
