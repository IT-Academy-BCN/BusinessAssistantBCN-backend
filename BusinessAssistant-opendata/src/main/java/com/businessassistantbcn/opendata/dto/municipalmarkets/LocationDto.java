package com.businessassistantbcn.opendata.dto.municipalmarkets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocationDto {
    @JsonIgnore
    @JsonProperty("type")
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    private String type;

    @JsonIgnore
    @JsonProperty("geometries")
    public ArrayList<GeometryDto> getGeometries() {
        return this.geometries;
    }
    public void setGeometries(ArrayList<GeometryDto> geometries) {
        this.geometries = geometries;
    }
    private ArrayList<GeometryDto> geometries;

    @JsonProperty("location")
    public List<ArrayList<Double>> getLocation() {
        return getGeometries()
                .stream()
                .map(GeometryDto::getCoordinates)
                .collect(Collectors.toList());
                  }
    private ArrayList<Double> location;

}


