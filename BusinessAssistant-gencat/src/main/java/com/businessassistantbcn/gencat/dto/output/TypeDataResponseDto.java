package com.businessassistantbcn.gencat.dto.output;

import com.businessassistantbcn.gencat.dto.input.TypeDataDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TypeDataResponseDto {

    @JsonProperty("count")
    private int count;

    @JsonProperty("elements")
    private TypeDataDto[] elements;
}
