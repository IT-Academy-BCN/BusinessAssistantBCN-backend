package com.businessassistantbcn.login.model;

public class ToRelationship {
    public int id;
    public int order;
    public int relationship_type;
    public RelationshipTypeData relationship_type_data;
    public Object from_entity;
    public FromEntityData from_entity_data;
    public Object to_entity;
    public ToEntityData to_entity_data;
    public String observation;
}
