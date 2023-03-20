package com.businessassistantbcn.mydata.helper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.businessassistantbcn.mydata.dto.SaveSearchRequestDto;
import com.businessassistantbcn.mydata.dto.SaveSearchResponseDto;
import com.businessassistantbcn.mydata.entity.UserSearch;

@Component
public class DtoHelper {
	
	public static SaveSearchResponseDto mapSearchToSaveSearchResponseDto(UserSearch userSearch) {
		SaveSearchResponseDto dto = new SaveSearchResponseDto();
		dto.setSearchUuid(userSearch.getSearchUuid());
		dto.setUserUuid(userSearch.getUserUuid());
		dto.setSearchDate(userSearch.getSearchDate());
		dto.setSearchName(userSearch.getSearchName());
		dto.setSearchDetail(userSearch.getSearchDetail());
		dto.setSearchResult(userSearch.getSearchResult());
		
		return dto;
	}
	
	public static UserSearch mapSaveSearchRequestDtoToSearch(SaveSearchRequestDto requestDto, String user_uuid) {
		UserSearch userSearch = new UserSearch();
		userSearch.setSearchName(requestDto.getSearchName());
		userSearch.setSearchDetail(requestDto.getSearchDetail());
		userSearch.setSearchResult(requestDto.getSearchResult());
		userSearch.setUserUuid(user_uuid);
		userSearch.setSearchDate(new Date());
		
		return userSearch;
	}

}