package com.businessassistantbcn.opendata.dto.input.marketfairs;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MarketFairsSearchDto {

    @NotNull
    private int[] zones;
}