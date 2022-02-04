package com.businessassistantbcn.opendata.dto.largeestablishments;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ClassificationDataDto {
	
    private int id;
    private String name;
    private String full_path;
    private int dependency_group;
    private int parent_id;
    private int tree_id;
    private String asia_id;
    private String core_type;
    private int level;
    
}
