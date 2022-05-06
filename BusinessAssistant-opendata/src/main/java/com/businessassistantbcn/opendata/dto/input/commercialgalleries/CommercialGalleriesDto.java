package com.businessassistantbcn.opendata.dto.input.commercialgalleries;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "register_id","prefix","suffix",/*"name",*/"created","modified","status","status_name","core_type","core_type_name",
        "body","tickets_data",/*"addresses",*/"entity_types_data","attribute_categories"/*,"values"*/,"from_relationships","to_relationships",
        /*"classifications_data",*/"secondary_filters_data","timetable","image_data","gallery_data","warnings","geo_epgs_25831",
        "geo_epgs_23031","geo_epgs_4326","is_section_of_data","sections_data","start_date","end_date","estimated_dates",
        "languages_data","type","type_name","period","period_name","event_status_name","event_status","ical"
})

/**
 * @JsonGetter for json deserialization
 * @JsonSetter for json serialization
 */
public class CommercialGalleriesDto {

        private String name;
    @JsonUnwrapped
    private List<ContactDto> values; // contact
    private List<ClassificationDataDto> classifications_data; // activities
    private List<AddressDto> addresses;

    @JsonGetter("activities") // deserialize clasification_data activities
    public List<ClassificationDataDto> getClassifications_data() {
        return classifications_data;
    }

    @JsonSetter("classifications_data")
    public void setClassifications_data(List<ClassificationDataDto> classifications_data) {
        this.classifications_data = classifications_data;
    }

    @JsonGetter("values")
    public ContactDto getValues() {

        ContactDto newContactDto = new ContactDto();;
        for(ContactDto c:values){

            if(c.getEmail_value() !=null){
                newContactDto.setEmail_value(c.getEmail_value());
            }

            if(c.getUrl_value() != null){
                newContactDto.setUrl_value(c.getUrl_value());
            }

            if(c.getPhone_value() != null){
                newContactDto.setPhone_value(c.getPhone_value());
            }
        }
        return newContactDto;
    }

    @JsonGetter("addresses")
    public List<AddressDto> getAddresses() {
        return addresses;
    }
	
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
    private List<TicketsDataDto> tickets_data;
    @JsonProperty("addresses")
    private List<AddressDto> addresses;
    @JsonProperty("entity_types_data")
    private List<EntityTypeDataDto> entity_types_data;
    @JsonProperty("attribute_categories")
    private List<AttributeCategoryDto> attribute_categories;
    @JsonProperty("values")
    private List<ContactDto> values;
    @JsonProperty("from_relationships")
    private List<FromRelationshipDto> from_relationships;
    @JsonProperty("to_relationships")
    private List<ToRelationshipDto> to_relationships;
    @JsonProperty("classifications_data")
    private List<ClassificationDataDto> classifications_data;
    @JsonProperty("secondary_filters_data")
    private List<SecondaryFilterDataDto> secondary_filters_data;
    @JsonProperty("timetable")
    private TimeTableDto timetable;
    @JsonProperty("image_data")
    private ImageDataDto image_data;
    @JsonProperty("gallery_data")
    private List<GalleryDataDto> gallery_data;
    @JsonProperty("warnings")
    private List<WarningDto> warnings;
    @JsonProperty("geo_epgs_25831")
    private CoordinateDto geo_epgs_25831;
    @JsonProperty("geo_epgs_23031")
    private CoordinateDto geo_epgs_23031;
    @JsonProperty("geo_epgs_4326")
    private CoordinateDto geo_epgs_4326;
    @JsonProperty("is_section_of_data")
    private boolean is_section_of_data;
    @JsonProperty("sections_data")
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
*/

}
