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
    public List<Geometry> geometries;


    @JsonGetter("geometries")
    public List<Geometry> getGeometries() {
        return geometries;
    }

    @JsonSetter("geometries")
    public void setGeometries(List<Geometry> geometries) {
        this.geometries = geometries;
    }
}
