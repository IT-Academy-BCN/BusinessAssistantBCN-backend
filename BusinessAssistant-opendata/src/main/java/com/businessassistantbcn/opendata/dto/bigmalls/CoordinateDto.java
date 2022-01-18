package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.stereotype.Component;

@Component
public class CoordinateDto {

    private float x;
    private float y;

    @JsonGetter("x")
    public float getX() {
        return x;
    }

    @JsonSetter("0")
    public void setX(float x) {
        this.x = x;
    }

    @JsonGetter("y")
    public float getY() {
        return y;
    }

    @JsonSetter("1")
    public void setY(float y) {
        this.y = y;
    }
}
