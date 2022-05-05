package com.businessassistantbcn.opendata.dto.output;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.input.largeestablishments.AddressDto;
import com.businessassistantbcn.opendata.dto.input.largeestablishments.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.input.largeestablishments.ContactDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LargeEstablishmentsResponseDto {

	private String name;
	@JsonUnwrapped
    private ContactDto value; // contact
    private List<ActivityInfoDto> activities; // activities
    private List<AddressDto> addresses;
    
    public List<ActivityInfoDto> mapClassificationDataListToActivityInfoList(List<ClassificationDataDto> classificationDataList) {
		List<ActivityInfoDto> activities = new ArrayList<ActivityInfoDto>();

		activities = classificationDataList.stream().map(c -> mapClassificationDataDtoToActivityInfoDto(c)).collect(Collectors.toList());
		return activities;
	}

	public ActivityInfoDto mapClassificationDataDtoToActivityInfoDto(ClassificationDataDto classificationDataDto) {
		ActivityInfoDto activity = new ActivityInfoDto();
		activity.setActivityId(classificationDataDto.getId());
		activity.setActivityName(classificationDataDto.getName());
		
		return activity;
	}
}
