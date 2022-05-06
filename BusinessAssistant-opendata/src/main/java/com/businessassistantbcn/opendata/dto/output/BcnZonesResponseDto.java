package com.businessassistantbcn.opendata.dto.output;

import com.businessassistantbcn.opendata.dto.input.bcnzones.BcnZonesDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class BcnZonesResponseDto {
	
    @JsonProperty("count")
    private int count;
    
    @JsonProperty("elements")
    private BcnZonesDto[] elements;
    
}
