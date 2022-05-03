package com.businessassistantbcn.opendata.dto.commercialgalleries;

import java.util.List;

import org.springframework.stereotype.Component;

import com.businessassistantbcn.opendata.dto.ActivityInfoDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommercialGalleriesResponseDto {

	private String name;
	@JsonUnwrapped
    private List<ContactDto> values; // contact
    private List<ActivityInfoDto> activities; // activities
    private List<AddressDto> addresses;
}
