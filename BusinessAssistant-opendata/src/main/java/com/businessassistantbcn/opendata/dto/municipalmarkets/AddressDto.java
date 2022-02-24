package com.businessassistantbcn.opendata.dto.municipalmarkets;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component("MunicipalMarketsAddressDto")
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

    @JsonGetter("street_name")
    public String getAddress_name() {
        return address_name;
    }


    @JsonSetter("address_name")
    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    @JsonGetter("number")
    public String getStreet_number_1() {
        return street_number_1;
    }

    @JsonSetter("street_number_1")
    public void setStreet_number_1(String street_number_1) {
        this.street_number_1 = street_number_1;
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

