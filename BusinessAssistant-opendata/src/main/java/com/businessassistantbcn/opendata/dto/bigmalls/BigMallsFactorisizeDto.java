package com.businessassistantbcn.opendata.dto.bigmalls;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@JsonIgnoreProperties({ "register_id","prefix","suffix",/*"name",*/"created","modified","status","status_name","core_type",
        "core_type_name","body","tickets_data", /*"addresses",*/"entity_types_data","attribute_categories", /*"values",*/
        "from_relationships", "to_relationships", /*"classifications_data",*/"secondary_filters_data","timetable",
        "image_data","gallery_data","warnings",/*"geo_epgs_25831",*/"geo_epgs_23031","geo_epgs_4326","is_section_of_data",
        "sections_data","start_date","end_date","estimated_dates","languages_data","type","type_name","period","period_name",
        "event_status_name","event_status","ical" })

public class BigMallsFactorisizeDto {

    //Factorisized properties
    @JsonProperty("name")
    private String name;
    @JsonProperty("classifications_data")
    private List<ClassificationData> classifications_data;
    @JsonProperty("values")
    private List<Value> values;
    @JsonProperty("addresses")
    private List<Address> addresses;
    @JsonProperty("geo_epgs_25831")
    private Coordinate geo_epgs_25831;
//    @JsonProperty("geo_epgs_23031")
//    private Coordinate geo_epgs_23031;
//    @JsonProperty("geo_epgs_4326")
//    private Coordinate geo_epgs_4326;

     /*TODO -clasification_data can be shared, is the same in largeStablishments.
            -select which geo_epgs_XXXX we want to work, comment/uncomment in @JsonIgnoreProperties*/

    // Default properties
    /*
    private long register_id;
    private String prefix;
    private String suffix;
    private String name;
    private String created;
    private String modified;
    private String status;
    private String status_name;
    private String core_type;
    private String core_type_name;
    private String body;
    private List<Object> tickets_data;
    private List<Object> addresses;
    private List<Object> entity_types_data;
    private List<Object> attribute_categories;
    private List<Object> values;
    private List<Object> from_relationships;
    private List<Object> to_relationships;
    private List<Object> classifications_data;
    private List<Object> secondary_filters_data;
    private Object timetable;
    private Object image_data;
    private List<Object> gallery_data;
    private List<Object> warnings;
    private CoordinateDto geo_epgs_25831;
    private CoordinateDto geo_epgs_23031;
    private CoordinateDto geo_epgs_4326;
    private boolean is_section_of_data;
    private List<Object> sections_data;
    private String start_date;
    private String end_date;
    private String estimated_dates;
    private String languages_data;
    private String type;
    private String type_name;
    private String period;
    private String period_name;
    private String event_status_name;
    private String event_status;
    private String ical;
    */


}