package com.businessassistantbcn.opendata.dto.bcnzones;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;


public class BcnZonesDto {
    @JsonProperty("idZone")
    private int id;
    @JsonProperty("zoneName")
    private String name;

    public BcnZonesDto(int idZone, String zoneName) {

        this.id = idZone;
        this.name = zoneName;
    }
}
