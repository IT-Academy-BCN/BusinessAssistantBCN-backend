package com.businessassistantbcn.opendata.dto.commercialgalleries;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("CommercialGalleriesTargetEntityTypeDataDto")
@Getter
@Setter
public class TargetEntityTypeDataDto {

    private int id;
    private String name;
}
