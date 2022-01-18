package com.businessassistantbcn.opendata.dto.largestablishments;


import com.fasterxml.jackson.annotation.*;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties({"place", "district_name","district_name","district_id","neighborhood_name","neighborhood_id"
        /*,"address_name"*/,"address_id","block_id","start_street_number","end_street_number"/*,"street_number_1"*/
        , "street_number_2", "stairs","level","door"/*,"zip_code"*/ ,"province"/*,"town"*/ ,"country","comments","position","main_address","road_name","road_id","roadtype_name","roadtype_id"
        /*,"location"*/,"related_entity","related_entity_data","street_number" })

public class AddressDto {
    private String address_name;
    private String street_number_1;
    private String zip_code;
    private String town;
    private LocationDto location;

    @JsonGetter("street_name")
    public String getAddress_name() {
        return address_name;
    }

    @JsonSetter("address_name")
    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    @JsonGetter("street_number")
    public String getStreet_number_1() {
        return street_number_1;
    }

    @JsonSetter("street_number_1")
    public void setStreet_number_1(String street_number_1) {
        this.street_number_1 = street_number_1;
    }

    @JsonGetter("zip_code")
    public String getZip_code() {
        return zip_code;
    }

    @JsonSetter("zip_code")
    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    @JsonGetter("town")
    public String getTown() {
        return town;
    }

    @JsonSetter("town")
    public void setTown(String town) {
        this.town = town;
    }

    @JsonGetter("location")
    public LocationDto getLocation() {
        return location;
    }

    @JsonSetter("location")
    public void setLocation(LocationDto location) {
        this.location = location;
    }

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
