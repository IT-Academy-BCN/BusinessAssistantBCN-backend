package com.businessassistantbcn.opendata.dto.largeestablishments;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component("LargeEstablishmentsClassificationDataDto")
@JsonIgnoreProperties({ "dependency_group","parent_id","tree_id","asia_id","core_type","level" })

public class ClassificationDataDto {

    private Long id;
    private String name;
    private String full_path;

    @JsonGetter("id")
    public Long getId() {
        return id;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }
    
    @JsonGetter("full_path")
    public String getFull_path() {
        return full_path;
    }
    
}
