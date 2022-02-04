package com.businessassistantbcn.opendata.dto.commercialgalleries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("CommercialGalleriesSectionDataDto")
@Getter
@Setter
@JsonIgnoreProperties({"id"})
public class SectionDataDto {

//    private Long id;
    private PrefixDto prefix;
    private SuffixDto suffix;
    private String name;
    private String status;
    private String core_type;
    private String is_section_of_name;
}
