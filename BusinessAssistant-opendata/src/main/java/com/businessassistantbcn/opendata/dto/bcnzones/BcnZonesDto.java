package com.businessassistantbcn.opendata.dto.bcnzones;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;


public class BcnZonesDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;

    public BcnZonesDto(int id, String name) {

        this.id = id;
        this.name = name;
    }
}
