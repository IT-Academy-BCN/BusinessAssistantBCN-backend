package com.businessassistantbcn.login.controller;

import com.businessassistantbcn.login.model.MercatMunicipalRootDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

//http://localhost:8761/v3/mercats-comercials

@RestController
@RequestMapping("/v3")
public class MercatMunicipalController {

    public MercatMunicipalController(){

    }

    //v1.functional

    /**
     * - Read a url.json
     * - Get JsonArra
     * @return
     */
    @GetMapping("/mercats-municipals")
    public MercatMunicipalRootDTO[] getMercatsMunicipals() {
        String url = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/opendatabcn_mercats-centrescomercials_mercats-municipals-js.json";
        MercatMunicipalRootDTO[] mercatMunicipalRootDTO = new MercatMunicipalRootDTO[0];
        try {
            ObjectMapper om = new ObjectMapper();
            mercatMunicipalRootDTO = om.readValue(new URL(url), MercatMunicipalRootDTO[].class); // funciona
            if(mercatMunicipalRootDTO.length >0){
                System.out.println("Quantity registers:" + mercatMunicipalRootDTO.length);

            }else{
                System.out.println("Quantity registers:" + mercatMunicipalRootDTO.length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mercatMunicipalRootDTO;
    }






}
