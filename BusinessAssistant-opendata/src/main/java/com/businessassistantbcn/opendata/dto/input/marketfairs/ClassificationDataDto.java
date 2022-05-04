package com.businessassistantbcn.opendata.dto.input.marketfairs;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component("MarketFairsClassificationDataDto")
@JsonIgnoreProperties({ "full_path","dependency_group","parent_id","tree_id","asia_id","core_type","level" })
public class ClassificationDataDto {

	private Long id;
    private String name;

    @JsonGetter("id")
    public Long getId() {
        return id;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

}
