package com.businessassistantbcn.opendata.dto.largestablishments;


import com.fasterxml.jackson.annotation.*;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
/* JsonIgnoreProperties for serializacion and deserialization */
@JsonIgnoreProperties({"register_id","prefix","name","suffix","created","modified","status","status_name","core_type","core_type_name","body","tickets_data"
        ,/*"addresses",*/"entity_types_data","attribute_categories",/*"values",*/"from_relationships","to_relationships",/*"classifications_data",*/"secondary_filters_data","timetable"
        ,"image_data","gallery_data","warnings",/*"geo_epgs_25831",*/ /*"geo_epgs_23031",*/ /*"geo_epgs_4326",*/ "is_section_of_data","sections_data"
        ,"start_date","end_date","estimated_dates","languages_data","type","type_name","period","period_name","event_status_name","event_status","ical" })

public class LargeStablishmentsDto {

    private List<ClassificationsDataDto> classifications_data;
    private List<WebMailPhoneDto> values;
    private List<AddressDto> addresses;
    private CoordinateDto geo_epgs_25831;
    private CoordinateDto geo_epgs_23031;
    private CoordinateDto geo_epgs_4326;

    @JsonGetter("activity")
    public List<ClassificationsDataDto> getClassifications_data() {
        return classifications_data;
    }

    @JsonSetter("classifications_data")
    public void setClassifications_data(List<ClassificationsDataDto> classifications_data) {
        this.classifications_data = classifications_data;
    }

    @JsonGetter("web_mail_phone")
    public List<WebMailPhoneDto> getValues() {
        return values;
    }

    @JsonSetter("values")
    public void setValues(List<WebMailPhoneDto> values) {
        this.values = values;
    }

    @JsonSetter("address")
    public List<AddressDto> getAddresses() {
        return addresses;
    }

    @JsonSetter("addresses")
    public void setAddresses(List<AddressDto> addresses) {
        this.addresses = addresses;
    }

    @JsonGetter("location_epgs_25831")
    public CoordinateDto getGeo_epgs_25831() {
        return geo_epgs_25831;
    }

    @JsonSetter("geo_epgs_25831")
    public void setGeo_epgs_25831(CoordinateDto geo_epgs_25831) {
        this.geo_epgs_25831 = geo_epgs_25831;
    }

    @JsonGetter("location_epgs_23031")
    public CoordinateDto getGeo_epgs_23031() {
        return geo_epgs_23031;
    }

    @JsonSetter("geo_epgs_23031")
    public void setGeo_epgs_23031(CoordinateDto geo_epgs_23031) {
        this.geo_epgs_23031 = geo_epgs_23031;
    }

    @JsonGetter("location_epgs_4326")
    public CoordinateDto getGeo_epgs_4326() {
        return geo_epgs_4326;
    }

    @JsonSetter("geo_epgs_4326")
    public void setGeo_epgs_4326(CoordinateDto geo_epgs_4326) {
        this.geo_epgs_4326 = geo_epgs_4326;
    }

    // Default properties below
    /*
    @JsonProperty("register_id")
    private long register_id;
    @JsonProperty("prefix")
    private String prefix;
    @JsonProperty("suffix")
    private String suffix;
    @JsonProperty("name")
    private String name;
    @JsonProperty("created")
    private String created;
    @JsonProperty("modified")
    private String modified;
    @JsonProperty("status")
    private String status;
    @JsonProperty("status_name")
    private String status_name;
    @JsonProperty("core_type")
    private String core_type;
    @JsonProperty("core_type_name")
    private String core_type_name;
    @JsonProperty("body")
    private String body;
    @JsonProperty("tickets_data")
    private List<Object> tickets_data;
    @JsonProperty("addresses")
    private List<Object> addresses;
    @JsonProperty("entity_types_data")
    private List<Object> entity_types_data;
    @JsonProperty("attribute_categories")
    private List<Object> attribute_categories;
    @JsonProperty("values")
    private List<Value> values;
    @JsonProperty("from_relationships")
    private List<Object> from_relationships;
    @JsonProperty("to_relationships")
    private List<Object> to_relationships;
    @JsonProperty("classifications_data")
    private List<Object> classifications_data;
    @JsonProperty("secondary_filters_data")
    private List<Object> secondary_filters_data;
    @JsonProperty("timetable")
    private Object timetable;
    @JsonProperty("image_data")
    private Object image_data;
    @JsonProperty("gallery_data")
    private List<Object> gallery_data;
    @JsonProperty("warnings")
    private List<Object> warnings;
    @JsonProperty("geo_epgs_25831")
    private CoordinateDto geo_epgs_25831;
    @JsonProperty("geo_epgs_23031")
    private CoordinateDto geo_epgs_23031;
    @JsonProperty("geo_epgs_4326")
    private CoordinateDto geo_epgs_4326;
    @JsonIgnore
    @JsonProperty("is_section_of_data")
    private boolean is_section_of_data;
    @JsonProperty("sections_data")
    private List<Object> sections_data;
    @JsonProperty("start_date")
    private String start_date;
    @JsonProperty("end_date")
    private String end_date;
    @JsonProperty("estimated_dates")
    private String estimated_dates;
    @JsonProperty("languages_data")
    private String languages_data;
    @JsonProperty("type")
    private String type;
    @JsonProperty("type_name")
    private String type_name;
    @JsonProperty("period")
    private String period;
    @JsonProperty("period_name")
    private String period_name;
    @JsonProperty("event_status_name")
    private String event_status_name;
    @JsonProperty("event_status")
    private String event_status;
    @JsonProperty("ical")
    private String ical;
    */


}
