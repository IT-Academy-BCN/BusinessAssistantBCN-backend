package com.businessassistantbcn.mydata.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
@Getter 
@Setter
@ToString
public class SearchResultsDto {

    private JsonNode[] results;

    public SearchResultsDto() {}

    public void setInfo(JsonNode[] results) {
        this.results = results;
    }
}
