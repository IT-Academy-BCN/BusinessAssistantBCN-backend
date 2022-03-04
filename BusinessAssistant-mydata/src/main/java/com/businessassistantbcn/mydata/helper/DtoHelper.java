package com.businessassistantbcn.mydata.helper;

import org.springframework.stereotype.Component;

import com.businessassistantbcn.mydata.dto.SavedSearchResponseDto;
import com.businessassistantbcn.mydata.entities.Search;

@Component
public class DtoHelper {
	
	public static SavedSearchResponseDto mapSearchToSearchResponseDto(Search search) {
		SavedSearchResponseDto dto = new SavedSearchResponseDto();
		dto.setSearchUuid(search.getSearchUuid());
		dto.setSearchName(search.getSearchName());
		dto.setSearchDate(search.getSearchDate());
		dto.setSearchResult(search.getSearchResult());
		
		return dto;
	}

}
