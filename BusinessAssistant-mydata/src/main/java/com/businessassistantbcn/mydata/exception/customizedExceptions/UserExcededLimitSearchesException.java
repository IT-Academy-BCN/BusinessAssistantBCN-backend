package com.businessassistantbcn.mydata.exception.customizedExceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class UserExcededLimitSearchesException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String message;

    public UserExcededLimitSearchesException(String message){;
        this.message = message;
    }


}
