package com.businessassistantbcn.opendata.dto.marketfairs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MarketFairsSourceEntityTypeDataDto")
@Getter
@Setter
public class SourceEntityTypeDataDto {

    private int id;
    private String name;
}
