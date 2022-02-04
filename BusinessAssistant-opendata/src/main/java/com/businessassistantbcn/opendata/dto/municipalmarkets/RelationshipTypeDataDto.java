package com.businessassistantbcn.opendata.dto.municipalmarkets;

public class RelationshipTypeDataDto {
    private int id;
    private String direct;
    private String reverse;
    private int order;
    private int source_entity_type;
    private int target_entity_type;
    private SourceEntityTypeDataDto source_entity_type_data;
    private TargetEntityTypeDataDto target_entity_type_data;
}
