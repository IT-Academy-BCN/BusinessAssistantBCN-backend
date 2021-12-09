package com.businessassistantbcn.opendata.service;

import com.businessassistantbcn.opendata.config.PropertiesConfig;
import com.businessassistantbcn.opendata.dto.GenericResultDto;
import com.businessassistantbcn.opendata.dto.largestablishments.LargeStablishmentsResponseDto;
import com.businessassistantbcn.opendata.helper.HttpClientHelper;
import com.businessassistantbcn.opendata.helper.JsonHelper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LargeStablishmentsService {

    @Autowired
    private PropertiesConfig config;
    @Autowired
    private HttpClientHelper httpClientHelper;
    @Autowired
    private JsonHelper jsonHelper;

    public GenericResultDto<LargeStablishmentsResponseDto> getAllData() {
        return null;
    }

    public GenericResultDto<LargeStablishmentsResponseDto> getPage(int offset, int limit) {
        return null;
    }

    /**
     *
     * {@link} https://tools.ietf.org/id/draft-goessner-dispatch-jsonpath-00.html
     * {@link} https://www.baeldung.com/guide-to-jayway-jsonpath
     */

    public String getStablishmentsByActivity(int[] activities, int offset, int limit){
        return null;
    }

    /**
     *
     * {@link} https://tools.ietf.org/id/draft-goessner-dispatch-jsonpath-00.html
     * {@link} https://www.baeldung.com/guide-to-jayway-jsonpath
     */

    public String getStablishmentsByDistrict(int[] districts, int offset, int limit){
        return null;
    }
}
