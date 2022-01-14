package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

//TODO this object is the same inside package largeStablishments, it can be share.

@Component
@JsonIgnoreProperties({ "full_path","dependency_group","parent_id","tree_id","asia_id","core_type","level" })
public class ClassificationData {

    // Factorisize properties
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;

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
