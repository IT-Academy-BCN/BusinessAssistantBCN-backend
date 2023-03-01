package com.businessassistantbcn.gencat.exception;

import com.businessassistantbcn.gencat.dto.GenericResultDto;
import com.businessassistantbcn.gencat.dto.io.CcaeDto;
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
    private GenericResultDto<CcaeDto> genericResultDto;

    @ExceptionHandler(ExpectedJSONFieldNotFoundException.class)
    @ResponseBody
    public GenericResultDto<CcaeDto> handleJSONFieldNotFound(ExpectedJSONFieldNotFoundException ex) {

        log.warn(ex.getMessage());

        //Returns an empty response object given the exception
        genericResultDto.setInfo(0, -1, 0, new CcaeDto[0]);
        return genericResultDto;
    }
}
