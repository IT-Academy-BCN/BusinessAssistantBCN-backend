package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class Coordinate {
    @JsonProperty("x")
    private float x;
    @JsonProperty("y")
    private float y;
}
