package com.businessassistantbcn.opendata.dto.largeestablishments;

import com.businessassistantbcn.opendata.dto.bigmalls.BigMallsDto;
import com.businessassistantbcn.opendata.dto.commercialgalleries.CommercialGalleriesDto;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter @Setter
@AllArgsConstructor
@JsonIgnoreProperties({ "register_id","prefix","suffix",/*"name",*/"created","modified","status","status_name","core_type","core_type_name",
        "body","tickets_data",/*"addresses",*/"entity_types_data","attribute_categories"/*,"values"*/,"from_relationships","to_relationships",
        /*"classifications_data",*/"secondary_filters_data","timetable","image_data","gallery_data","warnings","geo_epgs_25831",
        "geo_epgs_23031"/*,"geo_epgs_4326"*/,"is_section_of_data","sections_data","start_date","end_date","estimated_dates",
        "languages_data","type","type_name","period","period_name","event_status_name","event_status","ical"
})

/**
 * @JsonGetter for json deserialization
 * @JsonSetter for json serialization
 */

public class LargeEstablishmentsDto extends CommercialGalleriesDto {

}
