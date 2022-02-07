package com.businessassistantbcn.opendata.dto.marketfairs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component("MarketFairsCoordinateDto")
public class CoordinateDto {

    private Double x;
    private Double y;
}
