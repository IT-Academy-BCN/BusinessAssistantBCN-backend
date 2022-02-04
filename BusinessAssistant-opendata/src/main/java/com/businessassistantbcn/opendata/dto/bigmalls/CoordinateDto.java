package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateDto {
	
	@JsonProperty("x")
	private double x;
	@JsonProperty("y")
	private double y;
	
}