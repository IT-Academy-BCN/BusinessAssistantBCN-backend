package com.businessassistantbcn.opendata.dto.marketfairs;

import com.businessassistantbcn.opendata.dto.bigmalls.CoordinateDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
public class MarketFairsDto {
    
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
    private List<Object> values;
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
    @JsonProperty("is_section_of_data")
    private Object is_section_of_data;
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
    
}
