package com.businessassistantbcn.mydata.dto;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class SaveSearchRequestDto {
	@NotNull(message = "The name is required")
	private String searchName;
	private String searchDetail;
	@NotNull(message = "The search should have any results to save")
	private JsonNode searchResult;
}