package com.businessassistantbcn.gencat.dto.output;


import com.businessassistantbcn.gencat.helper.CcaeDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
@JsonDeserialize(using = CcaeDeserializer.class)
public class AllCcaeDto {

    private List<CcaeDto> allCcae;

}
