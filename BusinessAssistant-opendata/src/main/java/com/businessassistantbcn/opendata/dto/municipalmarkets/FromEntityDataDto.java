package com.businessassistantbcn.opendata.dto.municipalmarkets;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MunicipalMarketsFromEntityDataDto")
@Getter
@Setter
public class FromEntityDataDto {

    private Integer id;
    private String prefix;
    private String suffix;
    private String name;
    private String status;
    private String core_type;
    private String is_section_of_name;


}
