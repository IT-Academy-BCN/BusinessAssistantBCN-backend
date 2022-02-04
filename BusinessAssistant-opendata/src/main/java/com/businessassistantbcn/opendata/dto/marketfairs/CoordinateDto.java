package com.businessassistantbcn.opendata.dto.marketfairs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MarketFairsCoordinateDto")
@Getter
@Setter
public class CoordinateDto {
    private Double x;
    private Double y;
}
