package com.businessassistantbcn.opendata.dto.output;

import java.util.List;

import org.springframework.stereotype.Component;

import com.businessassistantbcn.opendata.dto.input.municipalmarkets.ActivityDto;
import com.businessassistantbcn.opendata.dto.input.municipalmarkets.AddressDto;
import com.businessassistantbcn.opendata.dto.input.municipalmarkets.ClassificationsDataDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalMarketsResponseDto {

	private String name;
	private List<String> web;
	private String email;
	private String phone;
	private ActivityDto activity;
	private List<AddressDto> addresses;

	public ActivityDto mapClassificationDataDtoToActivityDto(ClassificationsDataDto classificationsDataDto) {
		ActivityDto activity = new ActivityDto();
		activity.setActivityId(classificationsDataDto.getId());
		activity.setActivityName(classificationsDataDto.getName());

		return activity;
	}
}