package com.businessassistantbcn.opendata.dto.marketfairs;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class MarketFairsResponseDto {
    @JsonProperty("json_featuretype")
    public String json_featuretype;
    @JsonProperty("register_id")
    public String register_id;
    @JsonProperty("namet")
    public String name;
    @JsonProperty("institution_id")
    public String institution_id;
    @JsonProperty("institution_name")
    public String institution_name;
    @JsonProperty("created")
    public String created;
    @JsonProperty("modified")
    public String modified;
    @JsonProperty("adresses_roadtype_id")
    public String adresses_roadtype_id;
    @JsonProperty("adresses_roadtype_name")
    public String adresses_roadtype_name;
    @JsonProperty("adresses_road_id")
    public String adresses_road_id;
    @JsonProperty("adresses_road_name")
    public String adresses_road_name;
    @JsonProperty("adresses_start_street_number")
    public String adresses_start_street_number;
    @JsonProperty("adresses_end_street_number")
    public String adresses_end_street_number;
    @JsonProperty("adresses_neighborhood_id")
    public String adresses_neighborhood_id;
    @JsonProperty("adresses_neighborhood_name")
    public String adresses_neighborhood_name;
    @JsonProperty("adresses_district_id")
    public String adresses_district_id;
    @JsonProperty("adresses_district_name")
    public String adresses_district_name;
    @JsonProperty("adresses_zip_code")
    public String adresses_zip_code;
    @JsonProperty("adresses_town")
    public String adresses_town;
    @JsonProperty("adresses_main_adress")
    public String adresses_main_adress;
    @JsonProperty("adresses_type")
    public String adresses_type;
    @JsonProperty("values_id")
    public String values_id;
    @JsonProperty("values_attribute_id")
    public String values_attribute_id;
    @JsonProperty("values_category")
    public String values_category;
    @JsonProperty("values_attribute_name")
    public String values_attribute_name;
    @JsonProperty("values_value")
    public String values_value;
    @JsonProperty("values_outstanding")
    public String values_outstanding;
    @JsonProperty("values_description")
    public String values_description;
    @JsonProperty("secondary_filters_id")
    public String secondary_filters_id;
    @JsonProperty("secondary_filters_name")
    public String secondary_filters_name;
    @JsonProperty("secondary_filters_fullpath")
    public String secondary_filters_fullpath;
    @JsonProperty("secondary_filters_tree")
    public String secondary_filters_tree;
    @JsonProperty("secondary_filters_asia_id")
    public String secondary_filters_asia_id;
    @JsonProperty("geo_epgs_25831_x")
    public String geo_epgs_25831_x;
    @JsonProperty("geo_epgs_25831_y")
    public String geo_epgs_25831_y;
    @JsonProperty("geo_epgs_4326_x")
    public String geo_epgs_4326_x;
    @JsonProperty("geo_epgs_4326_y")
    public String geo_epgs_4326_y;
}
