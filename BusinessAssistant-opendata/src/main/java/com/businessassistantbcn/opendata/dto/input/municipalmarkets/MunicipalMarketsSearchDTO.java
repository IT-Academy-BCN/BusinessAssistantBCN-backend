package com.businessassistantbcn.opendata.dto.input.municipalmarkets;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MunicipalMarketsSearchDTO {

    @NotNull
    private int[] zones;
}

