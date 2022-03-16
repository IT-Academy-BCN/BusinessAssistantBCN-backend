package com.businessassistantbcn.opendata.dto.commercialgalleries;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component("CommercialGalleriesLocationDto")
@JsonIgnoreProperties({"type"})
public class LocationDto {

    @JsonUnwrapped
    private List<GeometryDto> geometries;

    @JsonGetter("geometries")
    public CoordinateDto getGeometries() {

        if(geometries != null){
            return geometries.get(0).getCoordinates();
        }
        return new CoordinateDto(0.0,0.0);
    }


}
