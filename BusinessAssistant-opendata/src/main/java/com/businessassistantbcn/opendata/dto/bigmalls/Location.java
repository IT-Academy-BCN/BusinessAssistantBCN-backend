package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Location {

    @JsonProperty("type")
    private String type;
    @JsonProperty("geometries")
    public List<Geometry> geometries;
}
