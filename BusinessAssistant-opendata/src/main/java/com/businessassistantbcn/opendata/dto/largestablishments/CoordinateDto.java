package com.businessassistantbcn.opendata.dto.largestablishments;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CoordinateDto {

    private float x;
    private float y;

    @JsonGetter("x")
    public float getX() {
        return x;
    }

    @JsonSetter("x")
    public void setX(float x) {
        this.x = x;
    }

    @JsonGetter("y")
    public float getY() {
        return y;
    }

    @JsonSetter("y")
    public void setY(float y) {
        this.y = y;
    }
}
