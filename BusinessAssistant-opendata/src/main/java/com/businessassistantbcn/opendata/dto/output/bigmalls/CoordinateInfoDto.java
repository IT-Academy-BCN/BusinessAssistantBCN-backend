package com.businessassistantbcn.opendata.dto.output.bigmalls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CoordinateInfoDto {

    private Double x;
    private Double y;

    public CoordinateInfoDto(){ //needed default?
        this.x = 0.0;
        this.y = 0.0;
    }

    public CoordinateInfoDto(Double x, Double y){
        this.x = x;
        this.y = y;

    }
}