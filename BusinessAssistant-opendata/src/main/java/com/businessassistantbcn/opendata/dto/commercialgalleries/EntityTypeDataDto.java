package com.businessassistantbcn.opendata.dto.commercialgalleries;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("CommercialGalleriesEntityTypeDataDto")
@Getter
@Setter
public class EntityTypeDataDto {
	
    private int id;
    private String name;
    
}
