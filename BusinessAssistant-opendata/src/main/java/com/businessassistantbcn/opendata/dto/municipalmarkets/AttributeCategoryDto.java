package com.businessassistantbcn.opendata.dto.municipalmarkets;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("MunicipalMarketsAttributeCategoryDto")
@Getter
@Setter
public class AttributeCategoryDto {
    private int id;
    private String name;
    private List<AttributeDto> attributes;
}
