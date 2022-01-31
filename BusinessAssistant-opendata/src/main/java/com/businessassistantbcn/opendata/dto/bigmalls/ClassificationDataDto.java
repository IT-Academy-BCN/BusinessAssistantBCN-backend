package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@JsonIgnoreProperties({ "full_path","dependency_group","parent_id","tree_id","asia_id","core_type","level" })
public class ClassificationDataDto {

    private int id;
    private String name;

    @JsonGetter("id")
    public int getId() {
        return id;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }


    
}
