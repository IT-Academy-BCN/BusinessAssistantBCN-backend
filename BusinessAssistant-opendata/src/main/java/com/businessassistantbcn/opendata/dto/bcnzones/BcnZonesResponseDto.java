package com.businessassistantbcn.opendata.dto.bcnzones;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;


public class BcnZonesResponseDto {
    @JsonProperty("count")
    private int count;
    @JsonProperty("elements")
    private BcnZonesDto[] elements;

    public BcnZonesResponseDto(int count, BcnZonesDto[] elements) {
        this.count = count;
        this.elements = elements;
    }
}
