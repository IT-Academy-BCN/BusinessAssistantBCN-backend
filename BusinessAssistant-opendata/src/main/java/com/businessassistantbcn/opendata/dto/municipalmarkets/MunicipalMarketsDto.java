package com.businessassistantbcn.opendata.dto.municipalmarkets;

import com.fasterxml.jackson.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"name","web","email","phone","activity","addresses",})
public class MunicipalMarketsDto {

    @JsonIgnore
    @JsonProperty("register_id")
    private String register_id;

    @JsonIgnore
    @JsonProperty("prefix")
    private String prefix;

    @JsonIgnore
    @JsonProperty("suffix")
    private String suffix;

    @JsonProperty("web")
    private String web;

    public String getWeb() {
        try {
            List<String> z = getValues()
                    .stream()
                    .filter(t -> t.getEmail_value() != null)
                    .map(ValueDto::getEmail_value)
                    .collect(Collectors.toList());
            return z.get(0);
        }catch (Exception e){
            return "Null";
        }
    }

    @JsonProperty("email")
    private String email;
    public String getEmail() {
        try {
            List<String> z = getValues()
                    .stream()
                    .filter(t -> t.getEmail_value() != null)
                    .map(ValueDto::getEmail_value)
                    .collect(Collectors.toList());
            return z.get(0);
        }catch (Exception e){
            return "Null";
        }
    }

    @JsonProperty("phone")
    private String phone;
    public String getPhone() {
        List<String>z= getValues()
                .stream()
                .filter(t->"Telèfons".equals(t.getCategory_name()))
                .map(ValueDto::getValue)
                .collect(Collectors.toList());
        return z.get(0);
    }

    @JsonIgnore
    @JsonProperty("created")
    private String created;

    @JsonIgnore
    @JsonProperty("modified")
    private String modified;

    @JsonIgnore
    @JsonProperty("status")
    private String status;

    @JsonIgnore
    @JsonProperty("status_name")
    private String status_name;

    @JsonIgnore
    @JsonProperty("core_type")
    private String core_type;

    @JsonIgnore
    @JsonProperty("core_type_name")
    private String core_type_name;

    @JsonIgnore
    @JsonProperty("body")
    private String body;

    @JsonIgnore
    @JsonProperty("tickets_data")
    private List<TicketDto> tickets_data;

    @JsonProperty("name")
    private String name;

    @JsonProperty("addresses")
    private List<AddressDto> addresses;
    public List<AddressDto> getAddresses() {
        return addresses;
    }
    public void setAddress(List<AddressDto> addresses) {
        this.addresses=addresses;
    }

    @JsonIgnore
    @JsonProperty("entity_types_data")
    private List<EntityTypesDataDto> entity_types_data;

    @JsonIgnore
    @JsonProperty("attribute_categories")
    private List<AttributeCategoryDto> attribute_categories;

    @JsonIgnore
    @JsonProperty("values")
    private List<ValueDto> values;
    List<ValueDto> getValues() {
        return this.values; }
    void setValues( List<ValueDto> values) {
        this.values = values; }

    @JsonIgnore
    @JsonProperty("from_relationships")
    private List<FromRelationshipDto> from_relationships;

    @JsonIgnore
    @JsonProperty("to_relationships")
    private List<ToRelationshipDto> to_relationships;

    @JsonProperty("activity")
    private List<ClassificationsDataDto> classificationsData;

    public String getClassificationsData() {
        List<String>z=classificationsData.stream()
                .map(t-> t.getName())

                .collect(Collectors.toList());
        return z.get(0);
    }

    public void setClassifications_data(List<ClassificationsDataDto>classificationsData){
        this.classificationsData=classificationsData;
    }

    @JsonIgnore
    @JsonProperty("secondary_filters_data")
    private List<SecondaryFiltersDataDto> secondary_filters_data;

    @JsonIgnore
    @JsonProperty("timetable")
    private TimetableDto timetable;

    @JsonIgnore
    @JsonProperty("image_data")
    private ImageDataDto image_data;

    @JsonIgnore
    @JsonProperty("gallery_data")
    private List<GaleryDataDto> gallery_data;

    @JsonIgnore
    @JsonProperty("warnings")
    private List<WarningDto> warnings;

    @JsonIgnore
    @JsonProperty("geo_epgs_25831")
    private GeoEpgs25831Dto geo_epgs_25831;

    @JsonIgnore
    @JsonProperty("geo_epgs_23031")
    private GeoEpgs23031Dto geo_epgs_23031;

   // @JsonAlias({"geo_epgs_4326", "location"})
    @JsonProperty("location")
    private GeoEpgs4326Dto location;

    @JsonIgnore
    @JsonProperty("is_section_of_data")
    private String is_section_of_data;

    @JsonIgnore
    @JsonProperty("sections_data")
    private List<SectionDataDto> sections_data;

    @JsonIgnore
    @JsonProperty("start_date")
    private String start_date;

    @JsonIgnore
    @JsonProperty("end_date")
    private String end_date;

    @JsonIgnore
    @JsonProperty("estimated_dates")
    private String estimated_dates;

    @JsonIgnore
    @JsonProperty("languages_data")
    private String languages_data;

    @JsonIgnore
    @JsonProperty("type")
    private String type;

    @JsonIgnore
    @JsonProperty("type_name")
    private String type_name;

    @JsonIgnore
    @JsonProperty("period")
    private String period;

    @JsonIgnore
    @JsonProperty("period_name")
    private String period_name;

    @JsonIgnore
    @JsonProperty("event_status_name")
    private String event_status_name;

    @JsonIgnore
    @JsonProperty("event_status")
    private String event_status;

    @JsonIgnore
    @JsonProperty("ical")
    private String ical;



}
