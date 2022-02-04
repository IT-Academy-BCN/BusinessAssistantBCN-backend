package com.businessassistantbcn.opendata.dto.municipalmarkets;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MunicipalMarketsDependencyGroupDataDto")
@Getter
@Setter

public class DependencyGroupDataDto {

    public String name;
    public Long id;
}
