package com.businessassistantbcn.opendata.dto.largeestablishments;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class LargeEstablishmentsResponseDto {
	
	@JsonProperty("count")
    private int count;
    
    @JsonProperty("elements")
    private LargeEstablishmentsDto[] elements;

}
