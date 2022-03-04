package com.businessassistantbcn.mydata.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class SavedSearchResponseDto {
	private String searchUuid;
	private String searchName;
	private Date searchDate;
	private String searchDetail;
	private JsonNode searchResult;
}
