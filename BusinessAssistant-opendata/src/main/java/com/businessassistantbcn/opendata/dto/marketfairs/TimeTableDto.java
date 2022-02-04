package com.businessassistantbcn.opendata.dto.marketfairs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MarketFairsTimeTableDto")
@Getter
@Setter
public class TimeTableDto {
    private Long id;
    private  String html;
}
