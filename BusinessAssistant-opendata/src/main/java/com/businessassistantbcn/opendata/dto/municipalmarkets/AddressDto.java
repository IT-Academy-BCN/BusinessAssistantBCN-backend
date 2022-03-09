package com.businessassistantbcn.opendata.dto.municipalmarkets;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"street_name","street_number","zip_code","town","district_id","location"})
public class AddressDto {
    @JsonIgnore
    @JsonProperty("place")
    public String getPlace() {
        return this.place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    private String place;

    @JsonIgnore
    @JsonProperty("district_name")
    public String getDistrict_name() {
        return this.district_name;
    }
    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }
    private String district_name;

    @JsonProperty("district_id")
    public String getDistrict_id() {
        return this.district_id;
    }
    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }
    private String district_id;

    @JsonIgnore
    @JsonProperty("neighborhood_name")
    public String getNeighborhood_name() {
        return this.neighborhood_name;
    }
    public void setNeighborhood_name(String neighborhood_name) {
        this.neighborhood_name = neighborhood_name;
    }
    private String neighborhood_name;

    @JsonIgnore
    @JsonProperty("neighborhood_id")
    public String getNeighborhood_id() {
        return this.neighborhood_id;
    }
    public void setNeighborhood_id(String neighborhood_id) {
        this.neighborhood_id = neighborhood_id;
    }
    private String neighborhood_id;

    @JsonAlias({"address_name", "street_name"})
    @JsonProperty("street_name")
    public String getStreet_name() {
        return this.street_name;
    }
    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }
    private String street_name;

    @JsonIgnore
    @JsonProperty("address_id")
    public String getAddress_id() {
        return this.address_id;
    }
    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }
    private String address_id;

    @JsonIgnore
    @JsonProperty("block_id")
    public Object getBlock_id() {
        return this.block_id;
    }
    public void setBlock_id(Object block_id) {
        this.block_id = block_id;
    }
    private Object block_id;
    
    
    @JsonAlias({"street_number", "start_street_number"})
    @JsonProperty("street_number")
    public int getStreet_number() {
        return this.street_number;
    }
    public void setStreet_number(int street_number) {
        this.street_number = street_number;
    }
    private int street_number;

    @JsonIgnore
    @JsonProperty("end_street_number")
    public Object getEnd_street_number() {
        return this.end_street_number;
    }
    public void setEnd_street_number(Object end_street_number) {
        this.end_street_number = end_street_number;
    }
    private Object end_street_number;

    @JsonIgnore
    @JsonProperty("street_number_1")
    public String getStreet_number_1() {
        return this.street_number_1;
    }
    public void setStreet_number_1(String street_number_1) {
        this.street_number_1 = street_number_1;
    }
    private String street_number_1;

    @JsonIgnore
    @JsonProperty("street_number_2")
    public Object getStreet_number_2() {
        return this.street_number_2;
    }
    public void setStreet_number_2(Object street_number_2) {
        this.street_number_2 = street_number_2;
    }
    private Object street_number_2;

    @JsonIgnore
    @JsonProperty("stairs")
    public Object getStairs() {
        return this.stairs;
    }
    public void setStairs(Object stairs) {
        this.stairs = stairs;
    }
    private Object stairs;

    @JsonIgnore
    @JsonProperty("level")
    public String getLevel() {
        return this.level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    private String level;

    @JsonIgnore
    @JsonProperty("door")
    public Object getDoor() {
        return this.door;
    }
    public void setDoor(Object door) {
        this.door = door;
    }
    private Object door;

    @JsonProperty("zip_code")
    public String getZip_code() {
        return this.zip_code;
    }
    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
    private String zip_code;

    @JsonIgnore
    @JsonProperty("province")
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    private String province;

    @JsonProperty("town")
    public String getTown() {
        return this.town;
    }
    public void setTown(String town) {
        this.town = town;
    }
    private String town;

    @JsonIgnore
    @JsonProperty("country")
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    private String country;

    @JsonIgnore
    @JsonProperty("comments")
    public Object getComments() {
        return this.comments;
    }
    public void setComments(Object comments) {
        this.comments = comments;
    }
    private Object comments;

    @JsonIgnore
    @JsonProperty("position")
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    private int position;

    @JsonIgnore
    @JsonProperty("main_address")
    public boolean getMain_address() {
        return this.main_address;
    }
    public void setMain_address(boolean main_address) {
        this.main_address = main_address;
    }
    private boolean main_address;

    @JsonIgnore
    @JsonProperty("road_name")
    public Object getRoad_name() {
        return this.road_name;
    }
    public void setRoad_name(Object road_name) {
        this.road_name = road_name;
    }
    private Object road_name;

    @JsonIgnore
    @JsonProperty("road_id")
    public Object getRoad_id() {
        return this.road_id;
    }
    public void setRoad_id(Object road_id) {
        this.road_id = road_id;
    }
    private Object road_id;

    @JsonIgnore
    @JsonProperty("roadtype_name")
    public Object getRoadtype_name() {
        return this.roadtype_name;
    }
    public void setRoadtype_name(Object roadtype_name) {
        this.roadtype_name = roadtype_name;
    }
    private Object roadtype_name;

    @JsonIgnore
    @JsonProperty("roadtype_id")
    public Object getRoadtype_id() {
        return this.roadtype_id;
    }
    public void setRoadtype_id(Object roadtype_id) {
        this.roadtype_id = roadtype_id;
    }
    private Object roadtype_id;

    
    @JsonProperty("location")
    public LocationDto getLocation() {
        return this.location;
    }
    public void setLocation(LocationDto location) {
        this.location = location;
    }
    private LocationDto location;

    @JsonIgnore
    @JsonProperty("related_entity")
    public Object getRelated_entity() {
        return this.related_entity;
    }
    public void setRelated_entity(Object related_entity) {
        this.related_entity = related_entity;
    }
    private Object related_entity;

    @JsonIgnore
    @JsonProperty("related_entity_data")
    public Object getRelated_entity_data() {
        return this.related_entity_data;
    }
    public void setRelated_entity_data(Object related_entity_data) {
        this.related_entity_data = related_entity_data;
    }
    private Object related_entity_data;

}
