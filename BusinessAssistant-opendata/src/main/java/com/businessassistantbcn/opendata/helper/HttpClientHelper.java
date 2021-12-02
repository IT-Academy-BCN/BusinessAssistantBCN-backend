package com.businessassistantbcn.opendata.helper;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class HttpClientHelper {

    @Autowired
    private PropertiesConfig config;

    public String getStringRoot(URL url){
        return "";
    }


    public JsonNode getJsonRoot(URL url) {
        return null;
    }


    public Object getObjectRoot (URL url, Class clazz){

        return null;
    }

}
