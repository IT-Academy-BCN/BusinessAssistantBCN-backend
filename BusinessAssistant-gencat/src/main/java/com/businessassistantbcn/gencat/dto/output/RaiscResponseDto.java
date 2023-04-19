package com.businessassistantbcn.gencat.dto.output;


import lombok.*;
import org.springframework.stereotype.Component;


@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaiscResponseDto {

    //ENDPOINTS
    //1
    private String idRaisc;
    //11
    private String entity;
    //13
    private String raiscType;
    //14
    private String anyo;
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
    private String maxBudgetPublish;
    //44
    private String maxBudgetUE;
    //45
    private String maxBudge;
    //46
    private String startDate;
    //47
    private String endDate;
    //50
    private String urlRequest;
    //65
    private String description;

}
