package com.businessassistantbcn.opendata.dto.output.commercialgalleries;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class LocationInfoDto {

    private List<GeometryInfoDto> geometries;

    public LocationInfoDto(){
    }

    public CoordinateInfoDto getGeometries() {

        if(geometries != null){
            return geometries.get(0).getCoordinates();
        }
        return new CoordinateInfoDto(0.0,0.0); // needed default coordinate?
    }

}
