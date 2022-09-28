package com.businessassistantbcn.gencat.dto.io;


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
    private CodeInfoDto codeInfoDto;;

}
