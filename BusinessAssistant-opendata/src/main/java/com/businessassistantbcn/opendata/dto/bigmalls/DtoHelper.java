package com.businessassistantbcn.opendata.dto.bigmalls;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;


public class DtoHelper {

	public static BigMallsResponseDto mapIncommingDtoToResponseDto(BigMallsDto incommingDto) {
		BigMallsResponseDto result = new BigMallsResponseDto();
		
		result.setName(incommingDto.getName());
		result.setValue(incommingDto.getValues());
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
