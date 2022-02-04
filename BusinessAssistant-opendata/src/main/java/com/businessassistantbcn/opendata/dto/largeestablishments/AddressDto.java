package com.businessassistantbcn.opendata.dto.largeestablishments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AddressDto {
	
    @JsonProperty("place")
    private String place;
    @JsonProperty("district_name")
    private String district_name;
    @JsonProperty("district_id")
    private String district_id;
    @JsonProperty("neighborhood_name")
    private String neighborhood_name;
    @JsonProperty("neighborhood_id")
    private String neighborhood_id;
    @JsonProperty("address_name")
    private String address_name;
    @JsonProperty("address_id")
    private String address_id;
    @JsonProperty("block_id")
    private String block_id;
    @JsonProperty("start_street_number")
    private int start_street_number;
    @JsonProperty("end_street_number")
    private int end_street_number;
    @JsonProperty("street_number_1")
    private String street_number_1;
    @JsonProperty("street_number_2")
    private String street_number_2;
    @JsonProperty("stairs")
    private String stairs;
    @JsonProperty("level")
    private String level;
    @JsonProperty("door")
    private String door;
    @JsonProperty("zip_code")
    private String zip_code;
    @JsonProperty("province")
    private String province;
    @JsonProperty("town")
    private String town;
    @JsonProperty("country")
    private String country;
    @JsonProperty("comments")
    private String comments;
    @JsonProperty("position")
    private String position;
    @JsonProperty("main_address")
    private boolean main_address;
    @JsonProperty("road_name")
    private String road_name;
    @JsonProperty("road_id")
    private String road_id;
    @JsonProperty("roadtype_name")
    private String roadtype_name;
    @JsonProperty("roadtype_id")
    private String roadtype_id;
    @JsonProperty("location")
    private LocationDto location;
    @JsonProperty("related_entity")
    private String related_entity;
    @JsonProperty("related_entity_data")
    private String related_entity_data;
    @JsonProperty("street_number")
    private String street_number;
    
}
