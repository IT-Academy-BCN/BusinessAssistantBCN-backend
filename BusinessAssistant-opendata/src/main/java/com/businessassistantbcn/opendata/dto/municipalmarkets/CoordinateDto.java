package com.businessassistantbcn.opendata.dto.municipalmarkets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component("MunicipalMarketsCoordinateDto")

public class CoordinateDto {

    private Double x;
    private Double y;

}
