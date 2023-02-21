package com.businessassistantbcn.mydata.handleError;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ErrorDetailsResponse{

    @Value("${user.searchLimit.errorMessage}")
    private String errorMessage;
    private HttpStatus httpStatus;
    private Date timestamp;

}
