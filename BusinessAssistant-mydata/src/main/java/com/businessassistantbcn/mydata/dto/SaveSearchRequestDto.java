package com.businessassistantbcn.mydata.dto;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class SaveSearchRequestDto {
	private String searchName;
	private String searchDetail;
	private JsonNode searchResult;
}