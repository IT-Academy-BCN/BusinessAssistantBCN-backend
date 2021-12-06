package com.businessassistantbcn.login.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;


import java.util.Date;
import java.util.List;

//TODO @JsonPropertyOrder organize all properties order
@JsonPropertyOrder({"register_id","prefix","suffix","name","created","modified","status","status_name","core_type"
        ,"core_type_name","body","tickets_data","addresses","entity_types_data","attribute_categories","values"
        ,"from_relationships","to_relationships","classifications_data","secondary_filter_data","timetable","image_data"
        ,"gallery_data","warnings","geo_epgs_25831","geo_epgs_23031","geo_epgs_4326","is_section_of_data","start_date"
        ,"end_date","estimated_dates","languages_data","type","type_name","period","period_name","event_status_name"
        ,"event_status","ical"})


public class MercatMunicipalRootDTO {

    @JsonProperty("register_id")
    public Object register_id;
    @JsonProperty("prefix")
    public Object prefix;
    @JsonProperty("suffix")
    public Object suffix;
    @JsonProperty("name")
    public String name;
    //TODO Date.created and Date.modified is not the same format in file.json
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    public Date created;// dar formato dato (1984-12-28T00:00:00+01:00)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    public Date modified;//dar formato dato
    @JsonProperty("status")
    public String status;
    @JsonProperty("status_name")
    public String status_name;
    @JsonProperty("core_type")
    public String core_type;
    @JsonProperty("core_type_name")
    public String core_type_name;
    @JsonProperty("body")
    public String body;
    @JsonProperty("tickets_data")
    public List<Object> tickets_data;
    @JsonProperty("addresses")
    public List<Address> addresses;
    @JsonProperty("entity_types_data")
    public List<EntityTypesData> entity_types_data;
    @JsonProperty("attribute_categories")
    public List<AttributeCategory> attribute_categories;
    @JsonProperty("values")
    public List<Value> values;
    @JsonProperty("from_relationships")
    public List<FromRelationship> from_relationships;
    @JsonProperty("to_relationships")
    public List<ToRelationship> to_relationships;
    @JsonProperty("classifications_data")
    public List<ClassificationsData> classifications_data;
    @JsonProperty("secondary_filters_data")
    public List<SecondaryFiltersData> secondary_filters_data;
    @JsonProperty("timetable")
    public Timetable timetable;
    @JsonProperty("image_data")
    public ImageData image_data;
    @JsonProperty("gallery_data")
    public List<Object> gallery_data;
    @JsonProperty("warnings")
    public List<Warning> warnings;
    @JsonProperty("geo_epgs_25831")
    public GeoEpgs25831 geo_epgs_25831;
    @JsonProperty("geo_epgs_23031")
    public GeoEpgs23031 geo_epgs_23031;
    @JsonProperty("geo_epgs_4326")
    public GeoEpgs4326 geo_epgs_4326;
    @JsonProperty("is_section_of_data")
    public Object is_section_of_data;
    public List<SectionData> sections_data;
    @JsonProperty("start_date")
    public Object start_date;
    @JsonProperty("end_date")
    public Object end_date;
    @JsonProperty("estimated_dates")
    public Object estimated_dates;
    @JsonProperty("languages_data")
    public Object languages_data;
    @JsonProperty("type")
    public Object type;
    @JsonProperty("type_name")
    public Object type_name;
    @JsonProperty("period")
    public Object period;
    @JsonProperty("period_name")
    public Object period_name;
    @JsonProperty("event_status_name")
    public Object event_status_name;
    @JsonProperty("event_status")
    public Object event_status;
    @JsonProperty("ical")
    public String ical;

}
