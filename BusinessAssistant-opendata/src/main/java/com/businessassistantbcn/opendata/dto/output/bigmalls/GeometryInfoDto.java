package com.businessassistantbcn.opendata.dto.output.bigmalls;

import com.businessassistantbcn.opendata.dto.input.bigmalls.CoordinateDto;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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