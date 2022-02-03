package com.businessassistantbcn.opendata.dto.municipalmarkets;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MunicipalMarketsSourceEntityTypeDataDto")
@Getter
@Setter
public class SourceEntityTypeDataDto {

    private int id;
    private String name;
}
