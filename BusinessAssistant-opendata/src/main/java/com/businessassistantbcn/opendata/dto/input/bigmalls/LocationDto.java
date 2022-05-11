package com.businessassistantbcn.opendata.dto.input.bigmalls;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component("BigMallsLocationDto")
@JsonIgnoreProperties({"type"})

public class LocationDto {

    @JsonUnwrapped
    private List<GeometryDto> geometries;

    @JsonGetter("geometries")
    public CoordinateDto getGeometries() {

        if(geometries != null){
            return geometries.get(0).getCoordinates();
        }
        return new CoordinateDto(0.0,0.0); // TODO needed default coordinate?
    }

    public void setNewGeometries(CoordinateDto coordinateDto){
        System.out.println("rebo a LocationDto: "+coordinateDto.getX());
        System.out.println("envio a GeometryDto: "+coordinateDto.getX());
        CoordinateDto newCoordinateDto = geometries.get(0).setNewCoordinates(coordinateDto);
        System.out.println("rebo de GeometryDto: "+newCoordinateDto.getX());
        GeometryDto newGeometryDto = new GeometryDto();
        newGeometryDto.setCoordinateDto(newCoordinateDto);
        System.out.println("per trepitjar: "+newGeometryDto.getCoordinateDto().getX());
        //geometries.set(0, newGeometryDto); //error 500
        //geometries.add(0, newGeometryDto); //error 500
    }

}
