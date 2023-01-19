package com.businessassistantbcn.gencat.dto.output;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({})

public class RaiscResponseDto {

    //ENDPOINTS
    //1
    private String idRaisc;
    //11
    private String entity;
    //13
    private String raiscType;
    //14
    private Date anyo;
    //15
    private String titleCA;
    //16
    private String titleES;
    //25-URL
    private String basesCA;
    //26-URL
    private String basesES;
    //28
    private String subventionType;
    //29
    private String idRegion;
    //30
    private String region;
    //35
    private String idScope;
    //36
    private String scope;
    //39
    private String idSector;
    //40
    private String sector;
    //42
    private String origin;
    //43
    private Double maxBudgetPublish;
    //44
    private Double maxBudgetUE;
    //45
    private Double maxBudge;
    //46
    private Date startDate;
    //47
    private Date endDate;
    //50
    private String urlRequest;
    //65
    private String description;

}
