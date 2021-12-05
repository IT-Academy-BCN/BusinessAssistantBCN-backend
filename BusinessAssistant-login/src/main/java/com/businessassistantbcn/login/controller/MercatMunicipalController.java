package com.businessassistantbcn.login.controller;

import com.businessassistantbcn.login.model.MercatMunicipalRootDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URL;


@RestController
@RequestMapping("/v3")
public class MercatMunicipalController {

    public MercatMunicipalController(){

    }

    /**
     * - Read an URL to get data.Json, Show Json to POJO
     * @return
     */
    @GetMapping("/mercats-municipals")
    public JsonNode getMercatsMunicipals() {

        //TODO pasar por parametro url.
        String url = "http://www.bcn.cat/tercerlloc/files/mercats-centrescomercials/opendatabcn_mercats-centrescomercials_mercats-municipals-js.json";

        MercatMunicipalRootDTO[] mercatMunicipalRootDTO = new MercatMunicipalRootDTO[0];
        JsonNode jsonNode = null;
        try {
            ObjectMapper om = new ObjectMapper();
            mercatMunicipalRootDTO = om.readValue(new URL(url), MercatMunicipalRootDTO[].class);
            jsonNode= jsonNodeIntoJsonNode(mercatMunicipalRootDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

    /**
     * - Receive an arrayDTO and return it to JsonNode
     * - Functional
     * @param mercatsMunicipals
     * @return
     */
    public JsonNode arrayDTOToJsonNode(MercatMunicipalRootDTO[] mercatsMunicipals) throws Exception {
        if(mercatsMunicipals==null) throw new Exception();
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
    public JsonNode jsonNodeIntoJsonNode (MercatMunicipalRootDTO[] mercatsMunicipals) throws Exception {
        if(mercatsMunicipals==null) throw new Exception();
        JsonNode jsonNode;
        ObjectNode outerJsonNode;
        ObjectMapper mapperDTO = new ObjectMapper();
        jsonNode = arrayDTOToJsonNode(mercatsMunicipals);
        outerJsonNode = mapperDTO.createObjectNode();
        outerJsonNode.put("count",mercatsMunicipals.length).putPOJO("elements",jsonNode);
        return outerJsonNode;
    }


}
