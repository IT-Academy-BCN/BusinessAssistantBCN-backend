package com.businessassistantbcn.opendata.dto.bigmalls;

import java.util.List;

public class BigMallsDto {

    private int register_id;
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
    //private List<TicketDto> ticket_data;
    //private List<AddressDto> addresses;
    private List<EntityTypeDto> entity_type_data;
    //private List<AtributeCategoriesDto> attributes_categories;
    private List<ValuesDto> values;
    //private List<FromRelationshipDto> from_relationships;
    //private List<ToRelationshipDto> to_relationships;
    private List<ClassificationDataDto> classifications_data;
    private List<SecondaryDataDto> secondary_filters_data;
    private TimetableDto timetable;
    //private ImageDataDto image_data;
    //private List<GaleryDataDto> gallery_data;
    //private List<WarningDto> warnings;
    private CoordinateDto geo_epgs_25831;
    private CoordinateDto geo_epgs_23031;
    private CoordinateDto geo_epgs_4326;
    private boolean is_section_of_data;
    private List<SectionDataDto> sections_data;
    private String start_date;
    private String end_date;
    private String estimated_dates;
    private String languajes_data;
    private String type;
    private String type_name;
    private String period;
    private String period_name;
    private String event_status_name;
    private String event_status;
    private String ical;
}
