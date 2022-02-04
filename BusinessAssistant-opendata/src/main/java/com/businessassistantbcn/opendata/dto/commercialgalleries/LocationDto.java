package com.businessassistantbcn.opendata.dto.commercialgalleries;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component("CommercialGalleriesLocationDto")
public class LocationDto {

    private String type ;
    private List<GeometryDto> geometries;

}
