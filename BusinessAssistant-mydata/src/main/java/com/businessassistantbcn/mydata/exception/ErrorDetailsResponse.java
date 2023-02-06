package com.businessassistantbcn.mydata.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ErrorDetailsResponse{

    private String message;
    private String details;
    private Date timestamp;

}
