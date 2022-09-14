package com.businessassistantbcn.gencat.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CcaeResponseDto {

    private String id;
    private String type;
    private CodeInfoDto code;

}
