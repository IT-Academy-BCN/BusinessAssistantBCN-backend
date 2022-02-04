package com.businessassistantbcn.opendata.dto.marketfairs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MarketFairsEntityTypeDataDto")
@Getter
@Setter
public class EntityTypeDataDto {
	
    private int id;
    private String name;
    
}
