package com.businessassistantbcn.mydata.helper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.businessassistantbcn.mydata.dto.SaveSearchRequestDto;
import com.businessassistantbcn.mydata.dto.SaveSearchResponseDto;
import com.businessassistantbcn.mydata.entities.Search;

@Component
public class DtoHelper {
	
	public static SaveSearchResponseDto mapSearchToSaveSearchResponseDto(Search search) {
		SaveSearchResponseDto dto = new SaveSearchResponseDto();
		dto.setSearchUuid(search.getSearchUuid());
		if(dto.getUserUuid()!=null)
			dto.setUserUuid(search.getUserUuid());
		dto.setSearchDate(search.getSearchDate());
		dto.setSearchName(search.getSearchName());
		if(dto.getSearchDetail()!=null)
			dto.setSearchDetail(search.getSearchDetail());
		if(dto.getSearchResult()!=null)
			dto.setSearchResult(search.getSearchResult());
		
		return dto;
	}
	
	public static Search mapSaveSearchRequestDtoToSearch(SaveSearchRequestDto dto, String user_uuid) {
		Search search = new Search();
		search.setSearchName(dto.getSearchName());
		search.setSearchDetail(dto.getSearchDetail());
		search.setSearchResult(dto.getSearchResult());
		search.setUserUuid(user_uuid);
		search.setSearchDate(new Date());
		
		return search;
	}

}