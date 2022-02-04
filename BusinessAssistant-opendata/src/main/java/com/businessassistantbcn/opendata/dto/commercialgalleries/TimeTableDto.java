package com.businessassistantbcn.opendata.dto.commercialgalleries;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("CommercialGalleriesTimeTableDto")
@Getter
@Setter
public class TimeTableDto {
    private Long id;
    private  String html;
}
