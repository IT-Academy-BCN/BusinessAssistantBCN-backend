package com.businessassistantbcn.opendata.dto.municipalmarkets;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MunicipalMarketsTimeTableDto")
@Getter
@Setter
public class TimeTableDto {
    private Long id;
    private  String html;
}
