package com.businessassistantbcn.opendata.dto.commercialgalleries;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("CommercialGalleriesCoordinateDto")
@Getter
@Setter
public class CoordinateDto {
    private Double x;
    private Double y;
}
