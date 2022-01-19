package com.businessassistantbcn.opendata.dto.bcnzones;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BcnZonesResponseDto {
	
    @JsonProperty("count")
    private int count;
    
    @JsonProperty("elements")
    private BcnZonesDto[] elements;
    
}
