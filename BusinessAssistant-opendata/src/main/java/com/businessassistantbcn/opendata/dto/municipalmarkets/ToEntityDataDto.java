package com.businessassistantbcn.opendata.dto.municipalmarkets;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MunicipalMarketsToEntityDataDto")
@Getter
@Setter
public class ToEntityDataDto {
    private Long id;
    private String prefix;
    private String suffix;
    private String name;
    private String status;
    private String core_type;
    private String is_section_of_name;
}
