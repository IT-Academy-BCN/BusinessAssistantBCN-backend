package com.businessassistantbcn.gencat.dto.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"sid",/*"id",*/"position","created_at","created_meta","updated_at","updated_meta","meta_data","codi_raisc","codi_bdns",
        "entitat_oo_aa_o_departament",/*"entitat_oo_aa_o_departament_1",*/"tipus_de_convocat_ria_codi",/*"tipus_de_convocat_ria","any_de_la_convocat_ria","t_tol_convocat_ria_catal","t_tol_convocat_ria_castell",*/"codi_diari_oficial","diari_oficial_de_publicaci","data_diari_oficial",
        "url_diari_oficial","codificacio_bases_reg","descripcio_bases_reg","codi_bases_reg","bases_reguladores_diari",/*"url_catala_bases_reg","url_castella_bases_reguladores",*/"codi_tipus_d_instument",/*"tipus_d_instument_ajut","codi_regio_apli",
        "regio_apli",*/"subfinalitat_codi","subfinalitat","finalitat_rais_codi","finalitat_rais",/*"codi_finalitat_publica","finalitat_publica",*/"codi_tipus_de_beneficiaris","tipus_de_beneficiaris",/*"codi_sector_eco",
        "sector_eco_afectat",*/"origen_del_finan_ament_codi",/*"origen_del_finan_ament","import_finan_ament_sec_pub","import_finan_ament_ue","import_total_convocat_ria","data_inici_presentaci_sol","data_fi_presentaci_sol",*/"data_fi_termini_presentaci_sol_licitud","hora_fi_presentaci_sol_licituds",
        /*"seu_electr_nica",*/"data_fi_de_termini_de","ajut_d_estat","codi_ajut_estat_mecanisme","ajut_estat_mecanisme","codi_ajut_estat_reglament_ue","ajut_estat_reglament_ue","ajut_estat_referencia_ue","codi_ajut_estat","ajut_estat_objectius_del_1",
        "codi_impacte_de_genere","impacte_de_g_nere","codei_publicitat_de_les_concessions","publicitat_de_les_concessions","etiqueta",/*"objecte_de_la_convocat_ria",*/"administraci_codi","administraci_", "departament_o_entitat_local_d_adscripci_codi","departament_o_entitat_local_d_adscripci_"})

public class RaiscInputDto {

    private String idRaisc;
    private String entity;
    private String raiscType;
    private String year;
    private String title_CA;
    private String title_ES;
    private String bases_CA;
    private String bases_ES;
    private String subventionType;
    private String idRegion;
    private String region;
    private String idScope;
    private String scope;
    private String idSector;
    private String sector;
    private String origin;
    private String maxBudgetPublic;
    private String maxBudgetUE;
    private String maxBudget;
    private String startDate;
    private String endDate;
    private String urlRequest;
    private String description;
}
