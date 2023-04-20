package com.businessassistantbcn.gencat.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
public class GenericResultDto<T> {

    private int offset;
    private int limit;
    private int count;

    private T[] results;

    private T result;

    public GenericResultDto() {
        //Empty constructor
    }

    public void setInfo(int offset, int limit, int count, T[] results) {
        this.offset = offset;
        this.limit = limit;
        this.count = count;
        this.results = results;
    }

    public void setInfoSingle(int offset, int limit, T result) {
        this.offset = offset;
        this.limit = limit;
        //this.count = count;
        this.result = result;
    }

}
