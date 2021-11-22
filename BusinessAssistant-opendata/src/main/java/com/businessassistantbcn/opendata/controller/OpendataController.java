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
    
    @GetMapping("/large-establishments")
    public String largeEstablishments() 
    {
        return "large-establishments";
    }
    
    @GetMapping("/commercial-galeries")
    public String commercialGaleries() 
    {
        return "commercial-galeries";
    }
    
    @GetMapping("/big-malls")
    public String bigMalls() 
    {
        return "big-malls";
    }
    
    @GetMapping("/municipal-markets")
    public String municipalMarkets() 
    {
        return "municipal-markets";
    }
    
    @GetMapping("/markets-fairs")
    public String marketsFairs() 
    {
        return "markets-fairs";
    }
    
    
}
