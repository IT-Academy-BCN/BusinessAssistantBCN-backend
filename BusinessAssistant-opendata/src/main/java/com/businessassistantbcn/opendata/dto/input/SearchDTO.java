package com.businessassistantbcn.opendata.dto.input;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SearchDTO {

    @NotNull
    private int[] zones;
    @NotNull
    private int[] activities;
}
