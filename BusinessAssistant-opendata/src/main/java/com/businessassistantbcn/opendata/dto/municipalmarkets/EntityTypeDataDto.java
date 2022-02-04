package com.businessassistantbcn.opendata.dto.municipalmarkets;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MunicipalMarketsEntityTypeDataDto")
@Getter
@Setter
public class EntityTypeDataDto {
	
    private int id;
    private String name;
    
}
