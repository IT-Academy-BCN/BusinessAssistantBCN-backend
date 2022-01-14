package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Geometry {

    @JsonProperty("type")
    private String type;
    @JsonProperty("coordinates")
    private List<Double> coordinates;
}
