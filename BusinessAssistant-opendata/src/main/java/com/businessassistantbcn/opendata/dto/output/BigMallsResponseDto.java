package com.businessassistantbcn.opendata.dto.output;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.input.bigmalls.AddressDto;
import com.businessassistantbcn.opendata.dto.input.bigmalls.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.input.bigmalls.ContactDto;
import com.businessassistantbcn.opendata.dto.input.bigmalls.CoordinateDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BigMallsResponseDto {

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

	public List<ActivityInfoDto> mapAddressesToCorrectLocation(List<AddressDto> addressesDataList, CoordinateDto coordinateDto){
		List<ActivityInfoDto> activities = new ArrayList<ActivityInfoDto>();

		activities = addressesDataList.stream().map(c -> mapAddressesToCorrectLocationDto(c, coordinateDto)).collect(Collectors.toList());
		return activities;
	}

	public ActivityInfoDto mapAddressesToCorrectLocationDto(AddressDto addressesDataDto, CoordinateDto coordinateDto){
		ActivityInfoDto activity = new ActivityInfoDto();
		System.out.println("rebut: "+coordinateDto.getX());
		System.out.println("abans: "+addressesDataDto.getLocation().getGeometries().getX());
		//addressesDataDto.
		System.out.println("després: "+addressesDataDto.getLocation().getGeometries().getX());

		return activity;
	}
}
