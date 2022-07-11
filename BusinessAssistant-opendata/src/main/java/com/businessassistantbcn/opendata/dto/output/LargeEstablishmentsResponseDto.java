package com.businessassistantbcn.opendata.dto.output;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.businessassistantbcn.opendata.dto.input.largeestablishments.AddressDto;
import com.businessassistantbcn.opendata.dto.input.largeestablishments.ClassificationDataDto;
import com.businessassistantbcn.opendata.dto.input.largeestablishments.ContactDto;
import com.businessassistantbcn.opendata.dto.input.largeestablishments.CoordinateDto;
import com.businessassistantbcn.opendata.dto.output.data.AddressInfoDto;
import com.businessassistantbcn.opendata.dto.output.data.CoordinateInfoDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LargeEstablishmentsResponseDto {
	private static final Logger log = LoggerFactory.getLogger(LargeEstablishmentsResponseDto.class);

	private String name;
	private String web;
	private String email;
	private String phone;
	@JsonUnwrapped
    private ContactDto value; // contact
    private List<ActivityInfoDto> activities; // activities
    private List<AddressInfoDto> addresses;

	public List<ActivityInfoDto> mapClassificationDataListToActivityInfoList(List<ClassificationDataDto> classificationDataList) {
		activities = new ArrayList<>();

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
		addresses = new ArrayList<>();
		CoordinateInfoDto newCoords = new CoordinateInfoDto();
		newCoords.setX(coordinateDto.getX());
		newCoords.setY(coordinateDto.getY());
		addresses = addressesDataList.stream().map(c -> mapClassificationDataDtoToAddressInfoDto(c, newCoords)).collect(Collectors.toList());
		return addresses;
	}

	public AddressInfoDto mapClassificationDataDtoToAddressInfoDto(AddressDto classificationDataDto, CoordinateInfoDto coordinateDto){
		AddressInfoDto address = new AddressInfoDto();
		address.setStreet_name(classificationDataDto.getAddress_name());
		address.setStreet_number(classificationDataDto.getStreet_number_1());
		address.setZip_code(classificationDataDto.getZip_code());
		address.setDistrict_id(classificationDataDto.getDistrict_id());
		address.setTown(classificationDataDto.getTown());
		address.setLocation(coordinateDto);

		return address;
	}
}
