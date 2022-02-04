package com.businessassistantbcn.opendata.dto.municipalmarkets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component("MunicipalMarketsImageDataDto")
@Getter @Setter
@JsonIgnoreProperties({"cuts","alt"})
public class ImageDataDto {

    private Long id;
    private String name;
    private String image;
    private String title;
    private String alt;//
    private String author;
    private String source;//
    private String type_license;
    private String description;
    private String image_thumb;
    private String image_optimized;
//    private List<Cut> cuts;
}
