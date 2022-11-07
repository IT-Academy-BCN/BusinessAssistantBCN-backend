package com.businessassistantbcn.opendata.dto.input;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SearchDTO {

    private int[] zones;
    private int[] activities;
}
