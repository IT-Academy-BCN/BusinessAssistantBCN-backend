package com.businessassistantbcn.usermanagement.dto;

import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.List;

public class GenericResultDto<T>{
    private int offset;
    private int limit;
    private int count;

    private T[] results;

    public GenericResultDto(T... results) {
        offset = 0; //default value when no pagintaion
        limit = -1; //default value when no pagintaion
        count = results.length;
        this.results = results;
    }

}
