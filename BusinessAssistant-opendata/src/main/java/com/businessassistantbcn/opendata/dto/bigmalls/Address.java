package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties({ "place","district_name",/*"district_id",*/ "neighborhood_name","neighborhood_name","neighborhood_id",
        /*"address_name",*/ "address_id","block_id","block_id","start_street_number","end_street_number",/*"street_number_1",*/
        "street_number_2","stairs","level","door",/*"zip_code",*/ "province",/*"town",*/"country","comments","position",
        "main_address","road_name","road_id","roadtype_name","roadtype_id",/*"location",*/ "related_entity","related_entity_data" })

public class Address {

    // Factorisize properties
    @JsonProperty("address_name")
    private String address_name;
    @JsonProperty("street_number_1")
    private String street_number_1;
    @JsonProperty("zip_code")
    private String zip_code;
    @JsonProperty("district_id")
    private String district_id;
    @JsonProperty("town")
    private String town;
    @JsonProperty("location")
    private Object location;

    // Default properties
    /*
    private String place;
    private String district_name;
    private String district_id;
    private String neighborhood_name;
    private String neighborhood_id;
    private String address_name;
    private String address_id;
    private String block_id;

    private int start_street_number;
    private int end_street_number;
    private String street_number_1;
    private String street_number_2;
    private String stairs;
    private String level;
    private String door;
    private String zip_code;
    private String province;
    private String town;
    private String country;
    private String comments;
    private String position;
    private boolean main_address;
    private String road_name;
    private String road_id;
    private String roadtype_name;
    private String roadtype_id;
    private Object location;
    private String related_entity;
    private String related_entity_data;
    */
}
