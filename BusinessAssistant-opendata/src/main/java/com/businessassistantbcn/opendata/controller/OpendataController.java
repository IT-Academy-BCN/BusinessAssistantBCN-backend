package com.businessassistantbcn.opendata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/api/opendata")
public class OpendataController {


    @Autowired
    public OpendataController(){
    }

    @GetMapping(value="/test")
    public String test() 
    {
        return "Hello from BusinessAssistant Barcelona!!!";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/large-establishments")
    public String largeEstablishments() 
    {
        return "large-establishments";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/commercial-galeries")
    public String commercialGaleries() 
    {
        return "commercial-galeries";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/big-malls")
    public String bigMalls() 
    {
        return "big-malls";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/municipal-markets")
    public String municipalMarkets() 
    {
        return "municipal-markets";
    }

    //GET ?offset=0&limit=10
    @GetMapping("/markets-fairs")
    public String marketsFairs() 
    {
        return "markets-fairs";
    }
    
    
}
