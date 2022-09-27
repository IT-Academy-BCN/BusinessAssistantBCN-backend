package com.businessassistantbcn.gencat.dto.input;


import com.businessassistantbcn.gencat.helper.CcaeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class CcaeDto {

    private String id;
    private String name;
    private String assetType;;



}
