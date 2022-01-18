package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@JsonIgnoreProperties({"type"})
public class LocationDto {

    private List<GeometryDto> geometries;

    @JsonGetter("geometries")
    public List<GeometryDto> getGeometries() {
        return geometries;
    }

    @JsonSetter("geometries")
    public void setGeometries(List<GeometryDto> geometries) {
        this.geometries = geometries;
    }
}
