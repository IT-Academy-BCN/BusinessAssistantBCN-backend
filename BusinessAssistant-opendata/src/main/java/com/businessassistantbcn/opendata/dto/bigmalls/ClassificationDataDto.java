package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties({ "full_path","dependency_group","parent_id","tree_id","asia_id","core_type","level" })
public class ClassificationDataDto {

    private int id;
    private String name;

    @JsonGetter("id")
    public int getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    // Default properties
    /*
    private int id;
    private String name;
    private String full_path;
    private int dependency_group;
    private int parent_id;
    private int tree_id;
    private String asia_id;
    private String core_type;
    private int level;
    */
}
