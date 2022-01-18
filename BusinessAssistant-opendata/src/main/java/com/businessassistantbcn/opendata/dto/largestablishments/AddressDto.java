package com.businessassistantbcn.opendata.dto.largestablishments;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties({"place", "district_name","district_name","district_id","neighborhood_name","neighborhood_id"
        ,"address_id","block_id","start_street_number","end_street_number", "street_number_2", "stairs","level","door"
        ,"province","country","comments","position","main_address","road_name","road_id","roadtype_name","roadtype_id"
        ,"location","related_entity","related_entity_data","street_number" })

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Address {

    // Factorisized properties
    @JsonProperty("address_name")
    private String address_name;
    @JsonProperty("street_number_1")
    private String street_number_1;
    @JsonProperty("zip_code")
    private String zip_code;
    @JsonProperty("town")
    private String town;

    // Default properites below
    /*
    private Object place;
    private String district_name;
    private String district_id; //*
    private String neighborhood_name;
    private String neighborhood_id;
    private String address_name; //*
    private String address_id; //*
    private Object block_id;
    private Integer start_street_number;
    private Integer end_street_number;
    private String street_number_1;
    private Object street_number_2;
    private Object stairs;
    private String level;
    private String door;
    private String zip_code;
    private String province;
    private String town;
    private String country;
    private Object comments;
    private int position;
    private Boolean main_address;
    private Object road_name;
    private Object road_id;
    private String roadtype_name;
    private String roadtype_id;
    private Location location;
    private Object related_entity;
    private Object related_entity_data;
    private String street_number;
    */

}
