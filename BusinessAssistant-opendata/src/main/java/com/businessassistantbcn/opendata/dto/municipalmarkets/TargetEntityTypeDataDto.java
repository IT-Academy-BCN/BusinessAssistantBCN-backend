package com.businessassistantbcn.opendata.dto.municipalmarkets;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MunicipalMarketsTargetEntityTypeDataDto")
@Getter
@Setter
public class TargetEntityTypeDataDto {

    private int id;
    private String name;
}
