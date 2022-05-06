package com.businessassistantbcn.opendata.dto.input.municipalmarkets;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
public class ActivityDto {
    @JsonProperty("activityId")
    private int activityId;
    @JsonProperty("activityName")
    private String activityName;
    
}