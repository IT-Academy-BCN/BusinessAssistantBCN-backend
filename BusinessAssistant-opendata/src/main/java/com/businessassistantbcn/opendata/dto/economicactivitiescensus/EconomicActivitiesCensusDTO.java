package com.businessassistantbcn.opendata.dto.economicactivitiescensus;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class EconomicActivitiesCensusDTO {
    @JsonProperty("json_featuretype")
    public String json_featuretype;
    @JsonProperty("Codi_Activitat_2019")
    public String Codi_Activitat_2019;
    @JsonProperty("Nom_Activitat")
    public String Nom_Activitat;
    @JsonProperty("Codi_Activitat_2016")
    public String Codi_Activitat_2016;
    @JsonProperty("Codi_Principal_Activitat")
    public String Codi_Principal_Activitat;
    @JsonProperty("Nom_Principal_Activitat")
    public String Nom_Principal_Activitat;
    @JsonProperty("Codi_Sector_Activitat")
    public String Codi_Sector_Activitat;
    @JsonProperty("Nom_Sector_Activitat")
    public String Nom_Sector_Activitat;
    @JsonProperty("Codi_Grup_Activitat")
    public String Codi_Grup_Activitat;
    @JsonProperty("Nom_Grup_Activitat")
    public String Nom_Grup_Activitat;
    @JsonProperty("Comentari_Activitat")
    public String Comentari_Activitat;
}
