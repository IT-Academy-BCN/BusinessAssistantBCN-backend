package com.businessassistantbcn.opendata.dto.commercialgalleries;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter @Setter
@Component("CommercialGalleriesGeometryDto")
public class GeometryDto {

    private String type;
    private List<Double> coordinates;
}
