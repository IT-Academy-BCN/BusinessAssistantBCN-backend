package com.businessassistantbcn.opendata.dto.largeestablishments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter

@JsonIgnoreProperties({"from_entity","from_entity_data","observation","to_entity"})
public class ToRelationshipDto {
    private Long id;
    private int order;
    private int relationship_type;
    private RelationshipTypeDataDto relationship_type_data;
//    private Integer from_entity;
//    private FromEntityDataDto from_entity_data;
//    private Long to_entity;
    private ToEntityDataDto to_entity_data;
//    private String observation;
}
