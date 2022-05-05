package com.businessassistantbcn.opendata.dto.input.municipalmarkets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GeometryDto{
    @JsonIgnore
    @JsonProperty("type")
    public String getType() {
        return this.type; }
    public void setType(String type) {
        this.type = type; }
    private String type;

    @JsonProperty("coordinates")
    public ArrayList<Double> getCoordinates() {
        return this.coordinates; }
    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates; }
    private ArrayList<Double> coordinates;
}