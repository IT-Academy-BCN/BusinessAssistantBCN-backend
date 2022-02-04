package com.businessassistantbcn.opendata.dto.municipalmarkets;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MunicipalMarketsCoordinateDto")
@Getter
@Setter
public class CoordinateDto {
    private Double x;
    private Double y;
}
