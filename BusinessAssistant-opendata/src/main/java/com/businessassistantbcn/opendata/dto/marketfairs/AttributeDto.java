package com.businessassistantbcn.opendata.dto.marketfairs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("MarketFairsAttributeDto")
@Getter
@Setter
@JsonIgnoreProperties({"options"})

public class AttributeDto {
    private Long id;
    private String name;
    private String description;
    private int category;
    private String type;
    private List<OptionDto> options;
    private List<ValueDto> values;
}

