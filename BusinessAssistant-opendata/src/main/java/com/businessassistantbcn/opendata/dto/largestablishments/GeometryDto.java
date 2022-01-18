package com.businessassistantbcn.opendata.dto.largestablishments;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@JsonIgnoreProperties({"type"})
public class GeometryDto {
//    public String type;
    public List<Double> coordinates;

    @JsonGetter("coordinates")
    public List<Double> getCoordinates() {
        return coordinates;
    }

    @JsonSetter("coordinates")
    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
