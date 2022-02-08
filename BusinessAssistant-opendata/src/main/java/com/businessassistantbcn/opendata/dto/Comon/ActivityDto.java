package com.businessassistantbcn.opendata.dto.Comon;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter @Setter
public class ActivityDto {

	
	
	@JsonProperty("id")
    private long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("full_path")
	private String full_path;
	
	
//	@JsonProperty("name")
//	private String name;
//
//	@JsonProperty("dependency_group")
//	private int dependency_group;
//	@JsonProperty("parent_id")
//	private int parent_id;
//	@JsonProperty("tree_id")
//	private int  tree_id;
//	@JsonProperty("asia_id")
//	private int asia_id;
//	@JsonProperty("core_type")
//	private String core_type;
//	@JsonProperty("level")
//	private int level;
//	
}
