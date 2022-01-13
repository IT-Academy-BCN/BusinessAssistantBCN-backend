package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoordinateDto {
	
	@JsonProperty("x")
	public double x;
	@JsonProperty("y")
	public double y;
	
	public CoordinateDto() {}
	
	public CoordinateDto(double x, double y) {
		this.x = x;
		this.y = y;
    }
    
}
