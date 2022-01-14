package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class CoordinateDto {
    @JsonProperty("x")
    float x;
    @JsonProperty("y")
    float y;
}
