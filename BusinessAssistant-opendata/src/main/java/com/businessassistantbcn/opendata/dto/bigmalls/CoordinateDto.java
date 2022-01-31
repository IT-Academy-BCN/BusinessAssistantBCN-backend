package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CoordinateDto {

	private Double x;
	private Double y;

	@JsonGetter("x")
	public Double getX() {
		return x;
	}

	@JsonSetter("0")
	public void setX(Double x) {
		this.x = x;
	}

	@JsonGetter("y")
	public Double getY() {
		return y;
	}

	@JsonSetter("1")
	public void setY(Double y) {
		this.y = y;
	}
}