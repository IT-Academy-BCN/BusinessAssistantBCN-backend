package com.businessassistantbcn.opendata.dto.commercialgalleries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;

@Component
public class DtoHelper {

	public static CommercialGalleriesResponseDto mapIncommingDtoToResponseDto(CommercialGalleriesDto incommingDto) {
		CommercialGalleriesResponseDto result = new CommercialGalleriesResponseDto();
		
		result.setName(incommingDto.getName());
		result.setValues(incommingDto.getValuesList());
		result.setAddresses(incommingDto.getAddresses());
		List<ClassificationDataDto> classificationDatas = incommingDto.getClassifications_data();
		result.setActivities(mapClassificationDataListToActivityInfoList(classificationDatas));
		
		return result;
	}

	private static List<ActivityInfoDto> mapClassificationDataListToActivityInfoList(List<ClassificationDataDto> classificationDataList) {
		List<ActivityInfoDto> activities = new ArrayList<ActivityInfoDto>();

		activities = classificationDataList.stream().map(c -> mapClassificationDataDtoToActivityInfoDto(c)).collect(Collectors.toList());
		return activities;
	}

	private static ActivityInfoDto mapClassificationDataDtoToActivityInfoDto(ClassificationDataDto classificationDataDto) {
		ActivityInfoDto activity = new ActivityInfoDto();
		activity.setActivityId(classificationDataDto.getId());
		activity.setActivityName(classificationDataDto.getName());
		
		return activity;
	}
}
