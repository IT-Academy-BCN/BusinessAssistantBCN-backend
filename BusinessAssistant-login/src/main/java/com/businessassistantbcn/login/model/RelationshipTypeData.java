package com.businessassistantbcn.login.model;

public class RelationshipTypeData {
    public int id;
    public String direct;
    public String reverse;
    public int order;
    public int source_entity_type;
    public int target_entity_type;
    public SourceEntityTypeData source_entity_type_data;
    public TargetEntityTypeData target_entity_type_data;
}
