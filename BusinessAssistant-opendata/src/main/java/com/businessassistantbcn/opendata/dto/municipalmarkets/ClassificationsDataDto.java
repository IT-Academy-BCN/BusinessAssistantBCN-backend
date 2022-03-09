package com.businessassistantbcn.opendata.dto.municipalmarkets;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassificationsDataDto {

    private int id;

    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name){this.name=name;}

    private String full_path;
    private int dependency_group;
    private int parent_id;
    private int tree_id;
    private String asia_id;
    private String core_type;
    private int level;
}
