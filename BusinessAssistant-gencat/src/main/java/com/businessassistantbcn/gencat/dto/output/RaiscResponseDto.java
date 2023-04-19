package com.businessassistantbcn.gencat.dto.output;


import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RaiscResponseDto that = (RaiscResponseDto) o;
        return Objects.equals(getIdRaisc(), that.getIdRaisc()) && Objects.equals(getEntity(), that.getEntity()) && Objects.equals(getRaiscType(), that.getRaiscType()) && Objects.equals(getAnyo(), that.getAnyo()) && Objects.equals(getTitleCA(), that.getTitleCA()) && Objects.equals(getTitleES(), that.getTitleES()) && Objects.equals(getBasesCA(), that.getBasesCA()) && Objects.equals(getBasesES(), that.getBasesES()) && Objects.equals(getSubventionType(), that.getSubventionType()) && Objects.equals(getIdRegion(), that.getIdRegion()) && Objects.equals(getRegion(), that.getRegion()) && Objects.equals(getIdScope(), that.getIdScope()) && Objects.equals(getScope(), that.getScope()) && Objects.equals(getIdSector(), that.getIdSector()) && Objects.equals(getSector(), that.getSector()) && Objects.equals(getOrigin(), that.getOrigin()) && Objects.equals(getMaxBudgetPublish(), that.getMaxBudgetPublish()) && Objects.equals(getMaxBudgetUE(), that.getMaxBudgetUE()) && Objects.equals(getMaxBudge(), that.getMaxBudge()) && Objects.equals(getStartDate(), that.getStartDate()) && Objects.equals(getEndDate(), that.getEndDate()) && Objects.equals(getUrlRequest(), that.getUrlRequest()) && Objects.equals(getDescription(), that.getDescription());
    }
}
