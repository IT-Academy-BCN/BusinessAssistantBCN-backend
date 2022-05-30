package com.businessassistantbcn.opendata.dto.output;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.businessassistantbcn.opendata.dto.input.municipalmarkets.*;
import com.businessassistantbcn.opendata.dto.output.data.AddressInfoDto;
import com.businessassistantbcn.opendata.dto.output.data.CoordinateInfoDto;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalMarketsResponseDto {
	private static final Logger log = LoggerFactory.getLogger(MunicipalMarketsResponseDto.class);

	private String name;
	private List<String> web;
	private String email;
	private String phone;
	private List<AddressInfoDto> addresses;

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
		address.setAddress_name(classificationDataDto.getAddress_name());
		address.setStreet_number(classificationDataDto.getStreet_number_1());
		address.setZip_code(classificationDataDto.getZip_code());
		address.setTown(classificationDataDto.getTown());
		address.setDistrict_id(classificationDataDto.getDistrict_id());
		address.setLocation(coordinateDto);

		return address;
	}
}