package com.businessassistantbcn.mydata.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class DetailsResponseDTO {

    @Value("${user.searchLimit.errorMessage}")
    private String errorMessage;
    private Date timestamp;

}
