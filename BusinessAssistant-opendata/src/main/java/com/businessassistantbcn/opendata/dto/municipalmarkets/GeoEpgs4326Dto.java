package com.businessassistantbcn.opendata.dto.municipalmarkets;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoEpgs4326Dto {

    @JsonProperty("x")
    public double getX() {
        return this.x; }
    public void setX(double x) {
        this.x = x; }
    private double x;

    @JsonProperty("y")
    public double getY() {
        return this.y; }
    public void setY(double y) {
        this.y = y; }
    private double y;
}
