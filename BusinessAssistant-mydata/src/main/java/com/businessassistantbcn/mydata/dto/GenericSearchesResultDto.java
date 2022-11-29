package com.businessassistantbcn.mydata.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.stereotype.Component;

@Component
@Getter 
@Setter
@ToString
public class GenericSearchesResultDto<T> {

    private int offset;
    private int limit;
    private int count;

    private T[] results;

    public GenericSearchesResultDto() {}

    public void setInfo(int offset, int limit, int count, T[] results) {
        this.offset = offset;
        this.limit = limit;
        this.count = count;
        this.results = results;
    }
}
