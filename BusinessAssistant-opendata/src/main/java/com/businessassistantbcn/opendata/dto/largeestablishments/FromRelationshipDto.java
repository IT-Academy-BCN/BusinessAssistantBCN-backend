package com.businessassistantbcn.opendata.dto.largeestablishments;

import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class FromRelationshipDto {
    private int type_id;
    private String name;
    private long entity_id;
    private EntityPrefixDto entity_prefix;
    private String entity_name;
    private String entity_institution;
    private EntitySufixDto entity_suffix;
    private String entity_core_type;
    private List<AddressDto> addresses;
}
