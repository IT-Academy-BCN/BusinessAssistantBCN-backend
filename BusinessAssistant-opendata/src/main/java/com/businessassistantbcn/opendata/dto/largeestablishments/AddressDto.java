package com.businessassistantbcn.opendata.dto.largeestablishments;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component("LargeEstablishmentsAddressDto")
@JsonIgnoreProperties({ "place","district_name",/*"district_id",*/ "neighborhood_name","neighborhood_id",
        /*"address_name",*/ "address_id","block_id","start_street_number","end_street_number",/*"street_number_1",*/
        "street_number_2","stairs","level","door",/*"zip_code",*/ "province",/*"town",*/"country","comments","position",
        "main_address","road_name","road_id","roadtype_name","roadtype_id",/*"location",*/ "related_entity","related_entity_data","street_number"})

 public class AddressDto {

    private String address_name;
    private String street_number_1;
    private String zip_code;
    private String district_id;
    private String town;
    private LocationDto location;

    @JsonGetter("address_name")
    public String getAddress_name() {
        return address_name;
    }

    @JsonGetter("street_number_1")
    public String getStreet_number_1() {
        return street_number_1;
    }

    @JsonGetter("zip_code")
    public String getZip_code() {
        return zip_code;
    }

    @JsonGetter("district_id")
    public String getDistrict_id() {
        return district_id;
    }

    @JsonGetter("town")
    public String getTown() {
        return town;
    }

    @JsonGetter("location")
    public LocationDto getLocation() {
        return location;
    }

}