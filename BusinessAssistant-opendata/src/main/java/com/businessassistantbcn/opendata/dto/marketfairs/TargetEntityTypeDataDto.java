package com.businessassistantbcn.opendata.dto.marketfairs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MarketFairsTargetEntityTypeDataDto")
@Getter
@Setter
public class TargetEntityTypeDataDto {

    private int id;
    private String name;
}
