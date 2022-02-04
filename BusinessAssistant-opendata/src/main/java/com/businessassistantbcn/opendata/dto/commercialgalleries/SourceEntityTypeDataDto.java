package com.businessassistantbcn.opendata.dto.commercialgalleries;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("CommercialGalleriesSourceEntityTypeDataDto")
@Getter
@Setter
public class SourceEntityTypeDataDto {

    private int id;
    private String name;
}
