package com.businessassistantbcn.gencat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class ExpectedJSONFieldNotFoundException extends RuntimeException{

    public ExpectedJSONFieldNotFoundException(String message) {
        super(message);
    }
}
