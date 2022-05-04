package com.businessassistantbcn.opendata.dto.input.bcnzones;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BcnZonesDto {
    /*@JsonProperty("id")*/
    private int idZone;
    /*@JsonProperty("name")*/
    private String zoneName;

    public BcnZonesDto(int idZone, String zoneName) {
        this.idZone = idZone;
        this.zoneName = zoneName;
    }

    @JsonGetter("idZone")
    public int getId() {
        return idZone;
    }

    @JsonSetter("id")
    public void setId(int id) {
        this.idZone = id;
    }

    @JsonGetter("zoneName")
    public String getName() {
        return zoneName;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.zoneName = name;
    }
}
