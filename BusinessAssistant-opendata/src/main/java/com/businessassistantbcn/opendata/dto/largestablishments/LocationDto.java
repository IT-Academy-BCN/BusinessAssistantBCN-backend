package com.businessassistantbcn.opendata.dto.largestablishments;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@JsonIgnoreProperties({"type"})
public class LocationDto {
//    private String type;
    public List<GeometryDto> geometries;


    @JsonGetter("geometries")
    public List<GeometryDto> getGeometries() {
        return geometries;
    }

    @JsonSetter("geometries")
    public void setGeometries(List<GeometryDto> geometries) {
        this.geometries = geometries;
    }
}
