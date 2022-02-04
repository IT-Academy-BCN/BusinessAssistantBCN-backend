package com.businessassistantbcn.opendata.dto.municipalmarkets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("MunicipalMarketsWarningDto")
@Getter
@Setter
public class WarningDto {

    private Long id;
    private String text;
    private String type;
    private DependencyGroupDataDto dependency_group_data;
    private int dependency_group;
    private Date start_date;
}
