package com.businessassistantbcn.opendata.dto.output;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.businessassistantbcn.opendata.dto.output.bigmalls.AddressInfoDto;
import com.businessassistantbcn.opendata.dto.input.bigmalls.*;
import com.businessassistantbcn.opendata.dto.output.bigmalls.CoordinateInfoDto;
import org.springframework.stereotype.Component;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
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
    private List<AddressInfoDto> addresses;

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

	public List<AddressInfoDto> mapAddressesToCorrectLocation(List<AddressDto> addressesDataList, CoordinateDto coordinateDto){
		List<AddressInfoDto> addresses = new ArrayList<AddressInfoDto>();
		CoordinateInfoDto newCoords = new CoordinateInfoDto();
		newCoords.setX(coordinateDto.getX());
		newCoords.setY(coordinateDto.getY());
		addresses = addressesDataList.stream().map(c -> mapClassificationDataDtoToAddressInfoDto(c, newCoords)).collect(Collectors.toList());
		return addresses;
	}

	public AddressInfoDto mapClassificationDataDtoToAddressInfoDto(AddressDto classificationDataDto, CoordinateInfoDto coordinateDto){

		AddressInfoDto address = new AddressInfoDto();
		address.setAddress_name(classificationDataDto.getAddress_name());
		address.setStreet_number(classificationDataDto.getStreet_number_1());
		address.setZip_code(classificationDataDto.getZip_code());
		address.setDistrict_id(classificationDataDto.getDistrict_id());
		address.setTown(classificationDataDto.getTown());
		address.setLocation(coordinateDto);

		return address;
	}
}
