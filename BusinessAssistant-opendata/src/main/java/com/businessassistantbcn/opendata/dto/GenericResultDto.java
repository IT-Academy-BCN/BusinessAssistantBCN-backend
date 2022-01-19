package com.businessassistantbcn.opendata.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
public class GenericResultDto<T> {
	
    private int count;
    private int offset;
    private int limit;
    
    private T[] results;
    
}
