package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@JsonIgnoreProperties({"tickets_data","entity_types_data","attribute_categories", "values","from_relationships", "to_relationships",
        "classifications_data","secondary_filters_data","timetable","image_data","gallery_data","warnings","sections_data"})
public class BigMallsDto {

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
    @JsonIgnore
    private List<TicketDto> tickets_data;
    //prueba con objetos object directamente
    @JsonProperty("addresses")
    private List<Object> addresses;
    @JsonIgnore
    private List<EntityTypeDto> entity_types_data;
    @JsonIgnore
    private List<AtributeCategoriesDto> attribute_categories;
    @JsonIgnore
    private List<ValuesDto> values;
    @JsonIgnore
    private List<FromRelationshipDto> from_relationships;
    @JsonIgnore
    private List<ToRelationshipDto> to_relationships;
    @JsonIgnore
    private List<ClassificationDataDto> classifications_data;
    @JsonIgnore
    private List<SecondaryDataDto> secondary_filters_data;
    @JsonIgnore
    private TimetableDto timetable;
    @JsonIgnore
    private ImageDataDto image_data;
    @JsonIgnore
    private List<GaleryDataDto> gallery_data;
    @JsonIgnore
    private List<WarningDto> warnings;
    @JsonProperty("geo_epgs_25831")
    private CoordinateDto geo_epgs_25831;
    @JsonProperty("geo_epgs_23031")
    private CoordinateDto geo_epgs_23031;
    @JsonProperty("geo_epgs_4326")
    private CoordinateDto geo_epgs_4326;
    @JsonProperty("is_section_of_data")
    private boolean is_section_of_data;
    @JsonIgnore
    private List<SectionDataDto> sections_data;
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
