package com.businessassistantbcn.opendata.dto.output.commercialgalleries;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class GeometryInfoDto {
    @JsonUnwrapped
    private List<Double> coordinates;

    private CoordinateInfoDto coordinateDto;

    @JsonGetter("coordinates")
    public CoordinateInfoDto getCoordinates() {

        coordinateDto = new CoordinateInfoDto();
        if(coordinates.size()>1){
            coordinateDto.setX(coordinates.get(0));
            coordinateDto.setY(coordinates.get(1));
        }
        return coordinateDto;
    }

}