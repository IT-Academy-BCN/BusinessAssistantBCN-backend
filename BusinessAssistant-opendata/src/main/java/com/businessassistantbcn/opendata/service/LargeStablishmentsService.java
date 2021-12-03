package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LargeStablishmentsService {

    @Autowired
    private PropertiesConfig config;
    @Autowired
    private HttpClientHelper httpClientHelper;
    @Autowired
    private JsonHelper jsonHelper;

    public JsonNode getAllData(){
       return null;
    }

    public JsonNode getPage(int offset, int limit){
        return null;
    }
}
