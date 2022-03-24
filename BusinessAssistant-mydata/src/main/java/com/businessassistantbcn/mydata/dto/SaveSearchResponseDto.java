package com.businessassistantbcn.mydata.dto;

import java.util.Date;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
public class SaveSearchResponseDto {
	private String searchUuid;
	private String userUuid;
	private Date searchDate;
	private String searchName;
	private String searchDetail;
	private JsonNode searchResult;
}
