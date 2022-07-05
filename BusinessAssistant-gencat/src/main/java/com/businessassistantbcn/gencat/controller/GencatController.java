package com.businessassistantbcn.gencat.controller;

import com.businessassistantbcn.gencat.dto.output.CcaeResponseDto;
import com.businessassistantbcn.gencat.service.CcaeService;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
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
    public Mono<?> getCcae(@RequestParam(required = false) String offset,
                           @RequestParam(required = false)  String limit) throws MalformedURLException {
        return ccaeService.getResultDTO(getValidOffset(offset), getValidLimit(limit));
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
