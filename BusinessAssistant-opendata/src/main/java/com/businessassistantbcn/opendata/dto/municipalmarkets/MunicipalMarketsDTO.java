package com.businessassistantbcn.opendata.dto.municipalmarkets;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MunicipalMarketsDTO {

    @JsonProperty("register_id")
    public String register_id;

    @JsonProperty("prefix")
    public String prefix;

    @JsonProperty("suffix")
    public String suffix;

    @JsonProperty("name")
    public String name;

    @JsonProperty("created")
    public String created;

    @JsonProperty("modified")
    public String modified;

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
    public List<Object> addresses;

    @JsonProperty("entity_types_data")
    public List<Object> entity_types_data;

    @JsonProperty("attribute_categories")
    public List<Object> attribute_categories;

    @JsonProperty("values")
    public List<Object> values;

    @JsonProperty("from_relationships")
    public List<Object> from_relationships;

    @JsonProperty("to_relationships")
    public List<Object> to_relationships;

    @JsonProperty("classifications_data")
    public List<Object> classifications_data;

    @JsonProperty("secondary_filters_data")
    public List<Object> secondary_filters_data;

    @JsonProperty("timetable")
    public Object timetable;

    @JsonProperty("image_data")
    public Object image_data;

    @JsonProperty("gallery_data")
    public List<Object> gallery_data;

    @JsonProperty("warnings")
    public List<Object> warnings;

    @JsonProperty("geo_epgs_25831")
    public Object geo_epgs_25831;

    @JsonProperty("geo_epgs_23031")
    public Object geo_epgs_23031;
    @JsonProperty("geo_epgs_4326")
    public Object geo_epgs_4326;

    @JsonProperty("is_section_of_data")
    public String is_section_of_data;
    public List<Object> sections_data;

    @JsonProperty("start_date")
    public String start_date;

    @JsonProperty("end_date")
    public String end_date;

    @JsonProperty("estimated_dates")
    public String estimated_dates;

    @JsonProperty("languages_data")
    public String languages_data;

    @JsonProperty("type")
    public String type;

    @JsonProperty("type_name")
    public String type_name;

    @JsonProperty("period")
    public String period;

    @JsonProperty("period_name")
    public String period_name;

    @JsonProperty("event_status_name")
    public String event_status_name;

    @JsonProperty("event_status")
    public String event_status;

    @JsonProperty("ical")
    public String ical;


}
