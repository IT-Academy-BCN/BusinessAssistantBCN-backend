package com.businessassistantbcn.opendata.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter @Setter
public class SearchRequestDto {

	private String[] zones;
	private Long[] activities;
}
