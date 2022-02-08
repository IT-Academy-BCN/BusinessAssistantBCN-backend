package com.businessassistantbcn.opendata.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import com.businessassistantbcn.opendata.dto.Comon.ActivityInfoDto;

@Component
@Getter @Setter
public class GenericResultDto<T> {
	
    public GenericResultDto(int length, int offset2, int limit2, ActivityInfoDto[] pagedDto) {
		// TODO Auto-generated constructor stub
	}

	private int count;
    private int offset;
    private int limit;
    
    private T[] results;
    
    
    public GenericResultDto() {}

    public GenericResultDto(int count, int offset, int limit, T[] results) {
        this.count = count;
        this.offset = offset;
        this.limit = limit;
        this.results = results;
    }

	public void setInfo(int i, int j, int k, ActivityInfoDto[] activityInfoDtos) {
		// TODO Auto-generated method stub
		
	}
}
