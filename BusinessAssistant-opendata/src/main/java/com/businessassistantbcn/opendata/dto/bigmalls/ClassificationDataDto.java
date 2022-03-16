package com.businessassistantbcn.opendata.dto.bigmalls;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component("BigMallsClassificationDataDto")
@JsonIgnoreProperties({"dependency_group","parent_id","tree_id","asia_id","core_type","level" })

public class ClassificationDataDto {

    private Long id;
    private String name;
    private String fullPath;

    @JsonGetter("id")
    public Long getId() {
        return id;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("full_path")
    public String getFullPath() {
        return fullPath;
    }


    public ClassificationDataDto(Long id,String name){//Is used Only for OpendataControllerTest
        this.id=id;
        this.name=name;
    }
}
