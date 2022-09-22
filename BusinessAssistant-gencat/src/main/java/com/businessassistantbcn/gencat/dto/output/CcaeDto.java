package com.businessassistantbcn.gencat.dto.output;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class CcaeDto {

    private String id;

    private String type;

    private CodeInfoDto codeInfoDto;

}
