package com.businessassistantbcn.opendata.dto.municipalmarkets;

public class ToRelationshipDto {
    private int id;
    private int order;
    private int relationship_type;
    private RelationshipTypeDataDto relationship_type_data;
    private long from_entity;
    private FromEntityDataDto from_entity_data;
    private long to_entity;
    private ToEntityDataDto to_entity_data;
    private Object observation;
}
