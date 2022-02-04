package com.businessassistantbcn.opendata.dto.largeestablishments;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter @Setter
@Component
public class Geometry {

    private String type;
    private List<Double> coordinates;
}
