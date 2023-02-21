package com.businessassistantbcn.mydata.exception;

import com.businessassistantbcn.gencat.exception.ExpectedJSONFieldNotFoundException;
import com.businessassistantbcn.mydata.dto.GenericSearchesResultDto;
import com.businessassistantbcn.mydata.entity.UserSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerAdvisor {

    private static final Logger log = LoggerFactory.getLogger(ControllerAdvisor.class);

    @Autowired
    private GenericSearchesResultDto<UserSearch> genericSearchesResultDto;

    @ExceptionHandler(ExpectedJSONFieldNotFoundException.class)
    @ResponseBody
    public GenericSearchesResultDto<UserSearch> handleJSONFieldNotFound(ExpectedJSONFieldNotFoundException ex) {

        log.warn(ex.getMessage());

        genericSearchesResultDto.setInfo(0, -1, 0, new UserSearch[0]);
        return genericSearchesResultDto;
    }
}
