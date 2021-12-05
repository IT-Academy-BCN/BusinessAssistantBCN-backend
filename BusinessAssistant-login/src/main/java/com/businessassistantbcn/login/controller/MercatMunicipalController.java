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
     * - Read a url to get JSON
     * - Show Json to POJO
     * - Show register
     * @return
     */
    @GetMapping("/mercats-municipals")
    public JsonNode getMercatsMunicipals() {
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
//        return mercatMunicipalRootDTO; // functional
//        return arrayDTOToJsonNode(mercatMunicipalRootDTO); //functional
//        return  arrayDTOToString(mercatMunicipalRootDTO); //functional
        return  jsonNodeIntoJsonNode(mercatMunicipalRootDTO);
    }

    /**
     * - Receive an arrayDTO and return it to JsonNode
     * - Functional
     * @param mercatsMunicipals
     * @return
     */
    public JsonNode arrayDTOToJsonNode(MercatMunicipalRootDTO[] mercatsMunicipals){
        JsonNode jsonNode;
        ObjectMapper mapperDTO = new ObjectMapper();
        jsonNode = mapperDTO.valueToTree(mercatsMunicipals);
        return jsonNode;
    }

    /**
     * - Receive an arrayDTO, convert arrayDTO to a JsoNode.
     * - Create an ObjectNode with two properties ( count, elements:[insertJsonNode])
     * - Return objectNode
     * - Functional
     * @param mercatsMunicipals
     * @return
     */
    public JsonNode jsonNodeIntoJsonNode (MercatMunicipalRootDTO[] mercatsMunicipals){
        JsonNode jsonNode;
        ObjectNode outerJsonNode;
        ObjectMapper mapperDTO = new ObjectMapper();
        jsonNode = arrayDTOToJsonNode(mercatsMunicipals);
        outerJsonNode = mapperDTO.createObjectNode();
        outerJsonNode.put("count",mercatsMunicipals.length).putPOJO("elements",jsonNode);
        return outerJsonNode;
    }


}
