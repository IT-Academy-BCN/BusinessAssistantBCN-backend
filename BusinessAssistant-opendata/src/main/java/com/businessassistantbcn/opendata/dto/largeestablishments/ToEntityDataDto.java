package com.businessassistantbcn.opendata.dto.largeestablishments;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
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
