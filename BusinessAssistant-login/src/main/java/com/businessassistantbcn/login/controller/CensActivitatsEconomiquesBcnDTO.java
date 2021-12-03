package com.businessassistantbcn.login.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CensActivitatsEconomiquesBcnDTO {

	@JsonProperty("json_featuretype")
	private String json_featuretype;
	
	@JsonProperty("Codi_Activitat_2019")
	private String Codi_Activitat_2019;
	
	@JsonProperty("Nom_Activitat")
	private String Nom_Activitat;
	
	@JsonProperty("Codi_Activitat_2016")
	private String Codi_Activitat_2016;
	
	@JsonProperty("Codi_Principal_Activitat")
	private String Codi_Principal_Activitat;
	
	@JsonProperty("Nom_Principal_Activitat")
	private String Nom_Principal_Activitat;
	
	@JsonProperty("Codi_Sector_Activitat")
	private String Codi_Sector_Activitat;
	
	@JsonProperty("Nom_Sector_Activitat")
	private String Nom_Sector_Activitat;
	
	@JsonProperty("Codi_Grup_Activitat")
	private String Codi_Grup_Activitat;
	
	@JsonProperty("Nom_Grup_Activitat")
	private String Nom_Grup_Activitat;
	
	@JsonProperty("Comentari_Activitat")
	private String Comentari_Activitat;

}