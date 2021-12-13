package com.businessassistantbcn.opendata.dto.commercialgaleries;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommercialGaleriesResponseDto {
    int count;
    CommercialGaleryDto[] elements;
}
