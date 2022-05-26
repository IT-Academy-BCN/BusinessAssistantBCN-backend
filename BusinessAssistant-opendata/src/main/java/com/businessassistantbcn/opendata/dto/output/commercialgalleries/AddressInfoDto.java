package com.businessassistantbcn.opendata.dto.output.commercialgalleries;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AddressInfoDto
{
    private String address_name;
    private String street_number;
    private String zip_code;
    private String district_id;
    private String town;
    private CoordinateInfoDto location;

    public AddressInfoDto() { }

    //Method overload not used
    public AddressInfoDto(String address_name, String street_number_1,String zip_code, String district_id, String town, CoordinateInfoDto location)
    {
        this.address_name = address_name;
        this.street_number = street_number_1;
        this.zip_code = zip_code;
        this.district_id = district_id;
        this.town = town;
        this.location = location;
    }

}
