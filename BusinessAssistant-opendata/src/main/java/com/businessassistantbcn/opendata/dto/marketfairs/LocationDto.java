package com.businessassistantbcn.opendata.dto.marketfairs;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component("MarketFairsLocationDto")
public class LocationDto {

    private String type ;
    private List<Geometry> geometries;

}
