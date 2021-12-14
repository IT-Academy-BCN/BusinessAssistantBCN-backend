package com.businessassistantbcn.opendata.helper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonHelper {

    public <T> List<T> deserializeToList(String json){
        return null;
    }

    public JsonNode deserializeToJsonNode(String json){
        return null;
    }

    public String serialize(JsonNode node){
        return "";
    }


}
