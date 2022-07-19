package com.businessassistantbcn.gencat.dto.input;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class TypeDataDto {

    private int idType;
    private String typeName;

    public TypeDataDto(int idType, String typeName) {
        this.idType = idType;
        this.typeName = typeName;
    }

    @JsonGetter("idType")
    public int getId() {
        return idType;
    }

    @JsonSetter("id")
    public void setId(int id) {
        this.idType = id;
    }

    @JsonGetter("typeName")
    public String getName() {
        return typeName;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.typeName = name;
    }
}
